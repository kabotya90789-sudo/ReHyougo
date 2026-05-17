package servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import model.Event;
import model.EventLogic;
import model.User;

/**
 * Servlet implementation class EventAdd
 */
@MultipartConfig(maxFileSize = 1024 * 1024 * 5, // 5MB (1ファイル)
		maxRequestSize = 1024 * 1024 * 10, // 10MB (全ファイル合計)
		fileSizeThreshold = 1024 * 1024 * 1 // 1MB (これを超えるとディスクへ)
)
@WebServlet("/EventAdd")
public class EventAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EventAdd() {
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

		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		if (session.getAttribute("loginUser") == null) {
			response.sendRedirect("LoginServlet");
			return;
		}
		String userId = loginUser.getUserId();

		//ログインチェック
		if (session.getAttribute("loginUser") == null) {
			response.sendRedirect("LoginServlet");
			return;
		}

		try {
			String name = request.getParameter("eventName");
			String address = request.getParameter("eventAddress");
			String url = request.getParameter("eventUrl");
			String latStr = request.getParameter("eventLat");
			String lonStr = request.getParameter("eventLon");
			String info = request.getParameter("eventInfo");
			String start = request.getParameter("eventStart");
			String end = request.getParameter("eventEnd");

			double lat = Double.parseDouble(latStr);
			double lon = Double.parseDouble(lonStr);

			// 兵庫県チェック
			if (!isHyogo(lat, lon)) {
				response.setStatus(400);
				response.getWriter().write("{\"success\":false,\"message\":\"兵庫県内のみ登録できます\"}");
				return;
			}

			Date startDate;
			Date endDate;
			try {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				startDate = formatter.parse(start);
				endDate = formatter.parse(end);
			} catch (ParseException e) {
				//		    throw new ServletException("createDate format error");
				out.write("{\"success\":false,\"message\":\"日付形式エラー\"}");
				return;
			}

			Part filePart = request.getPart("event_pict");

			String imageUrl;

			// ★ファイルがあるかチェック
			if (filePart != null && filePart.getSize() > 0) {

				String originalName = Paths.get(filePart.getSubmittedFileName())
						.getFileName().toString();

				String fileName = UUID.randomUUID() + "_" + originalName;

				String uploadPath = getServletContext().getRealPath("/uploads");
				File dir = new File(uploadPath);
				if (!dir.exists())
					dir.mkdir();

				String fullPath = uploadPath + File.separator + fileName;
				filePart.write(fullPath);

				imageUrl = "/uploads/" + fileName;

			} else {
				// ★デフォルト画像
				imageUrl = "/img/no_img.png";
			}
			Event addEvent = new Event(userId, name, address, url, latStr, lonStr, imageUrl, info, startDate, endDate);
			EventLogic el = new EventLogic();
			boolean isAdd = el.add(addEvent);
			if (isAdd) {
				out.write("{\"success\":true}");
			} else {
				if (imageUrl != null && imageUrl.startsWith("/uploads/")) {
					String oldPath = getServletContext().getRealPath("/") + imageUrl;
					File oldFile = new File(oldPath);
					String uploadDir = getServletContext().getRealPath("/uploads");
					String canonicalPath = oldFile.getCanonicalPath();
					String canonicalUploadDir = new File(uploadDir).getCanonicalPath();
					if (canonicalPath.startsWith(canonicalUploadDir)) {
						if (oldFile.exists()) {
							oldFile.delete();
						}
					}
				}
				out.write("{\"success\":false,\"message\":\"追加に失敗しました\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"success\":false,\"message\":\"サーバーエラー\"}");
		}
	}

	//兵庫チェックのメソッド緯度経度から住所に変換
	private boolean isHyogo(double lat, double lon) {
		try {
			String urlStr = "https://mreversegeocoder.gsi.go.jp/reverse-geocoder/LonLatToAddress"
					+ "?lat=" + lat + "&lon=" + lon;

			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			int status = conn.getResponseCode();
			if (status != 200) {
				return false;
			}

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(conn.getInputStream(), "UTF-8"));

			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			reader.close();

			String json = sb.toString();

			// muniCd を抜き出す
			String key = "\"muniCd\":\"";
			int start = json.indexOf(key);

			if (start == -1) {
				return false;
			}

			start += key.length();
			int end = json.indexOf("\"", start);

			if (end == -1) {
				return false;
			}

			String muniCd = json.substring(start, end);

			// 兵庫県判定（28）
			return muniCd.startsWith("28");

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
