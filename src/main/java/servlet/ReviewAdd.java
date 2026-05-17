package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Review;
import model.ReviewLogic;
import model.User;

/**
 * Servlet implementation class ReviewAdd
 */
@MultipartConfig
@WebServlet("/ReviewAdd")
public class ReviewAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReviewAdd() {
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

		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		String userId = loginUser.getUserId();

		//ログインチェック
		if (session.getAttribute("loginUser") == null) {
			response.sendRedirect("LoginServlet");
			return;
		}

		try {
			String pinId = request.getParameter("pinId");
			String title = request.getParameter("reviewTitle");
			String content = request.getParameter("reviewContent");
			if (pinId == null || pinId.isEmpty()) {
				//		    throw new ServletException("pinId is required");
				out.write("{\"success\":false,\"message\":\"pinIdが不正です\"}");
				return;
			}
			int intPinId;
			try {
				intPinId = Integer.parseInt(pinId);
			} catch (NumberFormatException e) {
				//		    throw new ServletException("pinId is invalid");
				out.write("{\"success\":false,\"message\":\"pinId形式エラー\"}");
				return;
			}
			Review addReview = new Review(userId, intPinId, title, content);
			ReviewLogic rl = new ReviewLogic();
			boolean isAdd = rl.add(addReview);
			if (isAdd) {
				out.write("{\"success\":true}");
			} else {
				out.write("{\"success\":false,\"message\":\"追加に失敗しました\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"success\":false,\"message\":\"サーバーエラー\"}");
		}
	}

}
