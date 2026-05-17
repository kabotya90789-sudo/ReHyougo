package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Reviews_FavoriteDAO {
	
	private static final String JDBC_URL = "jdbc:mysql://localhost/rehyogo?characterEncoding=UTF-8&serverTimezone=Asia/Tokyo";
	private static final String DB_USER = "root";
	private static final String DB_PASS = "root";

	public Reviews_FavoriteDAO() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした", e);
		}
	}
	
	public boolean updateReviewsFavorite(String loginUserId , int revId) {

	    final String deleteSql = 
	        "DELETE FROM REVIEWS_FAVORITE WHERE USERS_USER_ID = ? AND REVIEWS_ID = ?";
	    final String insertSql = 
	        "INSERT INTO REVIEWS_FAVORITE (USERS_USER_ID, REVIEWS_ID) VALUES (?, ?)";

	    boolean isFavorite = checkReviewsFavorite(revId, loginUserId);

	    try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

	        conn.setAutoCommit(false); // トランザクション開始

	        if (isFavorite) {
	            // お気に入り → 解除
	            try (PreparedStatement stmt = conn.prepareStatement(deleteSql)) {
	                stmt.setString(1, loginUserId);
	                stmt.setInt(2, revId);
	                stmt.executeUpdate();
	            }
	        } else {
	            // 未登録 → 登録
	            try (PreparedStatement stmt = conn.prepareStatement(insertSql)) {
	                stmt.setString(1, loginUserId);
	                stmt.setInt(2, revId);
	                stmt.executeUpdate();
	            }
	        }

	        conn.commit();
	        return true;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	
	public boolean checkReviewsFavorite (int revId ,String loginUserId) {
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			
			String sql = "SELECT * FROM REVIEWS_FAVORITE WHERE USERS_USER_ID=? AND REVIEWS_ID=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, loginUserId);
			pStmt.setInt(2, revId);
			ResultSet rs = pStmt.executeQuery();
			if (rs.next()) {
				return true; //お気に入りしている
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false; //お気に入りしていない
	}
}
