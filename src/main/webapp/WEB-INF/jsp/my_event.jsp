<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.Event, model.User" %>
<%
	List<Event> eventsList = (List<Event>) request.getAttribute("eventsList");
%>
<%
	User loginUser = (User) session.getAttribute("loginUser");
%>
<!DOCTYPE html>
<html lang="ja">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reひょうご</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/data/css/my_event.css">
</head>

<body>
		<% if (loginUser != null) { %>
    <input type="hidden" id="loginUserId" value="<%= loginUser.getUserId() %>">
	<% } else { %>
	    <input type="hidden" id="loginUserId" value="">
	<% } %>
	
    <div class="result_container">
        <section class="search_sec">
            <div class="item">
                <h1 class="section_title">あなたが作成したイベント</h1>
            </div>
            <!-- ここにitem入れていく -->

<!--=========================================================-->
    <div class="pinlist_container">
    	<% if (eventsList != null && !eventsList.isEmpty()) { %>
    		<% for (Event events : eventsList) { %>
            <div class="item">
                <a href="#" class="item_link">

                    <div class="text_area">
                        <div class="title_area">
                        	<h2 class="search_name">
                        		<%=events.getName() %>
                        	</h2>

                          <p class="my_event_period">
                           <%= events.getStart() %>～<%= events.getEnd() %>
                          </p>
                         
                           
                        </div>
                        <div class="desc_area"></div>
                        <p class="address">
                           <%= events.getAddress() %>
                        </p>
                        <p class="desc">
                            <%= events.getInfo() %>
                        </p>
                    </div>

                    <div class="right_area">
                        <div class="search_img">
							<img src="<%=request.getContextPath() + events.getImageUrl()%>">	
                        </div>
                        <span class="search_date"><%= events.getCreatedDate() %></span>
                    </div>

                </a>
                

                <button class="search_bookmark">
                    <img src="${pageContext.request.contextPath}/data/img/bookmark-off.svg" class="icon off">
                    <img src="${pageContext.request.contextPath}/data/img/bookmark-on.svg" class="icon on">
                </button>
            </div>
			<% } // end for%>
			<% }else{ %>
					 <div class="item">
					 	<p style="padding: 20px; text-align: center;">追加したイベントはまだありません</p>
					 </div>
			<%} %>
		</div>
        </section>
    </div>
    <header class="header"></header>
    <script src="${pageContext.request.contextPath}/data/js/my_event.js"></script>
    
</body>

</html>