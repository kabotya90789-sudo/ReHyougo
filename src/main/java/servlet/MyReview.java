package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.ReviewsDAO;
import model.Review;
import model.User;

/**
 * Servlet implementation class MyReview
 */
@WebServlet("/MyReview")
public class MyReview extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MyReview() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");

		// マイページ経由なのでloginUserは存在する前提
		String userId = loginUser.getUserId();
		
		// 自分が作成したレビューを取得
		ReviewsDAO reviewDao = new ReviewsDAO();
	    List<Review> myReviews = reviewDao.dispYourReviews(userId);
	    
	    // 3. リクエストスコープに「favoritePins」という名前で保存
	    request.setAttribute("myReviews", myReviews);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/my_review.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
