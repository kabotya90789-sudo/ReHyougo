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

import dao.EventsDAO;
import dao.PinsDAO;
import dao.ReviewsDAO;
import model.Event;
import model.Pin;
import model.Review;
import model.User;

/**
 * Servlet implementation class Mypage
 */
@WebServlet("/Mypage")
public class Mypage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Mypage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
        //ブラウザにキャッシュさせない
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
		
		//ユーザー情報取得
		HttpSession session = request.getSession();
	    User loginUser = (User) session.getAttribute("loginUser");
	    
	   //  ログインしているか
	    if(loginUser == null) {
	    	//ログインしていない場合ログイン画面へ
	    	response.sendRedirect(request.getContextPath() + "/LoginServlet");
	    	return;
	    	
	    } else {
	    	//ログインしていればマイページを表示
	    	
	    	//スポットピンの情報を表示
	    	PinsDAO pinsDAO = new PinsDAO();
	    	List<Pin> myPins = pinsDAO.dispYourPins(loginUser.getUserId()); 
	    	request.setAttribute("myPins", myPins);
	    	
	    	//イベントピンの情報を表示
	    	EventsDAO eventsDAO = new EventsDAO();
	    	List<Event> myEvents = eventsDAO.dispYourEvents(loginUser.getUserId());
	    	request.setAttribute("myEvents", myEvents);
	    	
	    	//ユーザー情報を表示
	    	request.setAttribute("myUser", loginUser);
	    	
	    	//レビュー情報を表示
	    	ReviewsDAO reviewsDAO = new ReviewsDAO();
	    	List<Review> myReviews = reviewsDAO.dispYourReviews(loginUser.getUserId());
	    	request.setAttribute("myReviews", myReviews);
	    	
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/mypage.jsp");
	        dispatcher.forward(request, response);
	       
	    }
	    
//	    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/mypage.jsp");
//        dispatcher.forward(request, response);
	    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}

}
