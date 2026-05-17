package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Pin_TagsDAO {

    private static final String JDBC_URL =
            "jdbc:mysql://localhost/rehyogo?characterEncoding=UTF-8&serverTimezone=Asia/Tokyo";
        private static final String DB_USER = "root";
        private static final String DB_PASS = "root";

        public Pin_TagsDAO() {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("JDBCドライバを読み込めませんでした", e);
            }
        }

	public boolean editPinTags(int pinId , List<Integer> tagsList) {

	    String deleteSql = "DELETE FROM PIN_TAGS WHERE PINS_ID = ?";
	    String insertSql = "INSERT INTO PIN_TAGS (PINS_ID, TAGS_CATEGORY_ID) VALUES (?, ?)";

	    try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

	        // トランザクション開始
	        conn.setAutoCommit(false);

	        // ① 既存タグ削除
	        try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
	            deleteStmt.setInt(1, pinId);
	            deleteStmt.executeUpdate();
	        }

	        // ② 新規タグ挿入
	        try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {

	            for (int tagId : tagsList) {
	                insertStmt.setInt(1, pinId);
	                insertStmt.setInt(2, tagId);
	                insertStmt.addBatch(); //バッチ登録
	            }

	            insertStmt.executeBatch(); //登録したバッチを実行
	        }

	        conn.commit();
	        return true;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	
	public List<String> getPinTags(int pinId) {
	    List<String> tagsList = new ArrayList<>();

	    String sql = "SELECT TAGS_CATEGORY_ID FROM PIN_TAGS WHERE PINS_ID = ?";

	    try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1,pinId);

	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                tagsList.add(rs.getString("TAGS_CATEGORY_ID"));
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return tagsList;
	}
	public boolean createPinTags (int pinId , List<Integer> tagsList) {
		final String sql = "INSERT INTO PIN_TAGS(PINS_ID , TAGS_CATEGORY_ID) VALUES (? , ?)";
		
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {

	            for (int tagId : tagsList) {
	                stmt.setInt(1, pinId);
	                stmt.setInt(2, tagId);
	                stmt.addBatch();
	            }
	            
	            int[] result = stmt.executeBatch();
	            return  result.length == tagsList.size();
			}         
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
