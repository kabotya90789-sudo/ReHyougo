package model;

import dao.UsersDAO;

public class UserLogic {

    // ログイン判定（従来の true/false 版）
    public boolean login(User user) {
        ValidationLogic vl = new ValidationLogic();
        if (!vl.userVali(user)) {
            return false;
        }

        HashingLogic hl = new HashingLogic();
        User hashedUser = hl.hashing(user);
        UsersDAO dao = new UsersDAO();
        return dao.searchLoginUser(hashedUser);
    }

    // ★ 追加：User を返すログインメソッド（DAO は変更不要）
    public User loginUser(User user) {
        ValidationLogic vl = new ValidationLogic();
        if (!vl.userVali(user)) {
            return null;
        }

        // パスワードをハッシュ化
        HashingLogic hl = new HashingLogic();
        User hashedUser = hl.hashing(user);

        UsersDAO dao = new UsersDAO();
        boolean ok = dao.searchLoginUser(hashedUser);

        if (ok) {
            // ★ DB から取らなくても userId だけで十分
            return new User(user.getUserId(), null);
        }

        return null;
    }

    // 登録判定
    public boolean register(User user) {
        ValidationLogic vl = new ValidationLogic();
        if (!vl.userVali(user)) {
            return false;
        }

        UsersDAO dao = new UsersDAO();
        if (!dao.duplicationCheckUser(user)) {
            return false;
        }

        HashingLogic hl = new HashingLogic();
        User hashedUser = hl.hashing(user);
        return dao.registerUser(hashedUser);
    }

    // 編集
    public boolean edit(User user) {
        ValidationLogic vl = new ValidationLogic();
        if (!vl.userVali(user)) {
            return false;
        }

        HashingLogic hl = new HashingLogic();
        User hashedUser = hl.hashing(user);
        UsersDAO dao = new UsersDAO();
        return dao.changeUserPass(hashedUser);
    }

    // 削除
    public boolean delete(User user) {
        UsersDAO dao = new UsersDAO();
        return dao.quitUser(user);
    }
}
