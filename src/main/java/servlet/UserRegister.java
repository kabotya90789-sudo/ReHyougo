package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.UsersDAO;
import model.HashingLogic;
import model.User;

@WebServlet("/UserRegister")
public class UserRegister extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // GET：入力画面の表示
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        
        // ★ 修正ポイント：ログイン中なら新規登録させずにトップへ戻す（誤操作救済）
        if (session.getAttribute("loginUser") != null) {
            response.sendRedirect(request.getContextPath() + "/Main");
            return;
        }

        String action = request.getParameter("action");
        if ("back".equals(action)) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        // 通常アクセス（未ログインの人だけがここに来る）
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/user_register.jsp");
        dispatcher.forward(request, response);
    }

    // POST：入力チェック → 重複チェック → 確認画面 → 完了
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        // 同意チェックの取得
        String agree = request.getParameter("agree");

        // 登録完了処理
        if ("done".equals(action)) {
            HttpSession session = request.getSession();
            User registerUser = (User) session.getAttribute("registerUser");

            if (registerUser == null) {
                response.sendRedirect("UserRegister");
                return;
            }
            
            HashingLogic hl = new HashingLogic();
            registerUser = hl.hashing(registerUser);

            UsersDAO dao = new UsersDAO();
            dao.registerUser(registerUser);
        
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/registerDone.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
        // 入力値取得
        String id = request.getParameter("userId");
        String pass = request.getParameter("password");
        String passConf = request.getParameter("passwordConfirm");
        
        
        // 入力エラーチェック（同意チェックが必要ならここにも条件を追加してください）
        if (id == null || id.isEmpty() ||
        	    pass == null || pass.isEmpty() ||
        	    !pass.equals(passConf) ||
        	    agree == null) {

        	    request.setAttribute("errorMsg", "未入力があるか、パスワードが一致しません。");

        	    RequestDispatcher dispatcher =
        	            request.getRequestDispatcher("/WEB-INF/jsp/user_register.jsp");
        	    dispatcher.forward(request, response);
        	    return;
        	}

        // ID 重複チェック
        UsersDAO dao = new UsersDAO();
        User tempUser = new User(id, pass);
        boolean available = dao.duplicationCheckUser(tempUser);

        if (!available) {
            request.setAttribute("errorMsg", "このユーザーIDは既に使用されています。");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/user_register.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // 入力内容を保持して確認画面へ
        User registerUser = new User(id, pass);
        HttpSession session = request.getSession();
        session.setAttribute("registerUser", registerUser);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/registerConfirm.jsp");
        dispatcher.forward(request, response);
    } 
}
