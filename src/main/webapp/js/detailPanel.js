"use strict";

let currentPinId = null;
let currentPinData = [];
let editReview2 = null;




function loadPinDetail(pinId) {
	const pinFetch = fetch("PinServlet?id=" + pinId)
		.then(res => res.json())
		.then(json => {
			if (!json.success) {
				alert(json.message || "Pin取得失敗");
			}
			return json;
		});

	const reviewFetch = fetch("ReviewServlet?id=" + pinId)
		.then(res => res.json())
		.then(json => {
			if (!json.success) {
				alert(json.message || "Review取得失敗");
			}
			return json;
		});

	Promise.all([pinFetch, reviewFetch])
		.then(([pinData, reviewData]) => {
			showPinDetail(pinData, reviewData);
		})
		.catch(err => {
			console.error(err);
		});
}


// =============================
// イベント詳細取得
// =============================
function loadEventDetail(eventId) {
	fetch("EventServlet?id=" + eventId)
		.then(response => {
			if (!response.ok) {
				throw new Error("通信エラー");
			}
			return response.json();
		})
		.then(edata => {
			showEventDetail(edata);
		})
		.catch(err => {
			console.error(err);
		});
}


// =============================
// スポット詳細表示
// =============================
let currentReviewsData = [];
let reviewDetailData = null;

function showPinDetail(pinData, reviewData) {

	const panel = document.getElementById("pinDetailPanel");

	if (!panel) {
		console.warn("pinDetailPanel が存在しません");
		return;
	}

	currentPinId = pinData.id;
	currentPinData = pinData;
	
	const pinBookmarkBtn =
		document.getElementById("pinBookmarkBtn");

	if (pinData.fav) {
		pinBookmarkBtn.classList.add("is-active");
	} else {
		pinBookmarkBtn.classList.remove("is-active");
	}
	
	currentReviewsData = reviewData.data || [];

	controlEditButton(currentPinData);

	document.getElementById("name").textContent = pinData.name;
	document.getElementById("address").textContent = "住所：" + pinData.address;
	document.getElementById("info").textContent = "紹介：" + pinData.info;

	const date = new Date(pinData.createDate);

	document.getElementById("createDate").textContent =
		"作成日：" + date.toLocaleDateString("ja-JP");

	const base = contextPath;

	document.getElementById("image").src =
		pinData.imageUrl
			? base + pinData.imageUrl
			: base + "/img/no_img.png";

	const urlEl = document.getElementById("url");

	if (pinData.url) {
		urlEl.href = pinData.url;
		urlEl.textContent = "詳細";
	} else {
		urlEl.removeAttribute("href");
		urlEl.textContent = "なし";
	}

	const tagArea = document.getElementById("tags");
	tagArea.innerHTML = "";

	if (!pinData.tags || pinData.tags.length === 0) {
		const div = document.createElement("div");
		div.className = "panel_tag";
		div.textContent = "なし";
		tagArea.appendChild(div);
	}

	pinData.tags.forEach(tag => {
		const div = document.createElement("div");
		div.className = "panel_tag";
		div.textContent = tag;
		tagArea.appendChild(div);
	});


	const container = document.getElementById("reviewList");
	container.innerHTML = "";
	if (!currentReviewsData || currentReviewsData.length === 0) {
		container.innerHTML = "<p>なし</p>";
	} else {

		currentReviewsData.forEach(review => {
			//			controlReviewButton(review.userId);
			const isOwner = loginUserId && (loginUserId == review.userId || loginUserId === "ADMIN");
			const goodIcon = review.fav
				? `${contextPath}/img/add_good-on.svg`
				: `${contextPath}/img/add_good.svg`;
			const created = new Date(review.createdDate);
			const html = `
		<div class="panel_section">
	       <div class="review_auther_wrapper">
	         <p>${review.userId}</p>
	          <p>作成日 ${created.toLocaleDateString("ja-JP")}</p>
			  <button class="review_edit_btn"
			                  style="display:${isOwner ? 'inline-block' : 'none'};"
			                  onclick="showEditReviewPinDetail(${review.id}, '${review.userId}')">
			                  編集/削除
			  </button>
	       </div>
		   <p class="review_title">${review.title}</p>
	       <p>${review.content}</p>
	       <div class="review_good_wrapper">
		   <button class="add_good"
		           onclick="toggleGood(${review.id}, this)">
				   <img src="${goodIcon}"
				        class="good_icon"
				        data-liked="${review.fav}">
		   </button>

	         <p class="good_count">${review.likeCounts}</p>
	       </div>
	     </div>
	   `;

			container.insertAdjacentHTML("beforeend", html);
		});
	}
	document.getElementById("pinDetailPanel").classList.add("open");
}

