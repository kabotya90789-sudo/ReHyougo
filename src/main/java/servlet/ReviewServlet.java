package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.Reviews_FavoriteDAO;
import model.Review;
import model.ReviewLogic;
import model.User;

/**
 * Servlet implementation class ReviewServlet
 */
@WebServlet("/ReviewServlet")
public class ReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReviewServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		String id = request.getParameter("id");
		PrintWriter out = response.getWriter();

		int pinId;
		try {
			pinId = Integer.parseInt(id);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			out.print("{\"success\":false,\"message\":\"invalid id\"}");
			return;
		}
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");
		String userId = "";
		if(user != null) {
		userId = user.getUserId();
		//        String userId = "urtrasoul";
		}
		ReviewLogic rl = new ReviewLogic();
		List<Review> reviewsList = rl.view(pinId);
		List<Review> newReviewsList = new ArrayList<>();
		boolean isFav;
		for (Review review : reviewsList) {
			Reviews_FavoriteDAO rfDao = new Reviews_FavoriteDAO();
			try {
				isFav = rfDao.checkReviewsFavorite(review.getId(), userId);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			Review newReview = new Review(review.getId(), review.getUserId(), review.getPinId(), review.getTitle(),
					review.getContent(), review.getCreatedDate(), review.getLikeCounts(), isFav);
			newReviewsList.add(newReview);
		}
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> result = new HashMap<>();
		result.put("success", true);
		result.put("data", newReviewsList);

		String json = mapper.writeValueAsString(result);
		out.print(json);
		out.flush();
//		String reviewsListJson = mapper.writeValueAsString(newReviewsList);
//		out.print(reviewsListJson);
//		out.flush();
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
