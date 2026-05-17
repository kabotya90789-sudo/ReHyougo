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

import dao.Events_FavoriteDAO;
import dao.Pins_FavoriteDAO;
import model.Event;
import model.Pin;
import model.User;

/**
 * Servlet implementation class MyBookmark
 */
@WebServlet("/MyBookmark")
public class MyBookmark extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyBookmark() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 1. セッションからユーザー情報を取得
	    HttpSession session = request.getSession();
	    User loginUser = (User) session.getAttribute("loginUser");
	    
	    // マイページ経由なのでloginUserは存在する前提
	    String userId = loginUser.getUserId();

	    //スポットピンを取得
	    Pins_FavoriteDAO pinDao = new Pins_FavoriteDAO();
	    List<Pin> favoritePins = pinDao.dispYourFavoritePins(userId);
	    
	    //スポットピンを取得
	    Events_FavoriteDAO eventDao = new Events_FavoriteDAO();
	    List<Event> favoriteEvents = eventDao.dispYourFavoriteEvents(userId);

	    // 3. リクエストスコープに「favoritePins」という名前で保存
	    request.setAttribute("favoritePins", favoritePins);
	    request.setAttribute("favoriteEvents", favoriteEvents);

	    // 4. 一覧表示用のJSPへフォワード
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/my_bookmark.jsp");
        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
