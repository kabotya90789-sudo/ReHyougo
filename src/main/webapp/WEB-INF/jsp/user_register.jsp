<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ja">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>新規ユーザー登録</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/user_register.css">

</head>
<body class="register_page">


<header class="header"></header>


    <main>
        <form action="${pageContext.request.contextPath}/UserRegister" id="register_form" method="post">
            <div class="user_register_form">

                <h2 class="register_ttl">新規ユーザー登録</h2>
                <hr>

                <% String msg = (String) request.getAttribute("errorMsg");
                   if (msg != null) { %>
                    <p style="color:red; font-size:14px; text-align:center;"><%= msg %></p>
                <% } %>

                <div class="field">
                    <label for="register_user_id" class="username_box">ユーザーID</label>
                    <input type="text" id="register_user_id" name="userId" placeholder="半角英数字4文字以上20文字以内">
                </div>

                <div class="field">
                    <label for="register_pass" class="password_box">パスワード</label>
                    <input type="password" id="register_pass" name="password" placeholder="半角英数字4文字以上20文字以内">
                </div>

                <div class="field">
                    <label for="register_pass_re" class="password_box">パスワード(確認)</label>
                    <input type="password" id="register_pass_re" name="passwordConfirm" placeholder="半角英数字4文字以上20文字以内">
                </div>

                <!-- 同意チェック -->
                <div class="agreement_area">
                    <p>※退会後も投稿されたデータは削除されず保持されます。</p>
                    <label>
                        <input type="checkbox" name="agree" id="agree_check">
                        内容を理解し、同意します
                    </label>
                </div>

                <div class="register_submit_area">
                    <button type="submit" class="register_submit" id="register_submit_btn" disabled>登録</button>
                    <a href="${pageContext.request.contextPath}/Main" class="register_back">戻る</a>
                </div>

            </div>
        </form>
    </main>

    <script src="${pageContext.request.contextPath}/js/header.js"></script>

    <script>
        const agreeCheck = document.getElementById('agree_check');
        const submitBtn = document.getElementById('register_submit_btn');

        agreeCheck.addEventListener('change', function() {
            submitBtn.disabled = !this.checked;
        });
    </script>

</body>
</html>
