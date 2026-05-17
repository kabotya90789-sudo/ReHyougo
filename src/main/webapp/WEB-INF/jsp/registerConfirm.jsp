<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.User" %>

<%
User registerUser = (User) session.getAttribute("registerUser");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ユーザー登録確認</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/user_register.css">

</head>
<body>
    <main>
<header class="header"></header>

<div class="container">
    <h2>登録内容の確認</h2>
    <p style="font-size: 13px;">以下の内容で登録します。よろしいですか？</p>

    <div class="confirm-box">
        <div class="label">ユーザーID</div>
        <div class="value"><%= registerUser.getUserId() %></div>

        <div class="label">パスワード</div>
        <div class="value">●●●●●●（非表示）</div>

        <div class="label">データの取り扱い</div>
        <div class="value" style="color:#d9534f;">退会後のデータ保持に同意済み</div>
    </div>

    <!-- ★ POST で action=done を送る（最重要） -->
    <form action="<%= request.getContextPath() %>/UserRegister?action=done" method="post">
        <button type="submit" class="btn-submit">この内容で登録</button>
    </form>

    <!-- 戻る -->
    <a href="<%= request.getContextPath() %>/UserRegister" class="back-link">修正する（戻る）</a>
</div>
    </main>
    <script src="${pageContext.request.contextPath}/js/header.js"></script>
</body>
</html>
