package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Pin;
import model.PinLogic;
import model.User;

/**
 * Servlet implementation class PinServlet
 */
@WebServlet("/PinServlet")
public class PinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PinServlet() {
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
			//            out.print("{\"error\":\"invalid id\"}");
			return;
		}
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");
		String userId = "";
		if(user != null) {
			userId = user.getUserId();
		}
		//        String userId = "test";
		PinLogic pl = new PinLogic();
		Pin pinDetail = pl.view(userId, pinId);

		if (pinDetail == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			out.print("{\"success\":false,\"message\":\"not found\"}");
			return;
		}

		double pinLat;
		try {
			pinLat = Double.parseDouble(pinDetail.getLat());
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			out.print("{\"success\":false,\"message\":\"invalid lat\"}");
			return;
		}
		double pinLon;
		try {
			pinLon = Double.parseDouble(pinDetail.getLon());
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			out.print("{\"success\":false,\"message\":\"invalid lon\"}");
			return;
		}

		List<String> pinTagsList = new ArrayList<>();
		for (String tag : pinDetail.getTags()) {
			switch (tag) {
			case "1":
				pinTagsList.add("食べる");
				break;
			case "2":
				pinTagsList.add("遊ぶ");
				break;
			case "3":
				pinTagsList.add("観る");
				break;
			case "4":
				pinTagsList.add("買う");
				break;
			case "5":
				pinTagsList.add("休む");
				break;
			}
		}

		String json = "{"
				+ "\"success\":true,"
				+ "\"id\":" + pinDetail.getId() + ","
				+ "\"userId\":\"" + escape(pinDetail.getUserId()) + "\","
				+ "\"name\":\"" + escape(pinDetail.getName()) + "\","
				+ "\"address\":\"" + escape(pinDetail.getAddress()) + "\","
				+ "\"url\":\"" + escape(pinDetail.getUrl()) + "\","
				+ "\"lat\":" + pinLat + ","
				+ "\"lon\":" + pinLon + ","
				+ "\"imageUrl\":\"" + escape(pinDetail.getImageUrl()) + "\","
				+ "\"info\":\"" + escape(pinDetail.getInfo()) + "\","
				+ "\"tags\":" + toJsonArray(pinTagsList) + ","
				+ "\"createDate\":\"" + formatDate(pinDetail.getCreatedDate()) + "\","
				+ "\"fav\":" + pinDetail.isFav()
				+ "}";

		out.print(json);
	}

	private String toJsonArray(List<String> list) {
		if (list == null)
			return "[]";

		StringBuilder sb = new StringBuilder();
		sb.append("[");

		for (int i = 0; i < list.size(); i++) {
			sb.append("\"").append(escape(list.get(i))).append("\"");
			if (i < list.size() - 1)
				sb.append(",");
		}

		sb.append("]");
		return sb.toString();
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
