package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.User;
import model.UserLogic;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // 最初にブラウザで「/Login」にアクセスした時にログイン画面を出す
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 「ログイン画面のJSP」を表示
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
        dispatcher.forward(request, response);
    }

    // ログインボタン（送信）が押された時の処理
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("--- ログイン処理開始 ---");

        request.setCharacterEncoding("UTF-8");
        String userId = request.getParameter("userId");
        String pass = request.getParameter("password");
        System.out.println("入力ID: " + userId + " / パス: " + pass);

        User user = new User(userId, pass);

        // ★ loginUser() を使う（User を返す）
        UserLogic loginLogic = new UserLogic();
        User dbUser = loginLogic.loginUser(user);

        if (dbUser != null) {
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", dbUser);

            response.sendRedirect(request.getContextPath() + "/Main");
            return;
        } else {
            response.sendRedirect("LoginServlet");
        }
        }
    }
    
   