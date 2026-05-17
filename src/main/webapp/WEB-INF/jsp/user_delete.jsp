<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reひょうご</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user_delete.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypage_logout.css">

</head>
<header class="header">
    <div class="header_container">
        <h1 class="header_logo"><a href="${pageContext.request.contextPath}/Main">Reひょうご</a></h1>
        <form action="/search" method="get" class="search_form">
            <input type="search" class="search_bar" name="q" placeholder="検索...">
            <button type="submit" class="search_btn">検索</button>
        </form>
        <button type="button" class="hamburger_menu_btn">
            <span></span>
        </button>
        <nav class="nav">
            <ul class="nav_list">
                <li class="nav_item"><a href="${pageContext.request.contextPath}/Main">トップ</a></li>
                <li class="nav_item"><a href="${pageContext.request.contextPath}/Mypage">マイページ</a></li>
                <li class="nav_item"><a href="${pageContext.request.contextPath}/Logout">ログアウト</a></li>
                <li class="nav_item"><a href="${pageContext.request.contextPath}/Help">ヘルプ</a></li>
            </ul>
        </nav>
    </div>
</header>

<body>
    <main>
       
            <div class="user_delete_form">
                <h2 class="user_delete_ttl">退会</h2>
                <hr>
                <div class="delete_notice">
                    <ul class="notice_list">
                        <p class="main_msg">あなたのアカウントが削除されます</p>
                        <li class="notice_list_item">
                            作成されたピンや投稿したレビューは削除されず、
                            匿名投稿として残り続けます。
                        </li>
                        <li class="notice_list_item">
                            アカウント削除後は作成されたピンや
                            投稿したレビューに変更を加えたり、
                            削除することはできません
                        </li>
                        <li class="notice_list_item">
                            同じユーザーIDでアカウントを
                            再度作成することはできません。
                        </li>
                    </ul>
                </div>
                <div class="delete_submit_area">
                    <button type="button" class="delete_btn" id="final_delete_open_btn">削除</button>
                    <!-- マイページへ戻る -->
                    <a href="${pageContext.request.contextPath}/Mypage" class="delete_back">戻る</a>
                </div>
            </div>
    </main>
    
     <form action="${pageContext.request.contextPath}/UserDelete" id="delete_form" method="post"></form>
    <div class="logout"></div>
    <script src="${pageContext.request.contextPath}/js/header.js"></script>
     <script src="${pageContext.request.contextPath}/js/mypage_logout.js"></script>
</body>

</html>