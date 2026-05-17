<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.Pin, model.Event"%>
<%
List<Pin> favoritePins = (List<Pin>) request.getAttribute("favoritePins");
List<Event> favoriteEvents = (List<Event>) request.getAttribute("favoriteEvents");
%>
<%@ page import="model.User"%>
<%
User loginUser = (User) session.getAttribute("loginUser");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Reひょうご</title>
<!--    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">-->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/data/css/my_bookmark.css">


</head>
<body>
	<%
	if (loginUser != null) {
	%>
	<input type="hidden" id="loginUserId"
		value="<%=loginUser.getUserId()%>">
	<%
	} else {
	%>
	<input type="hidden" id="loginUserId" value="">
	<%
	}
	%>

	<div class="result_container">
		<section class="search_sec">
			<div class="item">
				<div class="title_wrapper">
					<h1 id="display_title">ブックマークしたスポット</h1>
					<input type="checkbox" id="toggle_switch" class="toggle"
						style="--size: 64px;">
				</div>
			</div>

			<%-- スポット用 --%>
			<div id="spot_list_container">
				<%
				if (favoritePins != null && !favoritePins.isEmpty()) {
				%>
				<%
				for (Pin pin : favoritePins) {
				%>
				<div class="item">
					<a href="#" class="item_link">
						<div class="text_area">
							<div class="title_area">
								<h2 class="search_name"><%=pin.getName()%></h2>
								<%
								if (pin.getTags() != null && !pin.getTags().isEmpty()) {
								%>
								<%
								for (String tag : pin.getTags()) {
								%>
								<button class="search_tag"><%=tag%></button>
								<%} //end tag for %>
								<%
								}
								%>

							</div>
							<p class="address"><%=pin.getAddress()%></p>
							<p class="desc"><%=pin.getInfo()%></p>
						</div>
						<div class="right_area">
							<div class="search_img">
								<img src="<%=request.getContextPath() + pin.getImageUrl()%>">
							</div>
							<span class="search_date">作成日:<%=pin.getCreatedDate()%></span>
						</div>
					</a>
					<button class="search_bookmark is-active">
						<img src="data/img/bookmark-off.svg" class="icon off"> <img
							src="data/img/bookmark-on.svg" class="icon on">
					</button>
				</div>
				<%
				}
				%>
				<%
				} else {
				%>
				<div class="item">
					<p style="padding: 20px; text-align: center;">ブックマークしたスポットはありません。</p>
				</div>
				<%
				}
				%>
			</div>

			<%-- イベント用 --%>
			<div id="event_list_container" style="display: none;">
				<%
				if (favoriteEvents != null && !favoriteEvents.isEmpty()) {
				%>
				<%
				for (Event event : favoriteEvents) {
				%>
				<div class="item">
					<a href="#" class="item_link">
						<div class="text_area">
							<div class="title_area">
								<h2 class="search_name"><%=event.getName()%></h2>
								<button class="search_tag-event"
									style="font-size: 16px; height: 36px;">イベント</button>
							</div>
							<p class="address"><%=event.getAddress()%></p>
							<p class="desc"><%=event.getInfo()%></p>
						</div> <%-- ← text_area の閉じタグ --%>

						<div class="right_area">
							<div class="search_img">
								<img src="<%=request.getContextPath() + event.getImageUrl()%>">
							</div>
							<span class="search_date">作成日:<%=event.getCreatedDate()%></span>
						</div>
					</a>
					<%-- ← ここで item_link を閉じる --%>

					<button class="search_bookmark is-active">
						<img src="data/img/bookmark-off.svg" class="icon off"> <img
							src="data/img/bookmark-on.svg" class="icon on">
					</button>
				</div>
				<%
				}
				%>
				<%
				} else {
				%>
				<div class="item">
					<p style="padding: 20px; text-align: center;">ブックマークしたイベントはありません。</p>
				</div>
				<%
				}
				%>
			</div>
		</section>
	</div>
	<header class="header"></header>
	<script src="${pageContext.request.contextPath}/data/js/my_bookmark.js"></script>
	<script src="${pageContext.request.contextPath}/js/header.js"></script>
</body>
</html>