package com.aurionpro.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.aurionpro.model.BankBranch;

public class BranchDao {
	private DataSource dataSource;

	public BranchDao(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	public boolean addBranch(BankBranch branch) {
		String INSERT_SQL ="INSERT INTO bank_branch (ifsc_code, branch_name, address)VALUES (?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {

            ps.setString(1, branch.getIfscCode());
            ps.setString(2, branch.getBranchName());
            ps.setString(3, branch.getAddress());
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

	public List<BankBranch> getAllBranches() throws SQLException {
		List<BankBranch> branches = new ArrayList<>();
        String sql = "SELECT ifsc_code, branch_name FROM bank_branch WHERE is_deleted = FALSE";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                BankBranch branch = new BankBranch();
                branch.setIfscCode(rs.getString("ifsc_code"));
                branch.setBranchName(rs.getString("branch_name"));
                branches.add(branch);
            }
        }
        return branches;
	}
}
