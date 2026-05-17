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
 * Servlet implementation class ReviewEdit
 */
@MultipartConfig
@WebServlet("/ReviewEdit")
public class ReviewEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReviewEdit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json;charset=UTF-8");
	    
	    PrintWriter out = response.getWriter();
	    
	    String userId = request.getParameter("userId"); //hidden
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
	    
	    try {
			String reviewId = request.getParameter("reviewId"); //hidden
			String pinId = request.getParameter("pinId"); //hidden
			String title = request.getParameter("reviewTitle");
			String content = request.getParameter("reviewContent");
			System.out.println(reviewId);
			if (reviewId == null || reviewId.isEmpty()) {
				out.write("{\"success\":false,\"message\":\"reviewIdが不正です\"}");
		        return;
			}
			int intReviewId;
			try {
			    intReviewId = Integer.parseInt(reviewId);
			} catch (NumberFormatException e) {
				out.write("{\"success\":false,\"message\":\"reviewId形式エラー\"}");
	            return;
			}
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
			

	        Review editReview = new Review(intReviewId, userId, intPinId, title, content);
			ReviewLogic rl = new ReviewLogic();
			boolean isEdit = rl.edit(editReview);
			if (isEdit) {
				out.write("{\"success\":true}");
			}
			else {
				out.write("{\"success\":false,\"message\":\"更新に失敗しました\"}");
			}
	    } catch (Exception e) {
	        e.printStackTrace();
	        out.write("{\"success\":false,\"message\":\"サーバーエラー\"}");
	    }
	}
}
