package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

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
 * Servlet implementation class EventDelete
 */
@WebServlet("/EventDelete")
public class EventDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EventDelete() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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

		String eventId = request.getParameter("id");

		int intEventId;
		try {
			if (eventId == null || eventId.isEmpty()) {
				//	            throw new Exception("pinId is required");
				out.write("{\"success\":false,\"message\":\"eventIdが不正です\"}");
				return;
			}
			try {
				intEventId = Integer.parseInt(eventId);
			} catch (NumberFormatException e) {
				//		    throw new ServletException("pinId is invalid");
				out.write("{\"success\":false,\"message\":\"eventId形式エラー\"}");
				return;
			}
			EventLogic el = new EventLogic();
			Event deleteEvent = el.view(userId, intEventId);
			// uploads内に画像があれば削除
			if (deleteEvent.getImageUrl() != null && deleteEvent.getImageUrl().startsWith("/uploads/")) {
				String oldPath = getServletContext().getRealPath("/") + deleteEvent.getImageUrl();
				String uploadDir = getServletContext().getRealPath("/uploads");
				File oldFile = new File(oldPath);
				String canonicalPath = oldFile.getCanonicalPath();
				String canonicalUploadDir = new File(uploadDir).getCanonicalPath();

				// uploads配下だけ削除許可
				if (canonicalPath.startsWith(canonicalUploadDir)) {
					if (oldFile.exists()) {
						oldFile.delete();
					}
				}
			}

			boolean isDelete = el.delete(intEventId);

			if (isDelete) {
				out.write("{\"success\":true}");
			} else {
				out.write("{\"success\":false,\"message\":\"削除に失敗しました\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"success\":false,\"message\":\"サーバーエラー\"}");
		}
	}

}
