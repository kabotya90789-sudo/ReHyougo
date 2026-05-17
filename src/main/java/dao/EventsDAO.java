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

import model.Event;


public class EventsDAO {
	//	データベース接続値定義
	private final String JDBC_URL = "jdbc:mysql://localhost/rehyogo?characterEncoding=UTF-8&serverTimezone=Asia/Tokyo";
	private final String DB_USER = "root";
	private final String DB_PASS = "root";
	
	public EventsDAO() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした", e);
		}
	}
	public List<Event> getEventsList () {
		List<Event> eventsList = new ArrayList<>();
		final String sql = "SELECT ID , EVENT_GEO_X , EVENT_GEO_Y , EVENT_IMAGE_URL FROM EVENTS WHERE EVENT_STATUS_ID = 1";
	

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				int id = rs.getInt("ID");
				String lat = rs.getString("EVENT_GEO_Y");
				String lon = rs.getString("EVENT_GEO_X");
				String imgUrl = rs.getString("EVENT_IMAGE_URL");

				eventsList.add(new Event(id, lat, lon, imgUrl));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return eventsList;
	}
	
	public Event getEventDetail (String loginUserId , int evtId) {

		final String sql = "SELECT * FROM EVENTS WHERE ID = ?";

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, evtId);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {

					Events_FavoriteDAO evtDAO = new Events_FavoriteDAO();
					boolean fav = evtDAO.checkEventsFavorite(evtId , loginUserId);

					return new Event(
							rs.getInt("ID"),
							rs.getString("USERS_USER_ID"),
							rs.getString("EVENT_NAME"),
							rs.getString("EVENT_ADDRESS"),
							rs.getString("EVENT_URL"),
							rs.getString("EVENT_GEO_Y"),
							rs.getString("EVENT_GEO_X"),
							rs.getString("EVENT_IMAGE_URL"),
							rs.getString("EVENT_INFO"),
							rs.getDate("EVENT_START"),
							rs.getDate("EVENT_END"),
							rs.getDate("EVENT_TIMESTAMP"),
							fav);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean createEvent (Event evt) {
		final String sql = "INSERT INTO EVENTS(USERS_USER_ID, EVENT_NAME, EVENT_ADDRESS, EVENT_URL, EVENT_GEO_X, EVENT_GEO_Y, EVENT_IMAGE_URL, EVENT_INFO, EVENT_START, EVENT_END ) "
	            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ? ,?)";

	    try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
	         PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

	        stmt.setString(1, evt.getUserId());
	        stmt.setString(2, evt.getName());
	        stmt.setString(3, evt.getAddress());
	        stmt.setString(4, evt.getUrl());
	        stmt.setString(5, evt.getLon());
	        stmt.setString(6, evt.getLat());
	        stmt.setString(7, evt.getImageUrl());
	        stmt.setString(8, evt.getInfo());
	        stmt.setDate(9, new java.sql.Date(evt.getStart().getTime()));
	        stmt.setDate(10, new java.sql.Date(evt.getEnd().getTime()));
	        
	       return stmt.executeUpdate() == 1;
    
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public List<Event> searchEvents (List<String> searchWords , String loginUserId) {
	    List<Event> eventsList = new ArrayList<>();
	    List<Object> params = new ArrayList<>();

	    StringBuilder sql = new StringBuilder(
	        "SELECT DISTINCT E.* FROM EVENTS E WHERE 1=1 "
	    );

	    if (searchWords != null && !searchWords.isEmpty()) {
	        for (String word : searchWords) {
	            sql.append("AND (E.EVENT_NAME LIKE ? OR E.EVENT_INFO LIKE ? OR E.EVENT_ADDRESS LIKE ?) ");
	            params.add("%" + word + "%");
	            params.add("%" + word + "%");
	            params.add("%" + word + "%");
	        }
	    }

	    try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
	         PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

	        for (int i = 0; i < params.size(); i++) {
	            stmt.setObject(i + 1, params.get(i));
	        }

	        try (ResultSet rs = stmt.executeQuery()) {
	            Events_FavoriteDAO evtDAO = new Events_FavoriteDAO();

	            while (rs.next()) {
	                int id = rs.getInt("ID");
	                boolean fav = evtDAO.checkEventsFavorite(id, loginUserId);

	                eventsList.add(new Event( 
	                        id,
	                        rs.getString("USERS_USER_ID"),
	                        rs.getString("EVENT_NAME"),
	                        rs.getString("EVENT_ADDRESS"),
	                        rs.getString("EVENT_URL"),
	                        rs.getString("EVENT_GEO_Y"),
	                        rs.getString("EVENT_GEO_X"),
	                        rs.getString("EVENT_IMAGE_URL"),
	                        rs.getString("EVENT_INFO"),
	                        rs.getDate("EVENT_START"),
	                        rs.getDate("EVENT_END"),
	                        rs.getDate("EVENT_TIMESTAMP"),
	                        fav));
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return eventsList;
	}
	
	public boolean editEvent (Event evt) {
//    	duplicationCheckEventによりイベントの名前の重複確認が済んだ前提
		final String sql = "UPDATE EVENTS SET "
				+ "EVENT_NAME = ? , "
				+ "EVENT_ADDRESS = ? , "
				+ "EVENT_URL = ? , "
				+ "EVENT_GEO_X = ? , "
				+ "EVENT_GEO_Y = ? , "
				+ "EVENT_IMAGE_URL = ? , "
				+ "EVENT_INFO = ? , "
				+ "EVENT_START = ? , "
				+ "EVENT_END = ?"
				+ "WHERE ID = ?";

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, evt.getName());
			stmt.setString(2, evt.getAddress());
			stmt.setString(3, evt.getUrl());
			stmt.setString(4, evt.getLon());
			stmt.setString(5, evt.getLat());
			stmt.setString(6, evt.getImageUrl());
			stmt.setString(7, evt.getInfo());
			stmt.setDate(8, new java.sql.Date(evt.getStart().getTime()));
			stmt.setDate(9, new java.sql.Date(evt.getEnd().getTime()));
			stmt.setInt(10, evt.getId());

			return stmt.executeUpdate() == 1;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean fixEventsEnd () {
		final String sql = "UPDATE EVENTS SET EVENT_STATUS_ID = 2 WHERE EVENT_END < CURDATE()";
		
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.executeUpdate();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean duplicationCheckEvent (Event evt) {
		final String sql = "SELECT EVENT_NAME FROM EVENTS WHERE EVENT_NAME = ? ";

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, evt.getName());

			try (ResultSet rs = stmt.executeQuery()) {
				return !rs.next();
//		        	データがあればfalse=重複あり なければtrue=重複なし
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteEventStatus (int evtId) {
		final String sql = "UPDATE EVENTS SET EVENT_STATUS_ID = 2 WHERE ID = ?";
		
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, evtId);
			
			return stmt.executeUpdate() == 1;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public List<Event> dispYourEvents (String loginUserId) {
		List<Event> yourEventsList = new ArrayList<>();
		final String sql = "SELECT * FROM EVENTS WHERE USERS_USER_ID = ? ORDER BY EVENT_TIMESTAMP DESC";
		
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
		         PreparedStatement stmt = conn.prepareStatement(sql)) {

		        stmt.setString(1, loginUserId);

		        try (ResultSet rs = stmt.executeQuery()) {

		            Events_FavoriteDAO efDAO = new Events_FavoriteDAO();

		            while (rs.next()) {
		                int id = rs.getInt("ID");
		                String userId = rs.getString("USERS_USER_ID");
		                String name = rs.getString("EVENT_NAME");
		                String address = rs.getString("EVENT_ADDRESS");
		                String url = rs.getString("EVENT_URL");
		                String lat = rs.getString("EVENT_GEO_Y");
		                String lon = rs.getString("EVENT_GEO_X");
		                String imageUrl = rs.getString("EVENT_IMAGE_URL");
		                String info = rs.getString("EVENT_INFO");
		                Date start = rs.getDate("EVENT_TIMESTAMP");
		                Date end = rs.getDate("EVENT_TIMESTAMP");
		                Date timeStamp = rs.getDate("EVENT_TIMESTAMP");
		                boolean fav = efDAO.checkEventsFavorite(id, loginUserId);

		                yourEventsList.add(
		                    new Event(id, userId, name, address, url, lat, lon, imageUrl, info, start, end , timeStamp, fav)
		                );
		            }
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		    return yourEventsList;
	
	}
}
