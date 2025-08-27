package com.aurionpro.Dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.aurionpro.model.Transaction;

public class TransactionDao {
	private DataSource dataSource;

	public TransactionDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public double transferFunds(long senderAccNo, long receiverAccNo, double amount) {
		String debitSql = "UPDATE account SET balance = balance - ? WHERE acc_no = ? AND balance >= ? AND is_deleted = FALSE";
		String creditSql = "UPDATE account SET balance = balance + ? WHERE acc_no = ? AND is_deleted = FALSE";
		String insertTxnSql = "INSERT INTO transactions " + "(sender_acc, receiver_acc, amount, transaction_type, "
				+ "sender_prev_balance, sender_new_balance, receiver_prev_balance, receiver_new_balance) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			// 1. Get balances before transfer
			double senderPrevBalance = getBalance(conn, senderAccNo);
			double receiverPrevBalance = getBalance(conn, receiverAccNo);

			if (senderPrevBalance < amount) {
				return -10; // insufficient balance
			}

			// 2. Debit sender
			try (PreparedStatement ps = conn.prepareStatement(debitSql)) {
				ps.setDouble(1, amount);
				ps.setLong(2, senderAccNo);
				ps.setDouble(3, amount);
				if (ps.executeUpdate() == 0) {
					conn.rollback();
					return -1;
				}
			}

			// 3. Credit receiver
			try (PreparedStatement ps = conn.prepareStatement(creditSql)) {
				ps.setDouble(1, amount);
				ps.setLong(2, receiverAccNo);
				if (ps.executeUpdate() == 0) {
					conn.rollback();
					return -1;
				}
			}

			// 4. Get balances after transfer
			double senderNewBalance = getBalance(conn, senderAccNo);
			double receiverNewBalance = getBalance(conn, receiverAccNo);

			// 5. Insert transaction record
			try (PreparedStatement ps = conn.prepareStatement(insertTxnSql)) {
				ps.setLong(1, senderAccNo);
				ps.setLong(2, receiverAccNo);
				ps.setDouble(3, amount);
				ps.setString(4, "transfer");
				ps.setDouble(5, senderPrevBalance);
				ps.setDouble(6, senderNewBalance);
				ps.setDouble(7, receiverPrevBalance);
				ps.setDouble(8, receiverNewBalance);
				ps.executeUpdate();
			}

			conn.commit();
			return senderNewBalance;

		} catch (SQLException e) {
			e.printStackTrace();
			if (conn != null)
				try {
					conn.rollback();
				} catch (SQLException ignored) {
				}
			return -1;
		} finally {
			if (conn != null)
				try {
					conn.setAutoCommit(true);
					conn.close();
				} catch (SQLException ignored) {
				}
		}
	}

	private double getBalance(Connection conn, long accNo) throws SQLException {
		String sql = "SELECT balance FROM account WHERE acc_no=? AND is_deleted=FALSE";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setLong(1, accNo);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getDouble("balance");
				}
			}
		}
		return -1;
	}
	
	public double withdraw(long accNo, double amount) {
	    String updateSql = "UPDATE account SET balance = balance - ? WHERE acc_no = ? AND balance >= ? AND is_deleted = FALSE";
	    String insertTxnSql = "INSERT INTO transactions " +
	            "(sender_acc, receiver_acc, amount, transaction_type, " +
	            "sender_prev_balance, sender_new_balance, receiver_prev_balance, receiver_new_balance) " +
	            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	    Connection conn = null;

	    try {
	        conn = dataSource.getConnection();
	        conn.setAutoCommit(false);

	        // Get balance before
	        double prevBalance = getBalance(conn, accNo);
	        if (prevBalance < amount) {
	            return -10; // insufficient funds
	        }

	        // Update balance
	        try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
	            ps.setDouble(1, amount);
	            ps.setLong(2, accNo);
	            ps.setDouble(3, amount);
	            if (ps.executeUpdate() == 0) {
	                conn.rollback();
	                return -1;
	            }
	        }

	        // Get balance after
	        double newBalance = getBalance(conn, accNo);

	        // Insert transaction record
	        try (PreparedStatement ps = conn.prepareStatement(insertTxnSql)) {
	            ps.setLong(1, accNo);                  
	            ps.setNull(2, Types.BIGINT);           
	            ps.setDouble(3, amount);
	            ps.setString(4, "withdrawal");
	            ps.setDouble(5, prevBalance);          
	            ps.setDouble(6, newBalance);           
	            ps.setNull(7, Types.DOUBLE);           
	            ps.setNull(8, Types.DOUBLE);           
	            ps.executeUpdate();
	        }

	        conn.commit();
	        return newBalance;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        if (conn != null) try { conn.rollback(); } catch (SQLException ignored) {}
	        return -1;
	    } finally {
	        if (conn != null) try { conn.setAutoCommit(true); conn.close(); } catch (SQLException ignored) {}
	    }
	}
	
	public double deposit(long accNo, double amount) {
	    String updateSql = "UPDATE account SET balance = balance + ? WHERE acc_no = ? AND is_deleted = FALSE";
	    String insertTxnSql = "INSERT INTO transactions " +
	            "(sender_acc, receiver_acc, amount, transaction_type, " +
	            "sender_prev_balance, sender_new_balance, receiver_prev_balance, receiver_new_balance) " +
	            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	    Connection conn = null;

	    try {
	        conn = dataSource.getConnection();
	        conn.setAutoCommit(false);

	        // Get balance before
	        double prevBalance = getBalance(conn, accNo);

	        // Update balance
	        try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
	            ps.setDouble(1, amount);
	            ps.setLong(2, accNo);
	            if (ps.executeUpdate() == 0) {
	                conn.rollback();
	                return -1;
	            }
	        }

	        // Get balance after
	        double newBalance = getBalance(conn, accNo);

	        // Insert transaction record (only sender account is involved in deposit)
	        try (PreparedStatement ps = conn.prepareStatement(insertTxnSql)) {
	            ps.setLong(1, accNo);                  
	            ps.setNull(2, Types.BIGINT);           
	            ps.setDouble(3, amount);
	            ps.setString(4, "deposit");
	            ps.setDouble(5, prevBalance);          
	            ps.setDouble(6, newBalance);           
	            ps.setNull(7, Types.DOUBLE);           
	            ps.setNull(8, Types.DOUBLE);           
	            ps.executeUpdate();
	        }

	        conn.commit();
	        return newBalance;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        if (conn != null) try { conn.rollback(); } catch (SQLException ignored) {}
	        return -1;
	    } finally {
	        if (conn != null) try { conn.setAutoCommit(true); conn.close(); } catch (SQLException ignored) {}
	    }
	}
	
	public double upiTransfer(String senderUpiId, String receiverUpiId, double amount) {
	    String findCustomerSql = "SELECT customer_id FROM customer WHERE upi_id = ? AND is_deleted = FALSE";
	    String findUpiAccountSql = "SELECT acc_no, balance FROM account WHERE customer_id = ? AND is_upi_enable = TRUE AND is_deleted = FALSE";
	    String debitSql = "UPDATE account SET balance = balance - ? WHERE acc_no = ? AND balance >= ? AND is_deleted = FALSE";
	    String creditSql = "UPDATE account SET balance = balance + ? WHERE acc_no = ? AND is_deleted = FALSE";
	    String insertTxnSql = "INSERT INTO transactions " +
	            "(sender_acc, receiver_acc, amount, transaction_type, " +
	            "sender_prev_balance, sender_new_balance, receiver_prev_balance, receiver_new_balance, " +
	            "sender_upi_id, receiver_upi_id) " +
	            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    Connection conn = null;

	    try {
	        conn = dataSource.getConnection();
	        conn.setAutoCommit(false);

	        // 1. Validate sender UPI
	        long senderCustomerId;
	        try (PreparedStatement ps = conn.prepareStatement(findCustomerSql)) {
	            ps.setString(1, senderUpiId);
	            ResultSet rs = ps.executeQuery();
	            if (!rs.next()) return -1; // sender not found
	            senderCustomerId = rs.getLong("customer_id");
	        }

	        // 2. Validate receiver UPI
	        long receiverCustomerId;
	        try (PreparedStatement ps = conn.prepareStatement(findCustomerSql)) {
	            ps.setString(1, receiverUpiId);
	            ResultSet rs = ps.executeQuery();
	            if (!rs.next()) return -20; // receiver not found
	            receiverCustomerId = rs.getLong("customer_id");
	        }

	        // 3. Get sender UPI-enabled account
	        long senderAccNo;
	        double senderPrevBalance;
	        try (PreparedStatement ps = conn.prepareStatement(findUpiAccountSql)) {
	            ps.setLong(1, senderCustomerId);
	            ResultSet rs = ps.executeQuery();
	            if (!rs.next()) return -30; // no UPI-enabled account
	            senderAccNo = rs.getLong("acc_no");
	            senderPrevBalance = rs.getDouble("balance");
	        }

	        // 4. Get receiver UPI-enabled account
	        long receiverAccNo;
	        double receiverPrevBalance;
	        try (PreparedStatement ps = conn.prepareStatement(findUpiAccountSql)) {
	            ps.setLong(1, receiverCustomerId);
	            ResultSet rs = ps.executeQuery();
	            if (!rs.next()) return -30; // no UPI-enabled account
	            receiverAccNo = rs.getLong("acc_no");
	            receiverPrevBalance = rs.getDouble("balance");
	        }

	        if (senderPrevBalance < amount) {
	            return -10; // insufficient funds
	        }

	        // 5. Debit sender
	        try (PreparedStatement ps = conn.prepareStatement(debitSql)) {
	            ps.setDouble(1, amount);
	            ps.setLong(2, senderAccNo);
	            ps.setDouble(3, amount);
	            if (ps.executeUpdate() == 0) {
	                conn.rollback();
	                return -1;
	            }
	        }

	        // 6. Credit receiver
	        try (PreparedStatement ps = conn.prepareStatement(creditSql)) {
	            ps.setDouble(1, amount);
	            ps.setLong(2, receiverAccNo);
	            if (ps.executeUpdate() == 0) {
	                conn.rollback();
	                return -1;
	            }
	        }

	        // 7. Get balances after
	        double senderNewBalance = getBalance(conn, senderAccNo);
	        double receiverNewBalance = getBalance(conn, receiverAccNo);

	        // 8. Insert transaction record
	        try (PreparedStatement ps = conn.prepareStatement(insertTxnSql)) {
	            ps.setLong(1, senderAccNo);
	            ps.setLong(2, receiverAccNo);
	            ps.setDouble(3, amount);
	            ps.setString(4, "upi");
	            ps.setDouble(5, senderPrevBalance);
	            ps.setDouble(6, senderNewBalance);
	            ps.setDouble(7, receiverPrevBalance);
	            ps.setDouble(8, receiverNewBalance);
	            ps.setString(9, senderUpiId);
	            ps.setString(10, receiverUpiId);
	            ps.executeUpdate();
	        }

	        conn.commit();
	        return senderNewBalance;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        if (conn != null) try { conn.rollback(); } catch (SQLException ignored) {}
	        return -1;
	    } finally {
	        if (conn != null) try { conn.setAutoCommit(true); conn.close(); } catch (SQLException ignored) {}
	    }
	}
	
	public List<Transaction> getPassbookTransactions(long accNo, Date fromDate, Date toDate) {
		List<Transaction> transactions = new ArrayList<>();

		String sql = "SELECT * FROM transactions " +
		             "WHERE (sender_acc = ? OR receiver_acc = ?) ";

		if (fromDate != null && toDate != null) {
		    sql += "AND transaction_time BETWEEN ? AND ? ";
		}

		sql += "ORDER BY transaction_time DESC";

		try (Connection conn = dataSource.getConnection();
		     PreparedStatement ps = conn.prepareStatement(sql)) {

		    ps.setLong(1, accNo);
		    ps.setLong(2, accNo);

		    if (fromDate != null && toDate != null) {
		        ps.setDate(3, fromDate);
		        ps.setDate(4, toDate);
		    }

		    try (ResultSet rs = ps.executeQuery()) {
		        while (rs.next()) {
		            Transaction txn = new Transaction();
		            txn.setTransactionId(rs.getLong("transaction_id"));
		            txn.setTransactionTime(rs.getTimestamp("transaction_time"));
		            txn.setSenderAcc(rs.getLong("sender_acc"));
		            txn.setReceiverAcc(rs.getLong("receiver_acc"));
		            txn.setAmount(rs.getDouble("amount"));
		            txn.setTransactionType(rs.getString("transaction_type"));

		            // Set sender/receiver balances
		            txn.setSenderPreBalance(rs.getDouble("sender_prev_balance"));
		            txn.setSenderNewBalance(rs.getDouble("sender_new_balance"));
		            txn.setReceiverPreBalance(rs.getDouble("receiver_prev_balance"));
		            txn.setReceiverNewBalance(rs.getDouble("receiver_new_balance"));

		            txn.setSenderUpiId(rs.getString("sender_upi_id"));
		            txn.setReceiverUpiId(rs.getString("receiver_upi_id"));

		            // ✅ Set balance dynamically for passbook view
		            if (accNo == txn.getSenderAcc()) {
		                txn.setBalance(txn.getSenderNewBalance());
		            } else if (accNo == txn.getReceiverAcc()) {
		                txn.setBalance(txn.getReceiverNewBalance());
		            }

		            transactions.add(txn);
		        }
		    }

		} catch (SQLException e) {
		    e.printStackTrace();
		}

		return transactions;

    }
	
	public List<Transaction> getTransactions(
            String type, 
            String accNo, 
            Date fromDate, 
            Date toDate, 
            Double minAmount, 
            Double maxAmount, String upi) {

        List<Transaction> transactions = new ArrayList<>();

        // Base query
        StringBuilder sql = new StringBuilder("SELECT * FROM transactions WHERE transaction_type = ? ");

        List<Object> params = new ArrayList<>();
        params.add(type); // deposit / withdrawal / transfer / upi

        // Filters
        if (accNo != null && !accNo.isEmpty()) {
            sql.append("AND (sender_acc = ? OR receiver_acc = ?) ");
            params.add(Long.parseLong(accNo));
            params.add(Long.parseLong(accNo));
        }
        
        if (upi != null && !upi.isEmpty()) {
            sql.append("AND (sender_upi_id = ? OR receiver_upi_id = ?) ");
            params.add(upi);
            params.add(upi);
        }
        
        if (fromDate != null && toDate != null) {
            sql.append("AND transaction_time BETWEEN ? AND ? ");
            params.add(fromDate);
            params.add(toDate);
        }
        else if(fromDate != null) {
        	sql.append("AND transaction_time > ? ");
            params.add(fromDate);
        }
        else if(toDate != null) {
        	sql.append("AND transaction_time < ? ");
            params.add(toDate);
        }
        if (minAmount != null) {
            sql.append("AND amount >= ? ");
            params.add(minAmount);
        }
        if (maxAmount != null) {
            sql.append("AND amount <= ? ");
            params.add(maxAmount);
        }

        sql.append("ORDER BY transaction_time DESC");

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            // Set params dynamically
            for (int i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                if (param instanceof Long) {
                    ps.setLong(i + 1, (Long) param);
                } else if (param instanceof Date) {
                    ps.setDate(i + 1, (Date) param);
                } else if (param instanceof Double) {
                    ps.setDouble(i + 1, (Double) param);
                } else if (param instanceof String) {
                    ps.setString(i + 1, (String) param);
                }
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Transaction txn = new Transaction();
                    txn.setTransactionId(rs.getLong("transaction_id"));
                    txn.setTransactionTime(rs.getTimestamp("transaction_time"));
                    txn.setSenderAcc(rs.getLong("sender_acc"));
                    txn.setReceiverAcc(rs.getLong("receiver_acc"));
                    txn.setAmount(rs.getDouble("amount"));
                    txn.setTransactionType(rs.getString("transaction_type"));
                    txn.setSenderUpiId(rs.getString("sender_upi_id"));
                    txn.setReceiverUpiId(rs.getString("receiver_upi_id"));
                    txn.setSenderPreBalance(rs.getDouble("sender_prev_balance"));
                    txn.setSenderNewBalance(rs.getDouble("sender_new_balance"));
                    txn.setReceiverPreBalance(rs.getDouble("receiver_prev_balance"));
                    txn.setReceiverNewBalance(rs.getDouble("receiver_new_balance"));

                    transactions.add(txn);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }
}
