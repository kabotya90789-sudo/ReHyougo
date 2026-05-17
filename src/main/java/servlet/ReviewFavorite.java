package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.ReviewLikeLogic;
import model.User;

/**
 * Servlet implementation class ReviewFavorite
 */
@WebServlet("/ReviewFavorite")
public class ReviewFavorite extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReviewFavorite() {
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
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		String userId = loginUser.getUserId();

		//ログインチェック
		if (session.getAttribute("loginUser") == null) {
			response.sendRedirect("LoginServlet");
			return;
		}

		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		String id = request.getParameter("reviewId");

		PrintWriter out = response.getWriter();

		int reviewId;
		try {
			reviewId = Integer.parseInt(id);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			out.print("{\"error\":\"invalid id\"}");
			return;
		}

		ReviewLikeLogic rll = new ReviewLikeLogic();
		boolean isSwicth = rll.switchLike(userId, reviewId);
		if (isSwicth) {
			out.write("{\"success\":true}");
		} else {
			out.write("{\"success\":false,\"message\":\"失敗しました\"}");
		}
	}

}
