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

/**
 * Servlet implementation class UseEdit
 */
@WebServlet("/UserEdit")
public class UserEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserEdit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/user_edit.jsp");
        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    HttpSession session = request.getSession();
	    
	    System.out.println("サーブレットに到達");
	    // ログイン情報を取得
	    User loginUser = (User) session.getAttribute("loginUser");
	    
	    if(loginUser == null) {
	        response.sendRedirect("Login");
	        return;
	    }
	    
	    // jspから値を取得
	    String currentPass = request.getParameter("currentPass");
	    String newPass = request.getParameter("newPass");
	    String newPassConf = request.getParameter("newPassConf");
	    
	    // --- バリデーション ---
	    
	    // 入力された現在のパスワードをハッシュ化する
	    model.HashingLogic hl = new model.HashingLogic();
	    User inputUser = new User(loginUser.getUserId(), currentPass);
	    User hashedInputUser = hl.hashing(inputUser);
	    
	    // 現在のパスワードチェック
	    if (!loginUser.getPassword().equals(hashedInputUser.getPassword())) {
	    	System.out.println("2");
	        request.setAttribute("errorMsg", "現在のパスワードが正しくありません。");
	        doGet(request, response);
	        return;
	    }
	    
	    // 新パスワード一致チェック（! を追加して、一致しない時にエラーにする）
	    if (newPass == null || newPass.isEmpty() || !newPass.equals(newPassConf)) {
	        request.setAttribute("errorMsg", "新しいパスワードが一致しません。");
	        doGet(request, response);
	        return;
	    }
	    
	    // --- 更新実行 ---
	    

	    User updateUser = new User(loginUser.getUserId(), newPass);
	    UserLogic logic = new UserLogic(); // ここを修正！
	    
	    System.out.println("3");
	    boolean isSuccess = logic.edit(updateUser);
	    
	    if(isSuccess) {
	        // 成功（セッション内のパスワードも更新しておく）
	        loginUser.setPassword(newPass);
	        request.setAttribute("editSuccess", true); // JSP側で判定する名前に合わせる
	    } else {
	        request.setAttribute("errorMsg", "変更に失敗しました。");
	    }
	    
	    doGet(request, response);
	    
	    System.out.println("セッションのパスワード：" + loginUser.getPassword());
	    System.out.println("入力された現在のパスワード：" + currentPass);
	}

}
