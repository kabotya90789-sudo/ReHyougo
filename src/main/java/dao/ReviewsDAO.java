package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Review;

public class ReviewsDAO {
	
	private static final String JDBC_URL = "jdbc:mysql://localhost/rehyogo?characterEncoding=UTF-8&serverTimezone=Asia/Tokyo";
	private static final String DB_USER = "root";
	private static final String DB_PASS = "root";

	public ReviewsDAO() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした", e);
		}
	}
	
	public List<Review> getReviews (int getPinId){
		List<Review> reviewsList = new ArrayList<>();
//		final String sql = "SELECT "
//				+ "    R.ID, "
//				+ "    R.USERS_USER_ID, "
//				+ "    R.PINS_ID, "
//				+ "    R.REVIEW_TITLE, "
//				+ "    R.REVIEW_CONTENTS, "
//				+ "    R.REVIEW_TIMESTAMP, "
//				+ "    COALESCE(RFC.REVIEWS_FAVORITE_COUNT, 0) AS REVIEWS_FAVORITE_COUNT "
//				+ "FROM REVIEWS R "
//				+ "LEFT JOIN ( "
//				+ "    SELECT "
//				+ "        R2.PINS_ID, "
//				+ "        COUNT(*) AS REVIEWS_FAVORITE_COUNT"
//				+ "    FROM REVIEWS_FAVORITE RF "
//				+ "    JOIN REVIEWS R2 ON RF.REVIEWS_ID = R2.ID "
//				+ "    GROUP BY R2.PINS_ID "
//				+ ") RFC ON R.PINS_ID = RFC.PINS_ID "
//				+ "WHERE R.PINS_ID = ?;";
		
		final String sql = "SELECT\n"
				+ "    R.ID,\n"
				+ "    R.USERS_USER_ID,\n"
				+ "    R.PINS_ID,\n"
				+ "    R.REVIEW_TITLE,\n"
				+ "    R.REVIEW_CONTENTS,\n"
				+ "    R.REVIEW_TIMESTAMP,\n"
				+ "    COALESCE(RFC.REVIEWS_FAVORITE_COUNT, 0) AS REVIEWS_FAVORITE_COUNT\n"
				+ "FROM REVIEWS R\n"
				+ "LEFT JOIN (\n"
				+ "    SELECT\n"
				+ "        REVIEWS_ID,\n"
				+ "        COUNT(*) AS REVIEWS_FAVORITE_COUNT\n"
				+ "    FROM REVIEWS_FAVORITE\n"
				+ "    GROUP BY REVIEWS_ID\n"
				+ ") RFC ON R.ID = RFC.REVIEWS_ID\n"
				+ "WHERE R.PINS_ID = ?;";
		
		
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, getPinId);

			try (ResultSet rs = stmt.executeQuery()) {
				while(rs.next()) {
					int id = rs.getInt("ID");
					String userId = rs.getString("USERS_USER_ID");
					int pinId = rs.getInt("PINS_ID");
					String title = rs.getString("REVIEW_TITLE");
					String content = rs.getString("REVIEW_CONTENTS");
					Date createdDate = rs.getDate("REVIEW_TIMESTAMP");
					int likeCounts = rs.getInt("REVIEWS_FAVORITE_COUNT");
					
					reviewsList.add(new Review(id, userId, pinId, title, content, createdDate, likeCounts));
				}
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reviewsList;
	}
	
	public boolean addReview (Review rev) {
		final String sql = "INSERT INTO REVIEWS(USERS_USER_ID, PINS_ID, REVIEW_TITLE, REVIEW_CONTENTS) "
				+		   "VALUES (?, ?, ?, ?)";
		
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, rev.getUserId());
			stmt.setInt(2, rev.getPinId());
			stmt.setString(3, rev.getTitle());
			stmt.setString(4, rev.getContent());

			return stmt.executeUpdate() == 1;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteReview (int revId) {
		final String deleteReviewsSql = "DELETE FROM REVIEWS WHERE ID = ?";
		final String deleteReviewsFavoriteSql = "DELETE FROM REVIEWS_FAVORITE WHERE REVIEWS_ID = ?";
		
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

			// トランザクション開始
			conn.setAutoCommit(false);

			// ① 該当レビューのお気に入り削除
			try (PreparedStatement ptStmt = conn.prepareStatement(deleteReviewsFavoriteSql)) {
				ptStmt.setInt(1, revId);
				ptStmt.executeUpdate();
			}

			// ② 該当レビューの削除
			try (PreparedStatement pfStmt = conn.prepareStatement(deleteReviewsSql)) {
				pfStmt.setInt(1, revId);
				pfStmt.executeUpdate();
			}
			
			conn.commit();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean editReview (Review rev) {
		final String sql = "UPDATE REVIEWS SET REVIEW_TITLE = ? , REVIEW_CONTENTS = ?"
				+ "WHERE ID = ?";
		
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, rev.getTitle());
			stmt.setString(2, rev.getContent());
			stmt.setInt(3, rev.getPinId());

			return stmt.executeUpdate() == 1;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<Review> dispYourReviews (String loginUserId) {
		List<Review> reviewsList = new ArrayList<>();
		final String sql = 
				"SELECT "
			    +"R.ID,"
			    +"R.USERS_USER_ID,"
			    +"R.PINS_ID,"
			    +"R.REVIEW_TITLE,"
			    +"R.REVIEW_CONTENTS,"
			    +"R.REVIEW_TIMESTAMP,"
			    +"COALESCE(COUNT(RF.ID), 0) AS REVIEWS_FAVORITE_COUNT "
			    +"FROM REVIEWS R "
			    +"LEFT JOIN REVIEWS_FAVORITE RF "
			    +"ON R.ID = RF.REVIEWS_ID "
			    +"WHERE R.USERS_USER_ID = ? "
			    +"GROUP BY "
			    +"R.ID,"
			    +"R.USERS_USER_ID,"
			    +"R.PINS_ID,"
			    +"R.REVIEW_TITLE,"
			    +"R.REVIEW_CONTENTS,"
			    +"R.REVIEW_TIMESTAMP";
		
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
		         PreparedStatement stmt = conn.prepareStatement(sql)) {

		        stmt.setString(1, loginUserId);

		        try (ResultSet rs = stmt.executeQuery()) {

		            while (rs.next()) {
		                int id = rs.getInt("ID");
		                String userId = rs.getString("USERS_USER_ID");
		                int pinId = rs.getInt("PINS_ID");
		                String title = rs.getString("REVIEW_TITLE");
		                String contents = rs.getString("REVIEW_CONTENTS");
		                Date timeStamp = rs.getDate("REVIEW_TIMESTAMP");
		                int likeCounts = rs.getInt("REVIEWS_FAVORITE_COUNT");

		                reviewsList.add(
		                    new Review(id, userId, pinId, title, contents, timeStamp, likeCounts)
		                );
		            }
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return reviewsList;
	}
}