function closePanel() {
	document.getElementById("pinDetailPanel").classList.remove("open");
}

//追加者のみ編集ボタン表示(ピン詳細)
function controlEditButton(currentPinData) {
	const btn = document.getElementById("editBtn");

	if (loginUserId && (loginUserId == currentPinData.userId || loginUserId === "ADMIN")) {
		btn.style.display = "inline-block";
	} else {
		btn.style.display = "none";
	}
}
////追加者のみ編集ボタン表示(レビュー)
//function controlReviewButton(userId) {
//	const btn = document.getElementById("reviewEditBtn");
//
//	if (loginUserId && (loginUserId == userId || loginUserId === "ADMIN")) {
//		btn.style.display = "inline-block";
//	} else {
//		btn.style.display = "none";
//	}
//}




// =============================
// イベント詳細表示
// =============================

let currentEventId = null;
let currentEventData = null;

function showEventDetail(edata) {

	const panel = document.getElementById("eventDetailPanel");

	if (!panel) {
		console.warn("eventDetailPanel が存在しません");
		return;
	}
	console.log(edata.fav);
	const eventBookmarkBtn =
		document.getElementById("eventBookmarkBtn");
	
	if (edata.fav) {
		eventBookmarkBtn.classList.add("is-active");
	} else {
		eventBookmarkBtn.classList.remove("is-active");
	}

	currentEventId = edata.id;
	currentEventData = edata;

	controlEventEditButton(currentEventData);

	document.getElementById("event_name").textContent = edata.name;
	document.getElementById("event_address").textContent = "住所：" + edata.address;
	document.getElementById("event_info").textContent = "紹介：" + edata.info;

	const date = new Date(edata.createDate);

	document.getElementById("event_createDate").textContent =
		"作成日：" + date.toLocaleDateString("ja-JP");

	const base = contextPath;

	document.getElementById("event_image").src =
		edata.imageUrl
			? base + edata.imageUrl
			: base + "/img/no_img.png";

	const urlEl = document.getElementById("event_url");

	if (edata.url) {
		urlEl.href = edata.url;
		urlEl.textContent = "詳細";
	} else {
		urlEl.removeAttribute("href");
		urlEl.textContent = "なし";
	}

	document.getElementById("start").textContent =
		"開始日：" + new Date(edata.start).toLocaleDateString("ja-JP");

	document.getElementById("end").textContent =
		"終了日：" + new Date(edata.end).toLocaleDateString("ja-JP");

	panel.classList.add("open");
}

function closePanel() {
	document.getElementById("eventDetailPanel").classList.remove("open");
}

//追加者のみ編集ボタン表示(イベント詳細)
function controlEventEditButton(currentEventData) {
	const btn = document.getElementById("eventEditBtn");
	if (loginUserId && (loginUserId == currentEventData.userId || loginUserId == "ADMIN")) {
		btn.style.display = "inline-block";
	} else {
		btn.style.display = "none";
	}
}

// =============================
// DOM読み込み後処理
// =============================
document.addEventListener("DOMContentLoaded", function() {
	// ===== 詳細パネル外クリックで閉じる =====
	const detailPanel = document.getElementById("pinDetailPanel");

	document.addEventListener("click", function(e) {
		// パネル開いてないなら何もしない
		if (!detailPanel.classList.contains("open")) {
			return;
		}

		// パネル内クリックなら閉じない
		if (detailPanel.contains(e.target)) {
			return;
		}

		// マーカークリック直後は showDetail が走るので、
		// 外側クリックだけで閉じる
		detailPanel.classList.remove("open");
	});
});
// =============================
// DOM読み込み後処理
// =============================
document.addEventListener("DOMContentLoaded", function() {
	// ===== 詳細パネル外クリックで閉じる =====
	const detailPanel = document.getElementById("eventDetailPanel");

	document.addEventListener("click", function(e) {
		// パネル開いてないなら何もしない
		if (!detailPanel.classList.contains("open")) {
			return;
		}

		// パネル内クリックなら閉じない
		if (detailPanel.contains(e.target)) {
			return;
		}

		// マーカークリック直後は showDetail が走るので、
		// 外側クリックだけで閉じる
		detailPanel.classList.remove("open");
	});
});

