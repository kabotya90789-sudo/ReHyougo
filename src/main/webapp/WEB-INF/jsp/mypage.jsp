<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ 
    page import="java.util.List, model.User, model.Pin, model.Event, model.Review" 
    %>
    <%
    User myUser = (User) request.getAttribute("myUser");
    List<Pin> myPins = (List<Pin>) request.getAttribute("myPins");
    List<Event> myEvents = (List<Event>) request.getAttribute("myEvents");
    List<Review> myReviews = (List<Review>) request.getAttribute("myReviews");
    %>
    <%
	User loginUser = (User) session.getAttribute("loginUser");
	%>
<!DOCTYPE html>
<html lang="ja">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reひょうご編集</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypage.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypage_logout.css">
</head>

<body>
	<% if (loginUser != null) { %>
    <input type="hidden" id="loginUserId" value="<%= loginUser.getUserId() %>">
	<% } else { %>
	    <input type="hidden" id="loginUserId" value="">
	<% } %>
<header class="header"></header>
    <main>
        <div class="mypage_container">

            <!-- 左:ユーザー情報 -->
            <div class="column">
                <section class="user_sec">
                    <div class="section_title">
                        <h1>ユーザー情報</h1>
                    </div>
                    <div class="user_item">
                        <span class="list_text">
                        ユーザーID：<%= myUser.getUserId() %>
                        </span>
                    </div>

                    <div class="user_item password_row">
                        <span class="list_text"> パスワード：●●●●●●●●</span>
                        <a href="${pageContext.request.contextPath}/UserEdit" class="user_edit_btn">
                            編集
                        </a>
                    </div>

                    <!-- ログアウトと退会ボタン -->
                    <div class="user_list_btn">
                        <a href="${pageContext.request.contextPath}/Logout" id="logout_btn">
                            ログアウト
                        </a>
                        <a href="${pageContext.request.contextPath}/UserDelete" class="user_delete_btn">
                            退会
                        </a>
                    </div>
                </section>

                <!-- ひょうごっちの部屋 -->
                <section class="pet_sec">
                    <div class="section_title">
                        <h1>ひょうごっちの部屋</h1>
                    </div>

