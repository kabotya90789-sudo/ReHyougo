<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.User" %>

<%
    // キャッシュ無効化（戻るボタン対策）
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    // セッションからユーザー取得
    User loginUser = (User) session.getAttribute("loginUser");
    // ログインしているかどうかの判定（nullチェック + IDが入っているか）
    boolean isLoggedIn = (loginUser != null && loginUser.getUserId() != null && !loginUser.getUserId().isEmpty());
%>

<header class="header">
    <div class="header_container">

        <h1 class="header_logo">
            <a href="${pageContext.request.contextPath}/Main">Reひょうご</a>
        </h1>

        <div class="search_box">
            <form action="${pageContext.request.contextPath}/Search" method="get" class="search_form">
                <button type="button" class="search_icon_btn">
                    <img alt="検索" class="search_icon" src="${pageContext.request.contextPath}/img/search.svg">
                </button>
                <input type="search" class="search_bar" name="q" placeholder="検索...">
                <button type="submit" class="search_btn">検索</button>
            </form>

            <div class="suggest_area">
                <div class="divider"></div>
                <div class="tag_grid">
                    <button class="tag_btn">観光</button>
                    <button class="tag_btn">グルメ</button>
                    <button class="tag_btn-event">イベント</button>
                </div>
            </div>
        </div>

        <div class="header_right">
            <div class="login_status">
                <% if (!isLoggedIn) { %>
                    <span class="status_text">ログインしていません</span>
                <% } else { %>
                    <span class="status_text"><%= loginUser.getUserId() %> さんログイン中</span>
                <% } %>
            </div>

            <button type="button" class="hamburger_menu_btn">
                <span></span>
            </button>
        </div>

    </div>
</header>

<nav class="nav">
    <ul class="nav_list">
        <% if (!isLoggedIn) { %>
            <%-- 未ログイン時のメニュー --%>
            <li class="nav_item"><a href="${pageContext.request.contextPath}/Main">トップ</a></li>
            <li class="nav_item"><a href="${pageContext.request.contextPath}/Signup">新規登録</a></li>
            <li class="nav_item"><a href="${pageContext.request.contextPath}/Login">ログイン</a></li>
            <li class="nav_item"><a href="${pageContext.request.contextPath}/Help">ヘルプ</a></li>
        <% } else { %>
            <%-- ログイン済みのメニュー --%>
            <li class="nav_item"><a href="${pageContext.request.contextPath}/Main">トップ</a></li>
            <li class="nav_item"><a href="${pageContext.request.contextPath}/Mypage">マイページ</a></li>
            <li class="nav_item"><a id="logoutLink" href="#">ログアウト</a></li>
            <li class="nav_item"><a href="${pageContext.request.contextPath}/Help">ヘルプ</a></li>
        <% } %>
    </ul>
</nav>
