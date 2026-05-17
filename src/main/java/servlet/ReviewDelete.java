package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.ReviewLogic;
import model.User;

/**
 * Servlet implementation class ReviewDelete
 */
@WebServlet("/ReviewDelete")
public class ReviewDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReviewDelete() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");

		PrintWriter out = response.getWriter();

		String userId = request.getParameter("userId");
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");

		//ログインチェック
		if (loginUser == null) {
			response.sendRedirect("LoginServlet");
			return;
		}
		//権限チェック
		if (!loginUser.getUserId().equals(userId) && !"ADMIN".equals(loginUser.getUserId())) {
			out.write("{\"success\": false, \"message\": \"権限がありません\"}");
			return;
		}

		String reviewId = request.getParameter("id");
		int intReviewId;
		try {
			if (reviewId == null || reviewId.isEmpty()) {
				out.write("{\"success\":false,\"message\":\"reviewIdが不正です\"}");
			}
			try {
				intReviewId = Integer.parseInt(reviewId);
			} catch (NumberFormatException e) {
				out.write("{\"success\":false,\"message\":\"reviewId形式エラー\"}");
				return;
			}
			ReviewLogic el = new ReviewLogic();
			boolean isDelete = el.delete(intReviewId);

			if (isDelete) {
				out.write("{\"success\":true}");
			} else {
				out.write("{\"success\":false,\"message\":\"削除に失敗しました\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"success\":false,\"message\":\"サーバーエラー\"}");
		}
	}
}