<div class="room">	
<img src="${pageContext.request.contextPath}/img/room.png"	       
class="room_img">	
<div id="charWrap" class="char_wrap">	
<img src="${pageContext.request.contextPath}/img/hyougocchi1.png"	
class="char" id="char">	
</div>	
</div>	
<div class="user_info_wraper">	
<div class="user_left">	
<span class=light>あなたがよく行く場所<span class=bold>食べる</span></span>	
<span class=light>性格<span class=bold>くいしんぼ</span></span>	
</div>	
<div class="user_right">	
<span class=light>あなたの作ったスポット<span class=bold>100</span></span>	  
<span class=light>あなたの書いたレビュー<span class=bold>100</span></span>	
<span class=light>あなたのもらったいいね<span class=bold>100</span></span>	
<span class=light>あなたの偏差値<span class=bold>50</span></span>	
<br>	
<span class=light>成長度<span>104</span></span>	
<span class=bold>あと20で成長しそう</span>	
</div>	
</div>
                </section>
            </div>

            <!-- 真ん中：作成したスポットピンとイベントピン -->
            <div class="column center_col">
                <section class="pin_sec">
                    <!-- スポットピン -->

                    <!-- タイトル -->
                    <div class="item">
                        <h1 class="section_title">
                            <img src="${pageContext.request.contextPath}/img/wakaba.svg" alt="" class="icon_wakaba">
                            あなたが作成したスポット
                        </h1>
                    </div>
						
						<!--表示を繰り返す処理-->
				<% if (myPins != null && !myPins.isEmpty()) { %>
					<%
						int i = 0;
						for (Pin pin : myPins) {
							if(i >= 3) {
								break;
							}
					%>
                    <!-- スポット中身 -->
                    <div class="item">
                        <div class="text_area">
                            <div class="title_area">
                                <h2 class="search_name"><%= pin.getName() %></h2>
                            </div>

                            <div class="left_area">
                                <div class="search_img">
                                	<img src="<%=request.getContextPath() + pin.getImageUrl()%>">
                                </div>
                                <span class="search_date">作成日：<%= pin.getCreatedDate() %></span>
                            </div>
                        </div>
                    </div>
                    	<%
                    		i++;
							} //for文 閉じ
						%>
				<% } else {%>
					<div>
						<p>（まだありません）</p>
					</div>
				<%} %>	
					
                    <!-- イベントピン -->

                    <!-- タイトル -->
                    <div class="item">
                        <h1 class="section_title">
                            <img src="${pageContext.request.contextPath}/img/Mask_group.svg" alt="" class="icon_wakaba">
                            あなたが作成したイベント
                        </h1>
                    </div>
							
					<% if (myEvents != null && !myEvents.isEmpty()) { %>
					<%
						int myE = 0;
						for (Event event : myEvents) {
							if( myE >= 3) {
								break;
							}
					%>
                    <!-- イベントピン中身 -->
                    <div class="item">
                        <div class="text_area">
                            <div class="title_area">
                                <h2 class="search_name"><%= event.getName() %></h2>
                            </div>

                            <div class="left_area">
                                <div class="search_img">
                                <img src="<%=request.getContextPath() + event.getImageUrl()%>">
                                </div>
                                <span class="search_date">作成日：<%= event.getCreatedDate() %></span>
                            </div>
                        </div>
                    </div>
					<%
                    		myE++;
							} //for文 閉じ
						%>
				<% } else {%>
					<div>
						<p>（まだありません）</p>
					</div>
				<%} %>	

                    <!-- スポットピンとイベントピンのボタン -->
                    <div class="pin_list_btn">
                        <a href="${pageContext.request.contextPath}/MyPin">スポットの一覧へ</a>
                        <a href="${pageContext.request.contextPath}/MyEvent">イベントの一覧へ</a>
                    </div>
                </section>
            </div>

            <!-- 右：レビューとブックマーク -->
            <div class="column">

                <!-- レビュー -->
                <section class="review_sec">



                    <!-- タイトル -->
                    <div class="item">
                        <h1 class="section_title">
                            <img src="${pageContext.request.contextPath}/img/hukidasi.svg" alt="" class="icon_wakaba">
                            あなたが書いたレビュー
                        </h1>
                    </div>
                    <div class="item">
                    
                    <!--表示を繰り返す処理-->
				<% if (myReviews != null && !myReviews.isEmpty()) { %>
					<%
						int myR = 0;
						for (Review review : myReviews) {
							if( myR >= 3) {
								break;
							}
							
							// レビュー本文一部表示処理
							String content = review.getContent();
							int limit =10;
							
							if (content != null && content.length() > limit) {
								// 指定した文字数で切り取り、末尾に「...」をつける
								content = content.substring(0, limit) + "...";
							}
							
					%>
                        <div class="text_area">
                            <div class="title_area">
                                <h2 class="search_name reveiw_name"><%= review.getTitle() %></h2>
                                <span class="search_date review_date">作成日：<%= review.getCreatedDate() %></span>
                            </div>

                            <div class="desc_area">
                                <p class="desc"><%= content %></p>
                            </div>

                        </div>
                        <%
                    		myR++;
							} //for文 閉じ
						%>
				<% } else {%>
					<div>
						<p>（まだありません）</p>
					</div>
				<%} %>
				
                    </div>
	
                    <div class="review_list_btn">
                        <a href="${pageContext.request.contextPath}/MyReview">レビューの一覧へ</a>
                    </div>
                </section>

                <!-- ブックマーク -->
                <section class="bookmark_sec">

                    <!-- タイトル -->
                    <div class="item">
                        <h1 class="section_title">
                            <img src="${pageContext.request.contextPath}/img/Star.svg" alt="" class="icon_wakaba">
                            あなたのブックマーク
                        </h1>
                    </div>

                        <!-- 一覧ボタン -->
                        <div class="item">
                            <div class="bookmark_list_btn">
                                <a href="${pageContext.request.contextPath}/MyBookmark?type=spot">
                                    <div class="bookmark_img">
                                        <img src="${pageContext.request.contextPath}/img/wakaba.svg" alt="" class="icon_wakaba">
                                    </div>
                                    スポット一覧へ
                                </a>
                                <a href="${pageContext.request.contextPath}/MyBookmark?type=event">
                                    <div class="bookmark_img">
                                        <img src="${pageContext.request.contextPath}/img/Mask_group.svg" alt="" class="icon_wakaba">
                                    </div>
                                    イベントの一覧へ
                                </a>
                            </div>
                        </div>

                </section>
            </div>

        </div>
    </main>
    	<div class="logout"></div>
		<script>
		const contextPath = "${pageContext.request.contextPath}";
		</script>
    	
		<script src="${pageContext.request.contextPath}/js/header.js"></script>
		<script src="${pageContext.request.contextPath}/js/hyougocchi.js"></script>
		<script src="${pageContext.request.contextPath}/js/mypage_logout.js"></script>
</body>

</html>