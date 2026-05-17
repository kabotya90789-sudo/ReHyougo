function toggleGood(reviewId, button) {
	//ログインチェック
	if (!loginUserId) {
		location.href = "LoginServlet";
		return;
	}
	const icon = button.querySelector('.good_icon');

	// 現在状態
	let isLiked = icon.dataset.liked === "true";

	// 画像切り替え
	if (isLiked) {

		// ON → OFF
		icon.src = `${contextPath}/img/add_good.svg`;
		icon.dataset.liked = "false";

	} else {

		// OFF → ON
		icon.src = `${contextPath}/img/add_good-on.svg`;
		icon.dataset.liked = "true";
	}
	console.log("reviewId" , reviewId);
	// サーバー送信
	fetch("ReviewFavorite", {
		method: 'POST',
		headers: {
			'Content-Type': 'application/x-www-form-urlencoded'
		},
		body: `reviewId=${reviewId}`
	})
		.then(response => response.json())
		.then(json => {

			if (!json.success) {

				// 失敗したら戻す
				if (isLiked) {
					icon.src = `${contextPath}/img/add_good-on.svg`;
					icon.dataset.liked = "true";
				} else {
					icon.src = `${contextPath}/img/add_good.svg`;
					icon.dataset.liked = "false";
				}

				alert(json.message);
				return;
			}
			// 成功時メッセージ
			if (isLiked) {
			    alert("いいねを外しました。");
			} else {
			    alert("いいねしました。");
			}
			loadPinDetail(currentPinId);
		})
		.catch(error => {

			// 通信失敗時も戻す
			if (isLiked) {
				icon.src = `${contextPath}/img/add_good-on.svg`;
				icon.dataset.liked = "true";
			} else {
				icon.src = `${contextPath}/img/add_good.svg`;
				icon.dataset.liked = "false";
			}

			console.error(error);
			alert("通信エラー");
		});
}