// =============================
// DOM読み込み後処理
// =============================
document.addEventListener("DOMContentLoaded", function() {
	// ===== 外クリックで閉じる =====
	const selectPanel = document.getElementById("selectPanel");
	const openBtn = document.querySelector(".create_pin_btn");

	document.addEventListener("click", function(e) {
		// 開いてなければ何もしない
		if (!selectPanel.classList.contains("open")) {
			return;
		}

		// パネル内クリックなら閉じない
		if (selectPanel.contains(e.target)) {
			return;
		}

		// 開くボタン押した時も閉じない
		if (openBtn && openBtn.contains(e.target)) {
			return;
		}

		// それ以外は閉じる
		selectPanel.classList.remove("open");
	});
});


//===============================================================
//     ブックマーク（スポット）
//============================================================== 

const pinBookmarkBtn =
	document.getElementById("pinBookmarkBtn");

if (pinBookmarkBtn) {

	pinBookmarkBtn.addEventListener("click", function() {
		//ログインチェック
		if (!loginUserId) {
			location.href = "LoginServlet";
			return;
		}

		fetch("PinBookmark", {

			method: "POST",

			headers: {
				"Content-Type":
					"application/x-www-form-urlencoded"
			},

			body: `pinId=${currentPinId}`
		})
			.then(response => response.json())
			.then(json => {

				if (!json.success) {
					alert(json.message);
					return;
				}
				this.classList.toggle('is-active');

				if (this.classList.contains('is-active')) {
					alert("お気に入り登録しました");
					return;
				} else {
					alert("お気に入り解除しました");
					return;
				}
			})
			.catch(err => {
				console.error(err);
			});

	});
}
//===============================================================
//     ブックマーク（イベント）
//============================================================== 

const eventBookmarkBtn =
	document.getElementById("eventBookmarkBtn");

if (eventBookmarkBtn) {

	eventBookmarkBtn.addEventListener("click", function() {
		//ログインチェック
		if (!loginUserId) {
			location.href = "LoginServlet";
			return;
		}

		fetch("EventBookmark", {

			method: "POST",

			headers: {
				"Content-Type":
					"application/x-www-form-urlencoded"
			},

			body: `eventId=${currentEventId}`
		})
			.then(response => response.json())
			.then(json => {

				if (!json.success) {
					alert(json.message);
					return;
				}
				this.classList.toggle('is-active');

				if (this.classList.contains('is-active')) {
					alert("お気に入り登録しました");
				} else {
					alert("お気に入り解除しました");
				}
			})
			.catch(err => {
				console.error(err);
			});

	});
}
//document.querySelectorAll('.search_bookmark').forEach(btn => {
//	btn.addEventListener('click', function() {
//
//		fetch('PinBookmark', {
//			method: 'POST',
//			headers: {
//				'Content-Type': 'application/x-www-form-urlencoded'
//			},
//			body: `pinId=${currentPinId}`
//		})
//			.then(response => response.json())
//			.then(json => {
//
//				if (json.success) {
//
//					// 成功時だけ切り替え
//					this.classList.toggle('is-active');
//
//					if (this.classList.contains('is-active')) {
//						alert("登録しました");
//					} else {
//						alert("解除しました");
//					}
//
//				} else {
//					alert(json.message);
//				}
//			})
//			.catch(error => {
//				console.error(error);
//				alert("通信エラー");
//			});
//	});
//});
//===============================================================
//     ■いいね
//=============================================================== 
document.querySelectorAll('.add_good').forEach(btn => {
	btn.addEventListener('click', function() {
		this.classList.toggle('is-active');
	});
});


