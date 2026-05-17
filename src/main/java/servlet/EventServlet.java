package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Event;
import model.EventLogic;
import model.User;

/**
 * Servlet implementation class EventServlet
 */
@WebServlet("/EventServlet")
public class EventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EventServlet() {
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

		int eventid;
		try {
			eventid = Integer.parseInt(id);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			out.print("{\"error\":\"invalid id\"}");
			return;
		}
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");
		String userId = "";
		if (user != null) {
			userId = user.getUserId();
		}
		EventLogic el = new EventLogic();
		Event eventDetail = el.view(userId, eventid);

		if (eventDetail == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			out.print("{\"error\":\"not found\"}");
			return;
		}

		double eventLat;
		try {
			eventLat = Double.parseDouble(eventDetail.getLat());
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			out.print("{\"error\":\"invalid lat\"}");
			return;
		}
		double eventLon;
		try {
			eventLon = Double.parseDouble(eventDetail.getLon());
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			out.print("{\"error\":\"invalid lon\"}");
			return;
		}

		String json = "{"
				+ "\"id\":" + eventDetail.getId() + ","
				+ "\"userId\":\"" + escape(eventDetail.getUserId()) + "\","
				+ "\"name\":\"" + escape(eventDetail.getName()) + "\","
				+ "\"address\":\"" + escape(eventDetail.getAddress()) + "\","
				+ "\"url\":\"" + escape(eventDetail.getUrl()) + "\","
				+ "\"lat\":" + eventLat + ","
				+ "\"lon\":" + eventLon + ","
				+ "\"imageUrl\":\"" + escape(eventDetail.getImageUrl()) + "\","
				+ "\"info\":\"" + escape(eventDetail.getInfo()) + "\","
				+ "\"start\":\"" + formatDate(eventDetail.getStart()) + "\","
				+ "\"end\":\"" + formatDate(eventDetail.getEnd()) + "\","
				+ "\"createDate\":\"" + formatDate(eventDetail.getCreatedDate()) + "\","
				+ "\"fav\":" + eventDetail.isFav()
				+ "}";

		out.print(json);
	}

	private String formatDate(Date date) {
		if (date == null)
			return "";
		return new java.text.SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	private String escape(String value) {
		if (value == null)
			return "";
		return value
				.replace("\\", "\\\\")
				.replace("\"", "\\\"")
				.replace("\n", "\\n")
				.replace("\r", "\\r");
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
