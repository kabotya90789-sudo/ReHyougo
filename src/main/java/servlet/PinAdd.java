package servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import model.Pin;
import model.PinLogic;
import model.User;

/**
 * Servlet implementation class PinAdd
 */
@MultipartConfig(maxFileSize = 1024 * 1024 * 5, // 5MB (1ファイル)
		maxRequestSize = 1024 * 1024 * 10, // 10MB (全ファイル合計)
		fileSizeThreshold = 1024 * 1024 * 1 // 1MB (これを超えるとディスクへ)
)
@WebServlet("/PinAdd")
public class PinAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PinAdd() {
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
		//ログインチェック
		if (session.getAttribute("loginUser") == null) {
			response.sendRedirect("LoginServlet");
			return;
		}
		String userId = loginUser.getUserId();



		try {
			String name = request.getParameter("pinName");
			String address = request.getParameter("pinAddress");
			String url = request.getParameter("pinUrl");
			String latStr = request.getParameter("pinLat");
			String lonStr = request.getParameter("pinLon");

			double lat = Double.parseDouble(latStr);
			double lon = Double.parseDouble(lonStr);

			// 兵庫県チェック
			if (!isHyogo(lat, lon)) {
				response.setStatus(400);
				response.getWriter().write("{\"success\":false,\"message\":\"兵庫県内のみ登録できます\"}");
				return;
			}

			String info = request.getParameter("pinInfo");
			String[] tags = request.getParameterValues("tags");
			List<String> tagsList = new ArrayList<>();
			if (tags != null) {
				for (String tag : tags) {
					tagsList.add(tag);
				}
			}

			Part filePart = request.getPart("pict");

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

			Pin addPin = new Pin(userId, name, address, url, latStr, lonStr, imageUrl, info, tagsList);
			PinLogic pl = new PinLogic();
			boolean isAdd = pl.add(addPin);
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
