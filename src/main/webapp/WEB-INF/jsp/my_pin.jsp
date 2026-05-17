<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.Pin, model.User" %>
<%
	List<Pin> pinsList = (List<Pin>) request.getAttribute("pinsList");
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/data/css/my_pin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
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
                <h1 class="section_title">あなたが作成したスポット</h1>
            </div>
            <!-- ここにitem入れていく -->
         <div class="pinlist_container">
             <% if (pinsList != null && !pinsList.isEmpty()) { %>
    			<% for (Pin pins : pinsList) { %>
            <div class="item">
                <a href="#" class="item_link">
                    <div class="text_area">
                        <div class="title_area"><h2 class="search_name"><%= pins.getName() %></h2>


							<% if(pins.getTags() != null && !pins.getTags().isEmpty()) {%>
								<% for(String tag : pins.getTags()) {%>
                            <button class="search_tag"><%= tag %></button>
                            	<%} //end tag for %>
                            <% } %>
                            
                           
                        </div>
                        <div class="desc_area"></div>
                        <p class="address">
                           <%= pins.getAddress() %>
                        </p>
                        <p class="desc">
                            <%= pins.getInfo() %>
                        </p>
                    </div>

                    <div class="right_area">
                        <div class="search_img">
                    		 <img src="<%=request.getContextPath() + pins.getImageUrl()%>">
                        </div>
                        <span class="search_date"><%= pins.getCreatedDate() %></span>
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
            			<p style="padding: 20px; text-align: center;">追加したスポットはまだありません</p>
           			</div>
            <%} %>
		</div>


        </section>
    </div>
    <header class="header"></header>
    <script src="${pageContext.request.contextPath}/data/js/my_pin.js"></script>
    
</body>

</html>