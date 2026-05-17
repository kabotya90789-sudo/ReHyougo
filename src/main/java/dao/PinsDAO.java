package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Pin;

public class PinsDAO {

	private static final String JDBC_URL = "jdbc:mysql://localhost/rehyogo?characterEncoding=UTF-8&serverTimezone=Asia/Tokyo";
	private static final String DB_USER = "root";
	private static final String DB_PASS = "root";

	public PinsDAO() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした", e);
		}
	}

	// ---------------------------------------------------------
	// ピン一覧取得（N+1問題解消版）
	// ---------------------------------------------------------
	public List<Pin> getPinsList() {
		List<Pin> pinsList = new ArrayList<>();

		final String sql = "SELECT P.ID, P.PIN_GEO_X, P.PIN_GEO_Y, " +
				"       COALESCE(RC.REVIEWS_COUNT, 0) AS REVIEWS_COUNT, " +
				"       COALESCE(RFC.REVIEWS_FAVORITE_COUNT, 0) AS REVIEWS_FAVORITE_COUNT, " +
				"       COALESCE(PC.PINS_FAVORITE_COUNT, 0) AS PINS_FAVORITE_COUNT " +
				"FROM PINS P " +
				"LEFT JOIN (SELECT PINS_ID, COUNT(*) AS REVIEWS_COUNT FROM REVIEWS GROUP BY PINS_ID) RC ON P.ID = RC.PINS_ID "
				+
				"LEFT JOIN (SELECT R.PINS_ID, COUNT(*) AS REVIEWS_FAVORITE_COUNT " +
				"           FROM REVIEWS_FAVORITE RF " +
				"           JOIN REVIEWS R ON RF.REVIEWS_ID = R.ID " +
				"           GROUP BY R.PINS_ID) RFC ON P.ID = RFC.PINS_ID " +
				"LEFT JOIN (SELECT PINS_ID, COUNT(*) AS PINS_FAVORITE_COUNT FROM PINS_FAVORITE GROUP BY PINS_ID) PC ON P.ID = PC.PINS_ID";

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				int id = rs.getInt("ID");
				String lat = rs.getString("PIN_GEO_Y");
				String lon = rs.getString("PIN_GEO_X");

				int rc = rs.getInt("REVIEWS_COUNT");
				int rfc = rs.getInt("REVIEWS_FAVORITE_COUNT");
				int pc = rs.getInt("PINS_FAVORITE_COUNT");

				int pinGrow = pc * 3 + rc * 2 + rfc;

				pinsList.add(new Pin(id, lat, lon, pinGrow));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pinsList;
	}

	// ---------------------------------------------------------
	// ピン詳細取得
	// ---------------------------------------------------------
	public Pin getPinDetail(int pinId, String loginUserId) {

		final String sql = "SELECT * FROM PINS WHERE ID = ?";

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, pinId);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {

					Pins_FavoriteDAO pfDAO = new Pins_FavoriteDAO();
					boolean fav = pfDAO.checkPinsFavorite(pinId, loginUserId);

					return new Pin(
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
							fav);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// ---------------------------------------------------------
	// ピン作成
	// ---------------------------------------------------------
	public int createPin(Pin pin) {

	    final String sql = "INSERT INTO PINS(USERS_USER_ID, PIN_NAME, PIN_ADDRESS, PIN_URL, PIN_GEO_X, PIN_GEO_Y, PIN_IMAGE_URL, PIN_INFO) "
	            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	    try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
	         PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

	        stmt.setString(1, pin.getUserId());
	        stmt.setString(2, pin.getName());
	        stmt.setString(3, pin.getAddress());
	        stmt.setString(4, pin.getUrl());
	        stmt.setString(5, pin.getLon());
	        stmt.setString(6, pin.getLat());
	        stmt.setString(7, pin.getImageUrl());
	        stmt.setString(8, pin.getInfo());

	        int insertResult = stmt.executeUpdate();

	        try (ResultSet rs = stmt.getGeneratedKeys()) {
	            if (rs.next() && insertResult == 1) {
	                return rs.getInt(1);
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return -1;
	}


	// ---------------------------------------------------------
	// ピン検索（タグ + キーワード対応 / SQLインジェクション対策済）
	// ---------------------------------------------------------
	public List<Pin> searchPins(List<String> searchWords, List<Integer> tags, String loginUserId) {

		List<Pin> pinsList = new ArrayList<>();
		StringBuilder sql = new StringBuilder("SELECT DISTINCT P.* FROM PINS P ");

		List<Object> params = new ArrayList<>();

		// タグ検索
		if (tags != null && !tags.isEmpty()) {
			sql.append("JOIN PIN_TAGS PT ON P.ID = PT.PINS_ID WHERE PT.TAGS_CATEGORY_ID IN (");
			sql.append("?,".repeat(tags.size()));
			sql.setLength(sql.length() - 1); // カンマを消してる
			sql.append(") ");
			params.addAll(tags);
		} else {
			sql.append("WHERE 1=1 ");
		}

		// キーワード検索
		if (searchWords != null && !searchWords.isEmpty()) {
			for (String word : searchWords) {
				sql.append("AND (P.PIN_NAME LIKE ? OR P.PIN_INFO LIKE ? OR P.PIN_ADDRESS LIKE ?) ");
				params.add("%" + word + "%");
				params.add("%" + word + "%");
				params.add("%" + word + "%");
			}
		}

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
				PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

			// パラメータ設定
			for (int i = 0; i < params.size(); i++) {
				stmt.setObject(i + 1, params.get(i));
			}

			try (ResultSet rs = stmt.executeQuery()) {
				Pins_FavoriteDAO pfDAO = new Pins_FavoriteDAO();
				Pin_TagsDAO ptDAO = new Pin_TagsDAO();

				while (rs.next()) {
					int id = rs.getInt("ID");
					List<String> getTags = ptDAO.getPinTags(id);
					boolean fav = false;
					if(loginUserId == "-1") {
						pinsList.add(new Pin(
								id,
								rs.getString("USERS_USER_ID"),
								rs.getString("PIN_NAME"),
								rs.getString("PIN_ADDRESS"),
								rs.getString("PIN_URL"),
								rs.getString("PIN_GEO_X"),
								rs.getString("PIN_GEO_Y"),
								rs.getString("PIN_IMAGE_URL"),
								rs.getString("PIN_INFO"),
								getTags,
								rs.getDate("PIN_TIMESTAMP"),
								fav));
					} else {
					
						fav = pfDAO.checkPinsFavorite(id, loginUserId);
					
						pinsList.add(new Pin(
								id,
								rs.getString("USERS_USER_ID"),
								rs.getString("PIN_NAME"),
								rs.getString("PIN_ADDRESS"),
								rs.getString("PIN_URL"),
								rs.getString("PIN_GEO_X"),
								rs.getString("PIN_GEO_Y"),
								rs.getString("PIN_IMAGE_URL"),
								rs.getString("PIN_INFO"),
								getTags,
								rs.getDate("PIN_TIMESTAMP"),
								fav));
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return pinsList;
	}

	public boolean editPin(Pin pin) {
		//    	duplicationCheckPinによりピンの名前の重複確認が済んだ前提
		final String sql = "UPDATE PINS SET PIN_NAME = ? , PIN_ADDRESS = ? , PIN_URL = ? , PIN_GEO_X = ? , PIN_GEO_Y = ? , PIN_IMAGE_URL = ? , PIN_INFO = ? "
				+ "WHERE ID = ?";

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, pin.getName());
			stmt.setString(2, pin.getAddress());
			stmt.setString(3, pin.getUrl());
			stmt.setString(4, pin.getLon());
			stmt.setString(5, pin.getLat());
			stmt.setString(6, pin.getImageUrl());
			stmt.setString(7, pin.getInfo());
			stmt.setInt(8, pin.getId());

			return stmt.executeUpdate() == 1;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deletePin(int pinId) {
		final String deletePinSql = "DELETE FROM PINS WHERE ID = ?";
		final String deletePinsFavoriteSql = "DELETE FROM PINS_FAVORITE WHERE PINS_ID = ?";
		final String deletePinTagsSql = "DELETE FROM PIN_TAGS WHERE PINS_ID = ?";
		final String deletePinReviewsFavoriteSql =
			    "DELETE FROM REVIEWS_FAVORITE WHERE REVIEWS_ID IN (SELECT ID FROM REVIEWS WHERE PINS_ID = ?)";
		final String deletePinReviewsSql = "DELETE FROM REVIEWS WHERE PINS_ID = ?";

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

			// トランザクション開始
			conn.setAutoCommit(false);

			// ① 該当ピンのタグ削除
			try (PreparedStatement ptStmt = conn.prepareStatement(deletePinTagsSql)) {
				ptStmt.setInt(1, pinId);
				ptStmt.executeUpdate();
			}

			// ② 該当ピンへのお気に入り情報削除
			try (PreparedStatement pfStmt = conn.prepareStatement(deletePinsFavoriteSql)) {

				pfStmt.setInt(1, pinId);
				pfStmt.executeUpdate();
			}
			
			// ③ 該当ピンのレビューに対するお気に入り情報削除
			try (PreparedStatement pfStmt = conn.prepareStatement(deletePinReviewsFavoriteSql)) {

				pfStmt.setInt(1, pinId);
				pfStmt.executeUpdate();
			}
			
			// ④ 該当ピンのレビューの情報削除
			try (PreparedStatement pfStmt = conn.prepareStatement(deletePinReviewsSql)) {

				pfStmt.setInt(1, pinId);
				pfStmt.executeUpdate();
			}
			
			// ⑤ 該当ピンの削除
			try (PreparedStatement pStmt = conn.prepareStatement(deletePinSql)) {

				pStmt.setInt(1, pinId);
				pStmt.executeUpdate();
			}

			conn.commit();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean duplicationCheckPin(Pin pin) {
		final String sql = "SELECT PIN_NAME FROM PINS WHERE PIN_NAME = ?";

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, pin.getName());

			try (ResultSet rs = stmt.executeQuery()) {
				return !rs.next();
				//		        	データがあればfalse=重複あり なければtrue=重複なし
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Pin> dispYourPins(String loginUserId) {
	    List<Pin> yourPinsList = new ArrayList<>();
	    final String sql = 
	        "SELECT * FROM PINS WHERE USERS_USER_ID = ? ORDER BY PIN_TIMESTAMP DESC";

	    try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, loginUserId);

	        try (ResultSet rs = stmt.executeQuery()) {

	            Pins_FavoriteDAO pfDAO = new Pins_FavoriteDAO();

	            while (rs.next()) {
	                int id = rs.getInt("ID");
	                String userId = rs.getString("USERS_USER_ID");
	                String name = rs.getString("PIN_NAME");
	                String address = rs.getString("PIN_ADDRESS");
	                String url = rs.getString("PIN_URL");
	                String lat = rs.getString("PIN_GEO_Y");
	                String lon = rs.getString("PIN_GEO_X");
	                String imageUrl = rs.getString("PIN_IMAGE_URL");
	                String info = rs.getString("PIN_INFO");
	                Date timeStamp = rs.getDate("PIN_TIMESTAMP");
	                boolean fav = pfDAO.checkPinsFavorite(id, loginUserId);

	                yourPinsList.add(
	                    new Pin(id, userId, name, address, url, lat, lon, imageUrl, info, timeStamp, fav)
	                );
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return yourPinsList;
	}


}
