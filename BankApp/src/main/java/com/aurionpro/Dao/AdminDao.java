package com.aurionpro.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.mindrot.jbcrypt.BCrypt;

import com.aurionpro.model.Admin;

public class AdminDao {

	private DataSource dataSource;

	public AdminDao(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	public Admin login(String email, String password) {
		String sql = "SELECT * FROM admin WHERE email=? AND is_deleted=FALSE";
		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, email);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String hashedPassword = rs.getString("password");
				if (BCrypt.checkpw(password, hashedPassword)) {
					Admin admin = new Admin();
					admin.setId(rs.getInt("admin_id"));
					admin.setEmail(rs.getString("email"));
					admin.setPassword(rs.getString("password"));
					admin.setFirstName(rs.getString("first_name"));
					admin.setLastName(rs.getString("last_name"));
					admin.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
					admin.setDeleted(rs.getBoolean("is_deleted"));
					return admin;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
