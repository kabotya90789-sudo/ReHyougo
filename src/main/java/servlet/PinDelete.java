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

import model.Pin;
import model.PinLogic;
import model.User;

/**
 * Servlet implementation class PinDelete
 */
@WebServlet("/PinDelete")
public class PinDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PinDelete() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

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

		String pinId = request.getParameter("id");

		int intPinId;
		try {
			if (pinId == null || pinId.isEmpty()) {
				//	            throw new Exception("pinId is required");
				out.write("{\"success\":false,\"message\":\"pinIdが不正です\"}");
				return;
			}
			try {
				intPinId = Integer.parseInt(pinId);
			} catch (NumberFormatException e) {
				//		    throw new ServletException("pinId is invalid");
				out.write("{\"success\":false,\"message\":\"pinId形式エラー\"}");
				return;
			}
			PinLogic pl = new PinLogic();
			Pin deletePin = pl.view(userId, intPinId);
			// uploads内に画像があれば削除
			if (deletePin.getImageUrl() != null && deletePin.getImageUrl().startsWith("/uploads/")) {
				String oldPath = getServletContext().getRealPath("/") + deletePin.getImageUrl();
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
			
			boolean isDelete = pl.delete(intPinId);

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
