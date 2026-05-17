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
 * Servlet implementation class PinEdit
 */
@MultipartConfig(maxFileSize = 1024 * 1024 * 5, // 5MB (1ファイル)
		maxRequestSize = 1024 * 1024 * 10, // 10MB (全ファイル合計)
		fileSizeThreshold = 1024 * 1024 * 1 // 1MB (これを超えるとディスクへ)
)
@WebServlet("/PinEdit")
public class PinEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PinEdit() {
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

		try {
			String pinId = request.getParameter("id"); //hidden
			String name = request.getParameter("name");
			String address = request.getParameter("address");
			String url = request.getParameter("url");
			String latStr = request.getParameter("lat");
			String lonStr = request.getParameter("lon");
			String info = request.getParameter("info");
			String[] tags = request.getParameterValues("tags");

			double lat = Double.parseDouble(latStr);
			double lon = Double.parseDouble(lonStr);

			// 兵庫県チェック
			if (!isHyogo(lat, lon)) {
				response.setStatus(400);
				response.getWriter().write("{\"success\":false,\"message\":\"兵庫県内のみ登録できます\"}");
				return;
			}

			//			String createDate = request.getParameter("pinCreateDate");//hidden
			List<String> tagsList = new ArrayList<>();
			if (tags != null) {
				for (String tag : tags) {
					tagsList.add(tag);
				}
			}

			if (pinId == null || pinId.isEmpty()) {
				//		    throw new ServletException("pinId is required");
				out.write("{\"success\":false,\"message\":\"pinIdが不正です\"}");
				return;
			}

			int intPinId;
			try {
				intPinId = Integer.parseInt(pinId);
			} catch (NumberFormatException e) {
				//		    throw new ServletException("pinId is invalid");
				out.write("{\"success\":false,\"message\":\"pinId形式エラー\"}");
				return;
			}

			PinLogic pl = new PinLogic();
			Pin bfPin = pl.view(userId, intPinId);

			Part filePart = request.getPart("pict");

			String imageUrl = bfPin.getImageUrl();

			boolean deleteImage = "true".equals(request.getParameter("deleteImage"));

			if (deleteImage) {

				imageUrl = "/img/no_img.png";

			} else if (filePart != null && filePart.getSize() > 0) {
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

				// 変更なし
			} else {
				imageUrl = bfPin.getImageUrl();
			}

			Pin editPin = new Pin(intPinId, userId, name, address, url, latStr, lonStr, imageUrl, info, tagsList);

			boolean isEdit = pl.edit(bfPin, editPin);

			if (isEdit) {
				//旧画像削除
				if ((deleteImage || (filePart != null && filePart.getSize() > 0))
						&& bfPin.getImageUrl() != null
						&& bfPin.getImageUrl().startsWith("/uploads/")) {

					String oldPath = getServletContext().getRealPath("/") + bfPin.getImageUrl();
					File oldFile = new File(oldPath);
					if (oldFile.exists()) {
						oldFile.delete();
					}
				}
				out.print("{\"success\":true}");
			} else {
				// 新画像削除
				if (imageUrl != null
						&& imageUrl.startsWith("/uploads/")
						&& !imageUrl.equals(bfPin.getImageUrl())) {

					String oldPath = getServletContext().getRealPath("/") + imageUrl;
					File oldFile = new File(oldPath);
					if (oldFile.exists()) {
						oldFile.delete();
					}
				}
				out.print("{\"success\":false,\"message\":\"更新に失敗しました\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.print("{\"success\":false,\"message\":\"サーバーエラー\"}");
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