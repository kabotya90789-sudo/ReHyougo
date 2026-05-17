package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Event;

public class Events_FavoriteDAO {
//	データベース接続値定義
	private final String JDBC_URL = "jdbc:mysql://localhost/rehyogo?characterEncoding=UTF-8&serverTimezone=Asia/Tokyo";
	private final String DB_USER = "root";
	private final String DB_PASS = "root";
	
	public Events_FavoriteDAO() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした", e);
		}
	}
	
	public boolean updateEventsFavorite (String loginUserId , int evtId) {
		final String deleteSql = 
		        "DELETE FROM EVENTS_FAVORITE WHERE USERS_USER_ID = ? AND EVENTS_ID = ?";
		    final String insertSql = 
		        "INSERT INTO EVENTS_FAVORITE (USERS_USER_ID, EVENTS_ID) VALUES (?, ?)";

		    boolean isFavorite = checkEventsFavorite(evtId, loginUserId);

		    try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

		        conn.setAutoCommit(false); // トランザクション開始

		        if (isFavorite) {
		            // お気に入り → 解除
		            try (PreparedStatement stmt = conn.prepareStatement(deleteSql)) {
		                stmt.setString(1, loginUserId);
		                stmt.setInt(2, evtId);
		                stmt.executeUpdate();
		            }
		        } else {
		            // 未登録 → 登録
		            try (PreparedStatement stmt = conn.prepareStatement(insertSql)) {
		                stmt.setString(1, loginUserId);
		                stmt.setInt(2, evtId);
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
	
	public boolean checkEventsFavorite(int evtId , String loginUserId) {

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			
			String sql = "SELECT * FROM EVENTS_FAVORITE WHERE USERS_USER_ID=? AND EVENTS_ID=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, loginUserId);
			pStmt.setInt(2, evtId);
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
	
	public List<Event> dispYourFavoriteEvents(String loginUserId) {

	    final String sql =
	        "SELECT E.ID, E.USERS_USER_ID, E.EVENT_NAME, E.EVENT_ADDRESS, E.EVENT_URL, " +
	        "       E.EVENT_GEO_X, E.EVENT_GEO_Y, E.EVENT_IMAGE_URL, E.EVENT_INFO, E.EVENT_START, E.EVENT_END, E.EVENT_TIMESTAMP " +
	        "FROM EVENTS E " +
	        "JOIN EVENTS_FAVORITE EF ON E.ID = EF.EVENTS_ID " +
	        "WHERE PF.USERS_USER_ID = ? " +
	        "ORDER BY E.EVENT_TIMESTAMP DESC";

	    List<Event> eventsList = new ArrayList<>();

	    try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, loginUserId);

	        try (ResultSet rs = stmt.executeQuery()) {

	            while (rs.next()) {
	                eventsList.add(new Event(
	                    rs.getInt("ID"),
	                    rs.getString("USERS_USER_ID"),
	                    rs.getString("EVENT_NAME"),
	                    rs.getString("EVENT_ADDRESS"),
	                    rs.getString("EVENT_URL"),
	                    rs.getString("EVENT_GEO_X"),
	                    rs.getString("EVENT_GEO_Y"),
	                    rs.getString("EVENT_IMAGE_URL"),
	                    rs.getString("EVENT_INFO"),
	                    rs.getDate("EVENT_START"),
	                    rs.getDate("EVENT_END"),
	                    rs.getDate("EVENT_TIMESTAMP"),
	                    true // ← お気に入りデータのみをJOIN しているので必ずお気に入り
	                ));
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return eventsList;
	}
	
}
