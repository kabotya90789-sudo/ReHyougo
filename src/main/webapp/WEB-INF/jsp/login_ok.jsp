<!-- registerDone.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ユーザー登録完了</title>
</head>
<body>
    <h1>登録完了</h1>
    <p>ユーザー登録が完了しました！</p>

    <!-- 【修正箇所】リンク先を Login に変更します -->
    <a href="${pageContext.request.contextPath}/Login">ログイン画面へ進む</a>

</body>
</html>
