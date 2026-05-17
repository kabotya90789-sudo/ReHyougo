<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reひょうご</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user_edit.css">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypage_logout.css">

</head>
<header class="header">
    <div class="header_container">
        <h1 class="header_logo"><a href="/Main">Reひょうご</a></h1>
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
        <form action="${pageContext.request.contextPath}/UserEdit" id="edit_pass_form" method="post">
            <div class="user_edit_pass_form">
                <h2 class="edit_pass_ttl">パスワード変更</h2>
                    <hr>
                    <div class="field">
                        <label for="edit_pass" class="edit_password_box">現在のパスワード</label>
                        <input type="password" id="edit_pass" name="currentPass" placeholder="半角英数字4文字以上20文字以内">
                    </div>
                    <div class="field">
                        <label for="new_pass" class="new_password_box">新しいパスワード</label>
                        <input type="password" id="new_pass" name="newPass" placeholder="半角英数字4文字以上20文字以内">
                    </div>
                    <div class="field">
                        <label for="new_pass_re" class="new_password_box">新しいパスワード(確認)
                        </label>
                        <input type="password" id="new_pass_re" name="newPassConf" placeholder="半角英数字4文字以上20文字以内">
                    </div>

                    <div class="edit_submit_area">
                        <button type="submit" class="edit_submit" id="edit_submit">変更</button>
                        <a href="${pageContext.request.contextPath}/Mypage" class="edit_back">戻る</a>
                    </div>
            </div>
        </form>
    </main>
    <div class="logout"></div>
   <input type="hidden" id="edit_success_flag" value="${editSuccess == true ? 'true' : 'false'}">
    
    <script src="${pageContext.request.contextPath}/js/header.js"></script>
    <script src="${pageContext.request.contextPath}/js/mypage_logout.js"></script>
</body>

</html>