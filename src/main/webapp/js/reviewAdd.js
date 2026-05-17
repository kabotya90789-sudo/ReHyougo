

function loadReviewPinDetail() {
	fetch("PinServlet?id=" + currentPinId)
		.then(response => {
			if (!response.ok) {
				throw new Error("通信エラー");
			}
			return response.json();
		})
		.then(data => {
			showReviewPinDetail();
		})
		.catch(err => {
			console.error(err);
		});
}


// =============================
// レビュー用 スポット詳細表示
// =============================
function showReviewPinDetail() {
	//ログインチェック
	if (!loginUserId) {
		location.href = "LoginServlet";
		return;
	}

	// スポット画像
	document.getElementById("spotImage").src = contextPath + currentPinData.imageUrl;

	// スポット名
	document.getElementById("reviewName").textContent = currentPinData.name;

	//	// hidden input
	//	document.getElementById("reviewPinId").value = reviewPinId;
	//		console.log(reviewPinId);

	// レビューパネル開く
	document.getElementById("add_reviewPanel")
		.classList.add("open");
}


//function openAddreviewPanel() {
//	const panel = document.getElementById("add_reviewPanel");
//	panel.classList.add("open");
//}

function closeAddreviewPanel() {
	document.getElementById("add_reviewPanel").classList.remove("open");
	// ===== 入力リセット =====
	//タイトル
	const addReviewTitle = document.getElementById("review_title");
	if (addReviewTitle) addReviewTitle.value = "";

	//レビュー文
	const addReviewContext = document.getElementById("review_Content");
	if (addReviewContext) addReviewContext.value = "";

	loadPinDetail(currentPinData.id);
}

async function addReview() {
	const panel = document.getElementById("add_reviewPanel");
	const formData = new FormData();
	console.log("currentPinData =", currentPinData);
	console.log("currentPinData.id =", currentPinData.id);
	formData.append("pinId", currentPinData.id);
	formData.append("reviewTitle", panel.querySelector("#review_title").value);
	formData.append("reviewContent", panel.querySelector("#review_Content").value);
	const response = await fetch("ReviewAdd", {
		method: "POST",
		body: formData
	});

	const result = await response.json();

	if (result.success) {
		alert("登録成功！");
		closeAddreviewPanel()
	} else {
		alert("登録失敗：" + result.message);
	}
}

//// =============================
//// 詳細取得
//// =============================
//function loadPinDetail(pinId) {
//fetch("PinServlet?id=" + pinId)
//.then(response => {
//  if (!response.ok) {
//    throw new Error("通信エラー");
//  }
//  return response.json();
//})
//.then(data => {
//  showDetail(data);
//})
//.catch(err => {
//  console.error(err);
//});
//}
//// =============================
//// 詳細表示
//// =============================
//
//function showDetail(data) {
//	document.getElementById("name").textContent = data.name;
//	document.getElementById("address").textContent = "住所：" + data.address;
//	document.getElementById("info").textContent = "紹介：" + data.info;
//
//	const date = new Date(data.createDate);
//
//	const hasTime =
//		date.getHours() !== 0 ||
//		date.getMinutes() !== 0 ||
//		date.getSeconds() !== 0;
//
//	document.getElementById("createDate").textContent =
//		hasTime
//			? "作成日：" + date.toLocaleString("ja-JP")
//			: "作成日：" + date.toLocaleDateString("ja-JP");
//
//	document.getElementById("image").src =
//		data.imageUrl || "<%=request.getContextPath()%>/img/no_img.png";
//
//	document.getElementById("url").href =
//		data.url || "#";
//
//	// ↓レビュー用追加
//	document.getElementById("reviewName").textContent = data.name;
//
//	document.getElementById("reviewImage").src =
//		data.imageUrl || "<%=request.getContextPath()%>/img/no_img.png";
//
//	document.getElementById("detailPanel").classList.add("open");
//}

