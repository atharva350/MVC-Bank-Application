package com.aurionpro.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.aurionpro.model.Account;
import com.aurionpro.model.Beneficiary;
import com.aurionpro.model.Customer;

public class AccountDao {
	private DataSource dataSource;

	public AccountDao(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	public boolean isFirstAccount(int customerId) {
		String sql = "SELECT * FROM account WHERE customer_id=? AND is_deleted=FALSE";
		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, customerId);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		}
		return true;
	}

	public void disableUpiAccounts(Integer customerId) {
		String sql = "Update account SET is_upi_enable = FALSE WHERE customer_id=? AND is_deleted=FALSE";
		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, customerId);

			ResultSet rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public boolean addAccount(Account account) {
		String sql = "INSERT INTO account (acc_no, customer_id, ifsc_code, balance, is_deleted, is_upi_enable) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, generateAccountNumber());
            ps.setInt(2, account.getCustomerId());
            ps.setString(3, account.getIfscCode());
            ps.setDouble(4, account.getBalance());
            ps.setBoolean(5, false);
            ps.setBoolean(6, account.isUpiEnabled());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public long generateAccountNumber() {
		long accNo = 1000000000L;
        try (Connection conn = dataSource.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT MAX(acc_no) FROM account")) {
            if (rs.next()) {
                accNo = rs.getLong(1) + 1;
            }
        } catch (SQLException e) {
			e.printStackTrace();
			return -1L;
		}
        return accNo;
	}

	public List<Account> getCustomerAccounts(Customer customer) {
		String sql = "SELECT * FROM account WHERE customer_id=? AND is_deleted=FALSE";
		List<Account> accounts = new ArrayList<>();
		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, customer.getCustomerId());

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Account acc = new Account();
				acc.setAccNo(rs.getLong("acc_no"));
				acc.setBalance(rs.getDouble("balance"));
				acc.setIfscCode(rs.getString("ifsc_code"));
				accounts.add(acc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return accounts;
	}
	
	public List<Beneficiary> getCustomerBeneficiaries(Customer customer3) {
		String sql = "SELECT * FROM beneficiaries WHERE customer_id=? AND is_deleted=FALSE";
		List<Beneficiary> beneficiaries = new ArrayList<>();
		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, customer3.getCustomerId());

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Beneficiary bf = new Beneficiary();
				bf.setBeneficiaryAccNo(rs.getLong("beneficiary_acc_no"));
				bf.setNickname(rs.getString("nickname"));
				beneficiaries.add(bf);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return beneficiaries;
	}
}
