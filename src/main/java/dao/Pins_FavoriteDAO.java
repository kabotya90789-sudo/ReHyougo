package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Pin;

public class Pins_FavoriteDAO {
	
	//	データベース接続値定義
	private final String JDBC_URL = "jdbc:mysql://localhost/rehyogo?characterEncoding=UTF-8&serverTimezone=Asia/Tokyo";
	private final String DB_USER = "root";
	private final String DB_PASS = "root";
	
	public Pins_FavoriteDAO() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした", e);
		}
	}
	
	public boolean updatePinsFavorite(String loginUserId , int pinId) {

	    final String deleteSql = 
	        "DELETE FROM PINS_FAVORITE WHERE USERS_USER_ID = ? AND PINS_ID = ?";
	    final String insertSql = 
	        "INSERT INTO PINS_FAVORITE (USERS_USER_ID, PINS_ID) VALUES (?, ?)";

	    boolean isFavorite = checkPinsFavorite(pinId, loginUserId);

	    try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

	        conn.setAutoCommit(false); // トランザクション開始

	        if (isFavorite) {
	            // お気に入り → 解除
	            try (PreparedStatement stmt = conn.prepareStatement(deleteSql)) {
	                stmt.setString(1, loginUserId);
	                stmt.setInt(2, pinId);
	                stmt.executeUpdate();
	            }
	        } else {
	            // 未登録 → 登録
	            try (PreparedStatement stmt = conn.prepareStatement(insertSql)) {
	                stmt.setString(1, loginUserId);
	                stmt.setInt(2, pinId);
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

	
	
	//ピン詳細表示の際、ピンに対して閲覧するユーザーのお気に入り有無を判定する
	public boolean checkPinsFavorite(int pinId , String userId) {

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			
			String sql = "SELECT * FROM PINS_FAVORITE WHERE USERS_USER_ID=? AND PINS_ID=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userId);
			pStmt.setInt(2, pinId);
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
	public List<Pin> dispYourFavoritePins(String loginUserId) {

	    final String sql =
	        "SELECT P.ID, P.USERS_USER_ID, P.PIN_NAME, P.PIN_ADDRESS, P.PIN_URL, " +
	        "       P.PIN_GEO_X, P.PIN_GEO_Y, P.PIN_IMAGE_URL, P.PIN_INFO, P.PIN_TIMESTAMP " +
	        "FROM PINS P " +
	        "JOIN PINS_FAVORITE PF ON P.ID = PF.PINS_ID " +
	        "WHERE PF.USERS_USER_ID = ? " +
	        "ORDER BY P.PIN_TIMESTAMP DESC";

	    List<Pin> pinsList = new ArrayList<>();

	    try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, loginUserId);

	        try (ResultSet rs = stmt.executeQuery()) {

	            while (rs.next()) {
	                pinsList.add(new Pin(
	                    rs.getInt("ID"),
	                    rs.getString("USERS_USER_ID"),
	                    rs.getString("PIN_NAME"),
	                    rs.getString("PIN_ADDRESS"),
	                    rs.getString("PIN_URL"),
	                    rs.getString("PIN_GEO_Y"),
	                    rs.getString("PIN_GEO_X"),
	                    rs.getString("PIN_IMAGE_URL"),
	                    rs.getString("PIN_INFO"),
	                    rs.getDate("PIN_TIMESTAMP"),
	                    true // ← お気に入りデータのみをJOIN しているので必ずお気に入り
	                ));
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return pinsList;
	}

}
