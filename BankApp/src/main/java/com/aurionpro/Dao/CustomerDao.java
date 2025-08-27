package com.aurionpro.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.mindrot.jbcrypt.BCrypt;

import com.aurionpro.model.Account;
import com.aurionpro.model.Beneficiary;
import com.aurionpro.model.Customer;

public class CustomerDao {

	private DataSource dataSource;

	public CustomerDao(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	public Customer login(String email, String password) {
		String sql = "SELECT * FROM customer WHERE email=? AND is_deleted=FALSE";
		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, email);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String hashedPassword = rs.getString("password");
				if (BCrypt.checkpw(password, hashedPassword)) {
					Customer customer = new Customer();
					customer.setCustomerId(rs.getInt("customer_id"));
					customer.setEmail(rs.getString("email"));
					customer.setPassword(rs.getString("password"));
					customer.setFirstName(rs.getString("first_name"));
					customer.setLastName(rs.getString("last_name"));
					customer.setGender(rs.getString("gender"));
					customer.setDob(rs.getDate("dob"));
					customer.setAadhar(rs.getString("aadhar"));
					customer.setPan(rs.getString("pan"));
					customer.setContactNo(rs.getString("contact_no"));
					customer.setCreatedAt(rs.getTimestamp("created_at"));
					customer.setDeleted(rs.getBoolean("is_deleted"));
					customer.setUpiId(rs.getString("upi_id"));

					return customer;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean addCustomer(Customer customer) {
		String INSERT_SQL ="INSERT INTO customer (email, password, first_name, last_name, gender, dob, aadhar, pan, contact_no, upi_id)VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {

            ps.setString(1, customer.getEmail());

            // Default password (to be changed by customer later) → "customer@123"
            String defaultPassword = customer.getLastName()+customer.getContactNo().substring(6); 
            String hashedPassword = BCrypt.hashpw(defaultPassword, BCrypt.gensalt());
            ps.setString(2, hashedPassword);

            ps.setString(3, customer.getFirstName());
            ps.setString(4, customer.getLastName());
            ps.setString(5, customer.getGender());
            ps.setDate(6, customer.getDob());
            ps.setString(7, customer.getAadhar());
            ps.setString(8, customer.getPan());
            ps.setString(9, customer.getContactNo());
            
            String upiId=customer.getContactNo()+"@haribhari";
            ps.setString(10, upiId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

	public List<Customer> getAllCustomers() throws SQLException {
		List<Customer> customers = new ArrayList<>();
        String sql = "SELECT customer_id, first_name, last_name FROM customer WHERE is_deleted = FALSE";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Customer cust = new Customer();
                cust.setCustomerId(rs.getInt("customer_id"));
                cust.setFirstName(rs.getString("first_name"));
                cust.setLastName(rs.getString("last_name"));
                customers.add(cust);
            }
        }
        return customers;
	}
	
	public List<Customer> getAllCustomersWithAccounts() throws SQLException {
        Map<Integer, Customer> customerMap = new LinkedHashMap<>();

        String sql = "SELECT c.customer_id, c.first_name, c.last_name, c.created_at, " +
                     "a.acc_no, a.balance, b.branch_name " +
                     "FROM customer c " +
                     "LEFT JOIN account a ON c.customer_id = a.customer_id AND a.is_deleted = FALSE " +
                     "LEFT JOIN bank_branch b ON a.ifsc_code = b.ifsc_code " +
                     "WHERE c.is_deleted = FALSE " +
                     "ORDER BY c.customer_id, a.acc_no";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int custId = rs.getInt("customer_id");

                // If new customer, create entry
                Customer cust = customerMap.get(custId);
                if (cust == null) {
                    cust = new Customer();
                    cust.setCustomerId(custId);
                    cust.setFirstName(rs.getString("first_name"));
                    cust.setLastName(rs.getString("last_name"));
                    cust.setCreatedAt(rs.getTimestamp("created_at"));
                    customerMap.put(custId, cust);
                }

                // If account exists, add to list
                long accNo = rs.getLong("acc_no");
                if (accNo != 0) { 
                    Account acc = new Account();
                    acc.setAccNo(accNo);
                    acc.setBalance(rs.getDouble("balance"));
                    acc.setBranchName(rs.getString("branch_name"));
                    cust.getAccounts().add(acc);
                }
            }
        }

        return new ArrayList<>(customerMap.values());
    }

	public boolean addBeneficiary(Beneficiary beneficiary, Customer customer) {
		String INSERT_SQL ="INSERT INTO beneficiaries (nickname, beneficiary_acc_no, customer_id)VALUES (?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {

            ps.setString(1, beneficiary.getNickname());
            ps.setLong(2, beneficiary.getBeneficiaryAccNo());
            ps.setInt(3,customer.getCustomerId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
	}

	public boolean changePassword(String newPassword, Customer customer) {
		String UPDATE_SQL ="UPDATE customer SET password = ? WHERE customer_id= ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            ps.setString(1, hashedPassword);
            ps.setInt(2, customer.getCustomerId());
            
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
	}
}
