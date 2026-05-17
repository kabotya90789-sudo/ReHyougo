//レビュー編集時前データ自動入力

let editReviewId;
let editReviewUserId;

function showEditReviewPinDetail(reviewId, userId) {
	//ログインチェック
	if (!loginUserId) {
		location.href = "LoginServlet";
		return;
	}
	//権限チェック
	if (loginUserId != userId && loginUserId !== "ADMIN") {
		return;
	}
	editReviewId = reviewId;
	editReviewUserId = userId;

	const review = currentReviewsData.find(r => r.id === Number(reviewId));
	// スポット画像
	document.getElementById("editReviewSpotImage").src = contextPath + currentPinData.imageUrl;

	// スポット名
	document.getElementById("editReviewSpotName").value = currentPinData.name;
	//元のレビュータイトル
	document.getElementById("edit_review_title").value = review.title;
	//元のレビュー文
	document.getElementById("edit_review_content").value = review.content;

	//	// hidden input
	//	document.getElementById("reviewPinId").value = reviewPinId;
	//		console.log(reviewPinId);

	// レビューパネル開く
	document.getElementById("edit_reviewPanel")
		.classList.add("open");
}

function closeEditReviewPanel() {
	document.getElementById("edit_reviewPanel").classList.remove("open");
	loadPinDetail(currentPinData.id);
}
async function editReview() {
	const panel = document.getElementById("edit_reviewPanel");
	const formData = new FormData();
	formData.append("reviewId", editReviewId);
	formData.append("userId", editReviewUserId);
	formData.append("pinId", currentPinData.id);
	formData.append("reviewTitle", panel.querySelector("#edit_review_title").value);
	formData.append("reviewContent", panel.querySelector("#edit_review_content").value);
	const response = await fetch("ReviewEdit", {
		method: "POST",
		body: formData
	});

	const result = await response.json();

	if (result.success) {
		alert("登録成功！");
		closeEditReviewPanel()
	} else {
		alert("登録失敗：" + result.message);
	}
}