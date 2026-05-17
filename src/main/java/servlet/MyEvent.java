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
import model.Event;
import model.User;

/**
 * Servlet implementation class MyEvent
 */
@WebServlet("/MyEvent")
public class MyEvent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyEvent() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
	    User loginUser = (User) session.getAttribute("loginUser");
	    
	    // マイページ経由なのでloginUserは存在する前提
	    String userId = loginUser.getUserId();

	    //イベントピンを取得
	    EventsDAO eventsDao = new EventsDAO();
	    List<Event> eventsList = eventsDao.dispYourEvents(userId);
	    
	    // 3. リクエストスコープに「eventsList」という名前で保存
	    request.setAttribute("eventsList", eventsList);
	    
	    // フォワード
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/my_event.jsp");
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
