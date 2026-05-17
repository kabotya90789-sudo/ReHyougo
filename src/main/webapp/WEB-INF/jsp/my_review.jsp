<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.Review,  model.Pin, model.Event" %>
	<%@ page import="model.User"%>
<%
User loginUser = (User) session.getAttribute("loginUser");
%>
<%
    List<Review> myReviews = (List<Review>) request.getAttribute("myReviews");
	List<Pin> myPins = (List<Pin>) request.getAttribute("myPins");
	List<Event> myEvents = (List<Event>) request.getAttribute("myEvents");
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
                <h1 class="section_title">あなたが作成したレビュー</h1>
            </div>
            <!-- ここにitem入れていく -->

<!--=========================================================-->
		<div class="review_list_container">
             <% if (myReviews != null && !myReviews.isEmpty()) { %>
              <% for (Review review : myReviews) { %>
                    
					<%
					    // 1. 住所を探す（一つの塊にまとめることでエラーを回避）
					    String displayAddress = "住所不明";
						String displayImageUrl = request.getContextPath() + "/data/img/no_img.png";
					    
					    // Pinから探す
					    if (myPins != null) {
					        for (model.Pin p : myPins) {
					            if (p.getId() == review.getPinId()) {
					                displayAddress = p.getAddress();
					                displayImageUrl = request.getContextPath() + p.getImageUrl();
					                
					                break;
					            }
					        }
					    }
					    
					    // 見つからなければEventから探す
					  /*  if ("住所不明".equals(displayAddress) && myEvents != null) {
					        for (model.Event e : myEvents) {
					            if (e.getId() == review.getEventId()) { 
					                displayAddress = e.getAddress();
					                break;
					            }
					        }
					    }*/
					%>
            <div class="item">
                <a href="#" class="item_link">
                    <div class="text_area">
                        <div class="title_area">
                        	<h2 class="search_name"><%= review.getTitle() %></h2>

<!--                          <p class="my_event_period">-->
<!--                            2026年 4月10日　～　2026年 4月10日-->
                          </p>
                        </div>
                        <div class="desc_area"></div>
<!--                        <p class="address">-->
<%--                           <%= displayAddress%>--%>
<!--                        </p>-->
                        <p class="desc">
                            <%= review.getContent() %>
                        </p>
                    </div>

                    <div class="right_area">
                        <div class="search_img">
                            <img src="<%= displayImageUrl %>">
                        </div>
                        <span class="search_date">
                        	<%= review.getCreatedDate() %>
                        </span>
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
            			<p style="padding: 20px; text-align: center;">レビューはまだありません。</p>
           			</div>
            <% } %>
           </div>


<!--=========================================================-->
        </section>
    </div>
    <header class="header"></header>
    <script src="${pageContext.request.contextPath}/data/js/my_event.js"></script>
     <script src="${pageContext.request.contextPath}/js/header.js"></script>
</body>

</html>