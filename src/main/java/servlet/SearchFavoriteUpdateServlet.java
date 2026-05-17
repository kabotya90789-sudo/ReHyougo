package servlet;

import java.io.IOException;
import java.net.URLEncoder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.Events_FavoriteDAO;
import dao.Pins_FavoriteDAO;

/**
 * Servlet implementation class FavoriteUpdateServlet
 */
@WebServlet("/FavoriteUpdateServlet")
public class SearchFavoriteUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchFavoriteUpdateServlet() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    String userId = request.getParameter("userId");
	    
	    if(userId == "-1") {
	    	response.sendRedirect("Login");
	    	return;
	    }
	    
	    String pinIdStr = request.getParameter("pinId");
	    String evtIdStr = request.getParameter("evtId");
	    String action = request.getParameter("action");
	    String keyword = request.getParameter("keyword");
	    
	    System.out.println(userId + " by SearchFavoriteUpdateServlet");

	    int pinId = 0;
	    int evtId = 0;

	    if (pinIdStr != null && !pinIdStr.isEmpty()) {
	        pinId = Integer.parseInt(pinIdStr);
	    }

	    if (evtIdStr != null && !evtIdStr.isEmpty()) {
	        evtId = Integer.parseInt(evtIdStr);
	    }
	    
	    System.out.println("userId=" + userId);
	    System.out.println("pinId=" + pinId);
	    System.out.println("evtId=" + evtId);
	    System.out.println("action=" + action);
	    System.out.println("keyword=" + keyword);

	    // Pin のお気に入り更新
	    if (pinId != 0) {
	        Pins_FavoriteDAO dao = new Pins_FavoriteDAO();
	        dao.updatePinsFavorite(userId, pinId);
	    }

	    // Event のお気に入り更新
	    if (evtId != 0) {
	        Events_FavoriteDAO dao = new Events_FavoriteDAO();
	        dao.updateEventsFavorite(userId, evtId);
	    }
	    

	    // 検索結果に戻る
	    response.sendRedirect("Search?keyword=" + URLEncoder.encode(keyword, "UTF-8"));
	}


}
