<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="model.User"%>
    <%
	User loginUser = (User) session.getAttribute("loginUser");
	%>
<!DOCTYPE html>
<html lang="ja">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reひょうご</title>
    <link rel="stylesheet" href="css/help.css">
</head>

<body>
	<header class="header"></header>
	<% if (loginUser != null) { %>
    <input type="hidden" id="loginUserId" value="<%= loginUser.getUserId() %>">
	<% } else { %>
	    <input type="hidden" id="loginUserId" value="">
	<% } %>

		<!-- ★ 共通ヘッダー -->
<!--    <jsp:include page="/WEB-INF/jsp/header.jsp" />-->
    <main>
        <section class="help_sec">
            <div class="help_sec_ttl">
                <h2>ヘルプ</h2>
                <hr>
            </div>
                <div class="welcom_box">
                    <ul class="help_list">
                        <li class="help_list_item">
                            <h3 class="help_ttl">みんごへようこそ！</h3>
                            <p class="help_text">
                                みんごは、みんなで兵庫の町の魅力を再発見するアプリです。
                                まだ知らない場所を見つけたり、花や生き物を育成したり・・・。
                                みんなで楽しく兵庫の町を彩りましょう！
                            </p>

                        </li>
                    </ul>
                </div>

                <div class="image_box">
                    <ul class="help_list">
                        <li class="help_list_item">
                            <h3 class="help_ttl">画面イメージ</h3>
                            <div class="help_content_flex">
                                <div class="help_img_box">
                                    <h4 class="help_detail_ttl">トップ画面(ログイン中)
                                    </h4>
                                    <img src="img/login_top.svg" alt="">
                                </div>
                                <ul class="img_list">
                                    <li class="img_list_item">
                                        ①検索欄です。ピンの検索ができます。
                                    </li>
                                    <li class="img_list_item">
                                        ②スポットピンです。施設やお店を示します。
                                    </li>
                                    <li class="img_list_item">
                                        ③イベントピンです。その場所で開催されているイベントを示します。
                                    </li>
                                    <li class="img_list_item">
                                        ④ここからピンの作成ができます
                                    </li>
                                    <li class="img_list_item">
                                        ⑤メインメニューが開きます。
                                    </li>
                                </ul>
                            </div>
                            <div class="help_content_flex">
                                <div class="help_img_box">
                                    <h4 class="help_detail_ttl">メインメニュー
                                    </h4>
                                    <img src="img/login_top.svg" alt="">
                                </div>
                                <ul class="img_list">
                                    <li class="img_list_item">
                                        ①検索欄です。ピンの検索ができます。
                                    </li>
                                    <li class="img_list_item">
                                        ②スポットピンです。施設やお店を示します。
                                    </li>
                                    <li class="img_list_item">
                                        ③イベントピンです。その場所で開催されているイベントを示します。
                                    </li>
                                    <li class="img_list_item">
                                        ④ここからピンの作成ができます
                                    </li>
                                    <li class="img_list_item">
                                        ⑤メインメニューが開きます。
                                    </li>
                                </ul>
                            </div>
                            <div class="help_content_flex">
                                <div class="help_img_box">
                                    <h4 class="help_detail_ttl">
                                        メインメニュー
                                    </h4>
                                    <img src="img/mypage.svg" alt="">
                                </div>
                                <ul class="img_list">
                                    <li class="img_list_item">
                                        ①ユーザー情報です。パスワードのみ変更可能です。退会したい場合はここから退会ボタンをクリックします。
                                    </li>
                                    <li class="img_list_item">
                                        ②ご自身が追加したピンや投稿したレビュー、ブックマークしたピンの一覧をここから見ることができます。
                                    </li>
                                </ul>
                            </div>
                        </li>
                    </ul>
                </div>


                <div class="pin_box">
                    <ul class="help_list">
                        <li class="help_list_item">
                            <h3 class="help_ttl">ピン</h3>
                            <h4 class="help_detail_ttl">ピンの打ち方</h4>
                            <ol>
                                <li class="img_list_item">ログイン中のトップ画面の右下のボタンをクリック。</li>
                                <li class="img_list_item">イベントピンを作るかスポットピンを作るかどちらかを選択。</li>
                                <li class="img_list_item">ピン作成画面が表示されるので、項目をすべて入力。</li>
                                <li class="img_list_item">入力し終えたら作成ボタンをクリック。</li>
                                <li class="img_list_item">確認画面で内容を確認し「はい」を押して作成完了。地図上にピンが表示されます。</li>
                            </ol>
                            <h4 class="help_detail_ttl">ピンの編集</h4>
                            <ol>
                                <li class="img_list_item">編集したいピンをクリックしピンの詳細を表示。</li>
                                <li class="img_list_item">ピン詳細の編集ボタンをクリック。</li>
                                <li class="img_list_item">編集画面で内容を入力し完了をクリック。</li>
                                <li class="img_list_item">確認画面で編集内容を確認し「はい」を押して編集完了。</li>
                            </ol>
                            <h4 class="help_detail_ttl">ピンの削除</h4>
                            <ol>
                                <li class="img_list_item">ピン編集画面で「ピン削除」をクリック。</li>
                                <li class="img_list_item">確認画面で「はい」を押して削除完了。ピンが地図上から消えます。</li>
                            </ol>
                            <h4 class="help_detail_ttl">スポットピンの成長</h4>
                            <ul>
                                <li class="img_list_item">ピンには成長要素があり、<strong>芽→つぼみ→花</strong>で成長します。</li>
                                <li class="img_list_item">
                                    成長条件は、
                                    <strong>ピンのレビュー数</strong>と
                                    <strong>レビューについたいいね数</strong>、
                                    <strong>スポットのブックマークされた数</strong>の合計です。
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>

                <div class="review_box">
                    <ul class="help_list">
                        <li class="help_list_item">
                            <h3 class="help_ttl">レビュー</h3>
                            <h4 class="help_detail_ttl">レビュー投稿</h4>
                            <ol>
                                <li class="img_list_item">レビュー投稿したいピンをクリックしてピン詳細を開く。</li>
                                <li class="img_list_item">ピン詳細の「レビュー追加」ボタンをクリック</li>
                                <li class="img_list_item">レビュー入力画面が開き、内容を入力し終えたら「＋」を押す。</li>
                                <li class="img_list_item">確認画面で内容を確認し「はい」を押してレビュー投稿完了。</li>
                            </ol>
                            <h4 class="help_detail_ttl">レビュー編集</h4>
                            <ol>
                                <li class="img_list_item">ピン詳細から投稿したレビューの「編集」をクリック。</li>
                                <li class="img_list_item">編集内容を入力し、「＋」をクリック。</li>
                                <li class="img_list_item">確認画面で内容を確認し「はい」を押してレビュー編集完了。</li>
                            </ol>
                            <h4 class="help_detail_ttl">レビュー削除</h4>
                            <ol>
                                <li class="img_list_item">レビュー編集画面で「レビュー削除」をクリック。</li>
                                <li class="img_list_item">確認画面で「はい」を押して削除完了。</li>
                            </ol>
                        </li>
                    </ul>
                </div>

                <div class="help_search_box">
                    <ul class="help_list">
                        <li class="help_list_item">
                            <h3 class="help_ttl">検索</h3>
                            <h4 class="help_detail_ttl">検索方法</h4>
                            <ol>
                                <li class="img_list_item">ページ上部にある、検索欄をクリック。</li>
                                <li class="img_list_item">自分で直接入力するか、タグを使って検索。併用もできます。</li>
                                <li class="img_list_item">検索欄に入力し、検索をクリックすると条件に合ったピンが一覧で表示されます。</li>
                                <li class="img_list_item">一覧の中から気になるピンをクリックすると、そのままピンの詳細を確認することができます。</li>
                            </ol>
                            <h4 class="help_detail_ttl">検索欄のイベントタグについて</h4>
                            <p class="help_text">
                                イベントタグは、イベントピンを絞り込む専用の検索タグになります。そのため、スポットピンの検索はできません。
                            </p>
                        </li>
                    </ul>
                </div>

                <div class="help_mypage_box">
                    <ul class="help_list">
                        <li class="help_list_item">
                            <h3 class="help_ttl">マイページでできること</h3>
                            <h4 class="help_detail_ttl">ユーザー情報の編集</h4>
                                <p class="help_text">
                                    登録したパスワードの変更や、退会手続きができます。
                                </p>
                            <h4 class="help_detail_ttl">活動記録の確認</h4>
                                <ul>
                                    <li class="img_list_item">
                                        <strong>あなたが作成したスポット・イベントピン：</strong>これまでにあなたが追加したピンの一覧を見ることができます
                                    </li>
                                    <li class="img_list_item">
                                       <strong>あなたが書いたレビュー：</strong>これまでにあなたが追加投稿したレビューの一覧を見ることができます
                                    </li>
                                    <li class="img_list_item">
                                        <strong>ブックマークしたイベント・スポット：</strong>これまでにあなたが保存したブックマークの一覧を見ることができます
                                    </li>
                                </ul>
                        </li>
                    </ul>

                    <div class="help_notes_area">
                        <p class="help_text">
                            ※ユーザーIDは変更することができません。
                        </p>
                        <p class="help_text">
                            ※退会はマイページからのみ行えます。
                        </p>
                    </div>

                </div>

            
        </section>
    </main>
    <script src="js/header.js"></script>
</body>

</html>