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

import com.fasterxml.jackson.databind.ObjectMapper;

import model.Event;
import model.EventLogic;
import model.Pin;
import model.PinLogic;
import model.User;

/**
 * Servlet implementation class main
 */
@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Main() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//仮
//		String userId = "ADMIN";
//		String password = "DUMY0000";
//		String userId = "54321";
//		String password = "54321";
//		User loginUser = new User(userId, password);
//		User loginUser = null;
//		HttpSession session = request.getSession();
//		session.setAttribute("loginUser", loginUser);
		
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		
		System.out.println("loginUserId = " + loginUser);
		
		PinLogic pl = new PinLogic();
		List<Pin> pinsList = pl.disp();
		
		ObjectMapper mapper = new ObjectMapper();
        String pinsListJson = mapper.writeValueAsString(pinsList);
        System.out.println(pinsList.getFirst().getLat()+","+pinsList.getFirst().getLon());
        request.setAttribute("pinsListJson", pinsListJson);

		EventLogic el = new EventLogic();
		el.editStatus();
		List<Event> eventsList = el.disp();
		
		String eventsListJson = mapper.writeValueAsString(eventsList);
        request.setAttribute("eventsListJson", eventsListJson);
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
