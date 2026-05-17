<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>登録完了</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/user_register.css">
</head>
<body>
<main>
<header class="header"></header>

    <div class="modal_wrapper">
        <div class="modal">
            <div class="msg-main">ユーザー登録が完了しました</div>
            <div class="msg-sub">ぜひひょうごを楽しんでください。</div>
            
            <div class="btn-area">
                <a href="${pageContext.request.contextPath}/Main">トップへ</a>
                <a href="${pageContext.request.contextPath}/LoginServlet">ログイン</a>
            </div>
        </div>
    </div>
</main>
    <script src="${pageContext.request.contextPath}/js/header.js"></script>
</body>
</html>
