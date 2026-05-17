package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;

public class UsersDAO {

    private static final String JDBC_URL =
        "jdbc:mysql://localhost/rehyogo?characterEncoding=UTF-8&serverTimezone=Asia/Tokyo";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root";

    public UsersDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("JDBCドライバを読み込めませんでした", e);
        }
    }

    // ログイン認証
    public boolean searchLoginUser(User user) {

        final String sql =
            "SELECT 1 FROM USERS WHERE MST_USER_ID = ? AND USER_PASSWORD = ? AND USERS_STATUS_ID = 1 ";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUserId());
            stmt.setString(2, user.getPassword());

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ユーザーID重複チェック
    public boolean duplicationCheckUser(User user) {

        final String sql =
            "SELECT 1 FROM USERS WHERE MST_USER_ID = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUserId());

            try (ResultSet rs = stmt.executeQuery()) {
                return !rs.next(); // 存在しなければ true
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ユーザー登録
    public boolean registerUser(User user) {

        final String sql =
            "INSERT INTO USERS (MST_USER_ID, USER_PASSWORD) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUserId());
            stmt.setString(2, user.getPassword());

            return stmt.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // パスワード変更
    public boolean changeUserPass(User user) {

        final String sql =
            "UPDATE USERS SET USER_PASSWORD = ? WHERE MST_USER_ID = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getPassword());
            stmt.setString(2, user.getUserId());

            return stmt.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 退会処理
    public boolean quitUser(User user) {

        final String sql =
            "UPDATE USERS SET USERS_STATUS_ID = 2 WHERE MST_USER_ID = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUserId());

            return stmt.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
