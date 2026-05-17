<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.List"%>
<%@ page import="model.User"%>
<%@ page import="model.Pin"%>
<%@ page import="model.Event"%>

<%
User loginUser = (User) session.getAttribute("loginUser");

String user = "-1";

if(loginUser != null) {
	user = loginUser.getUserId();
}

List<Pin> resultPins =
	(List<Pin>) request.getAttribute("resultPins");

List<Event> resultEvents =
	(List<Event>) request.getAttribute("resultEvents");

String keyword =
	(String) request.getAttribute("keyword");
%>

<!DOCTYPE html>
<html lang="ja">

<head>

<meta charset="UTF-8">

<meta name="viewport"
	content="width=device-width, initial-scale=1.0">

<title>Reひょうご</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/result.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

</head>

<body>
<header class="header"></header>
	<% if (loginUser != null) { %>
    <input type="hidden" id="loginUserId" value="<%= loginUser.getUserId() %>">
	<% } else { %>
	    <input type="hidden" id="loginUserId" value="">
	<% } %>
<div class="result_container">

	<section class="search_sec">

		<div class="item">
			<h1 class="section_title">検索結果</h1>
		</div>

		<%
		boolean noPins =
			(resultPins == null || resultPins.isEmpty());

		boolean noEvents =
			(resultEvents == null || resultEvents.isEmpty());
		%>

		<% if(noPins && noEvents) { %>

			<div class="item">
				<p class="no_result">
					該当する検索結果はありませんでした。
				</p>
			</div>

		<% } %>


		<!-- ========================= -->
		<!-- Pins -->
		<!-- ========================= -->

		<% if(resultPins != null) { %>

			<% for(Pin pin : resultPins) { %>

			<div class="item">

				<!-- ========================= -->
				<!-- リンク本体 -->
				<!-- ========================= -->

				<a href="#"
				   class="item_link">

					<div class="text_area">

						<div class="title_area">

							<h2 class="search_name">
								<%= pin.getName() %>
							</h2>

							<%
							List<String> tags = pin.getTags();

							if(tags != null && !tags.isEmpty()) {

								for(String tag : tags) {

									switch(tag) {

										case "1":
							%>

											<button
												type="button"
												class="search_tag">
												食べる
											</button>

							<%
											break;

										case "2":
							%>

											<button
												type="button"
												class="search_tag">
												遊ぶ
											</button>

							<%
											break;

										case "3":
							%>

											<button
												type="button"
												class="search_tag">
												観る
											</button>

							<%
											break;

										case "4":
							%>

											<button
												type="button"
												class="search_tag">
												買う
											</button>

							<%
											break;

										case "5":
							%>

											<button
												type="button"
												class="search_tag">
												休む
											</button>

							<%
											break;
									}
								}
							}
							%>

						</div>

						<p class="address">
							<%= pin.getAddress() %>
						</p>

						<p class="desc">
							<%= pin.getInfo() %>
						</p>

					</div>

					<div class="right_area">

						<div class="search_img">

							<img src="<%=request.getContextPath() + pin.getImageUrl()%>">

						</div>

						<span class="search_date">
							<%= pin.getCreatedDate() %>
						</span>

					</div>

				</a>


				<!-- ========================= -->
				<!-- bookmark -->
				<!-- aタグの外へ出す -->
				<!-- ========================= -->

				<button
					type="button"
					class="search_bookmark <%= pin.isFav() ? "is-active" : "" %>"
					data-userid="<%= user %>"
					data-pinid="<%= pin.getId() %>"
					data-keyword="<%= keyword %>">

					<img
						src="${pageContext.request.contextPath}/img/bookmark-on.svg"
						class="icon on">

					<img
						src="${pageContext.request.contextPath}/img/bookmark-off.svg"
						class="icon off">

				</button>

			</div>

			<% } %>

		<% } %>


		<!-- ========================= -->
		<!-- Events -->
		<!-- ========================= -->

		<% if(resultEvents != null) { %>

			<% for(Event evt : resultEvents) { %>

			<div class="item">

				<a href="#"
				   class="item_link">

					<div class="text_area">

						<div class="title_area">

							<h2 class="search_name">
								<%= evt.getName() %>
							</h2>

						</div>

						<p class="address">
							<%= evt.getAddress() %>
						</p>

						<p class="desc">
							<%= evt.getInfo() %>
						</p>

					</div>

					<div class="right_area">

						<div class="search_img">

							<img src="<%=request.getContextPath() + evt.getImageUrl()%>">

						</div>

						<span class="search_date">
							<%= evt.getCreatedDate() %>
						</span>

					</div>

				</a>


				<!-- ========================= -->
				<!-- bookmark -->
				<!-- ========================= -->

				<button
					type="button"
					class="search_bookmark <%= evt.isFav() ? "is-active" : "" %>"
					data-userid="<%= user %>"
					data-evtid="<%= evt.getId() %>"
					data-keyword="<%= keyword %>">

					<img
						src="${pageContext.request.contextPath}/img/bookmark-on.svg"
						class="icon on">

					<img
						src="${pageContext.request.contextPath}/img/bookmark-off.svg"
						class="icon off">

				</button>

			</div>

			<% } %>

		<% } %>

	</section>

</div>



    <script src="${pageContext.request.contextPath}/js/header.js"></script>
     <script src="${pageContext.request.contextPath}/js/searchResult.js"></script>

</body>
</html>