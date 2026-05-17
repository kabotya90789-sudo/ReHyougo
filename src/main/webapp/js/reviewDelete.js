function deleteReview() {
	//ログインチェック
	if (!loginUserId) {
		location.href = "LoginServlet";
		return;
	}
	//権限チェック
	if (loginUserId != editReviewUserId && loginUserId !== "ADMIN") {
		return;
	}
	if (!confirm("削除しますか？")) return;
	console.log(reviewDetailData);
	fetch(`ReviewDelete?id=${editReviewId}&userId=${editReviewUserId}`, {
		method: "POST"
	})
		.then(res => res.json())
		.then(data => {
			if (data.success) {
				alert("削除完了");
				closeEditReviewPanel()
			} else {
				alert("削除失敗：" + data.message);
			}
		})
		.catch(err => {
			console.error(err);
			alert("通信エラー");
		});
}