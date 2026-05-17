<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reひょうご</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

</head>

<body class="login_page">
<header class="header"></header>

<!--   ★ 共通ヘッダー -->
<!--    <jsp:include page="/WEB-INF/jsp/header.jsp" />-->

    <main>
        <form action="${pageContext.request.contextPath}/LoginServlet" id="login_form" method="post">
            <div class="login_form">
                <h2 class="login_ttl">ログイン</h2>
                <hr>

                <div class="field">
                    <label for="login_user_id" class="username_box">ユーザーID</label>
                    <input type="text" id="login_user_id" name="userId">
                </div>

                <div class="field">
                    <label for="login_pass" class="password_box">パスワード</label>
                    <input type="password" id="login_pass" name="password">
                </div>

                <div class="login_register_btn_area">
                    <a href="UserRegister" class="login_register_btn">新規ユーザー登録へ</a>
                </div>

                <div class="login_submit_area">
                    <button type="submit" class="login_submit">ログイン</button>
                    <a href="${pageContext.request.contextPath}/Main" class="login_back">戻る</a>
                </div>
            </div>
        </form>
    </main>

    <script src="${pageContext.request.contextPath}/js/header.js"></script>
</body>

</html>