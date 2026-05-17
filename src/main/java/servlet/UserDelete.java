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
 * Servlet implementation class UserDelete
 */
@WebServlet("/UserDelete")
public class UserDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserDelete() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/user_delete.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//確認用
		//System.out.println("到達");
		
		// 1. セッションからログイン中のユーザー情報を取得
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser"); // セッション保存名が "user" の場合

		if (loginUser != null) {
			// Logicクラスをインスタンス化
			UserLogic logic = new UserLogic();
			// Logicのdeleteメソッドを呼ぶ
			boolean result = logic.delete(loginUser);

			if (result) {
				// 3. 退会成功したらセッションを破棄（ログアウト状態にする）
				session.invalidate();
				// 4. メイン画面や「退会完了画面」へリダイレクト
				response.sendRedirect(request.getContextPath() + "/Main");
				return;
			}
		}

		// 失敗した場合やユーザー情報がない場合はマイページなどへ戻す
		response.sendRedirect(request.getContextPath() + "/Mypage");
	}

}
