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

import dao.PinsDAO;
import model.Pin;
import model.User;

/**
 * Servlet implementation class MyPin
 */
@WebServlet("/MyPin")
public class MyPin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyPin() {
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

	    //スポットピンを取得
	    PinsDAO pinsDao = new PinsDAO();
	    List<Pin> pinsList = pinsDao.dispYourPins(userId);
	    
	    // 3. リクエストスコープに「pinsList」という名前で保存
	    request.setAttribute("pinsList", pinsList);
	    
	    // フォワード
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/my_pin.jsp");
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
