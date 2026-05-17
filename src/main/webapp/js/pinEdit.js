// =============================
// ■詳細→編集
// =============================


function openSpotPanel() {
	const panel = document.getElementById("spot-addPanel");
	panel.classList.add("open");
}

function closePinDetailEditPanel() {
	document.getElementById("spot-editPanel").classList.remove("open");
	loadPinDetail(currentPinData.id);
}
let lat;
let lon;

document.addEventListener("DOMContentLoaded", function() {

	const panel = document.getElementById("spot-editPanel");
	const preview = document.getElementById("edit_preview");
	const input = document.getElementById("edit_image");
	const deleteCheck = document.getElementById("delete_image");
	const deleteWrap = document.getElementById("delete_wrap");
	const cancelBtn = document.getElementById("edit_cancel_image_btn");
	const fileName = document.getElementById("file_name");
	const editBtn = document.getElementById("editBtn");
	const noImg = contextPath + "/img/no_img.png";

	// UI制御（最重要）
	function updateUI() {

		const hasOriginalImage =
			currentPinData?.imageUrl &&
			!currentPinData.imageUrl.includes("no_img.png");

		const hasNewFile = input.files && input.files.length > 0;

		// 全部非表示
		deleteWrap.style.display = "none";
		cancelBtn.style.display = "none";

		// ===== 分岐 =====
		if (hasNewFile) {
			// 新しい画像がある（最優先）
			cancelBtn.style.display = "inline-block";

		} else if (hasOriginalImage) {
			// 元画像だけある
			deleteWrap.style.display = "flex";
		}

	}

	// =========================
	// 編集ボタン
	// =========================
	if (editBtn) {
		editBtn.addEventListener("click", openPinDetailEditPanel);
	}

	// =========================
	// 画像選択
	// =========================
	input.addEventListener("change", function(e) {

		if (!this.files || this.files.length === 0) {
			return;
		}

		const file = this.files[0];

		fileName.textContent = file.name;

		const reader = new FileReader();
		reader.onload = function(ev) {
			preview.src = ev.target.result;
		};
		reader.readAsDataURL(file);

		deleteCheck.checked = false;

		updateUI();
	});

	// キャンセル（新規画像）
	cancelBtn.addEventListener("click", function() {

		input.value = "";
		fileName.textContent = "変更されていません";

		// 元画像に戻す
		if (currentPinData?.imageUrl && currentPinData.imageUrl !== "/img/no_img.png") {
			preview.src = contextPath + currentPinData.imageUrl;
		} else {
			preview.src = noImg;
		}

		updateUI();
	});

	// 削除チェック
	deleteCheck.addEventListener("change", function() {

		if (this.checked) {
			fileName.textContent = "削除予定";
		} else {
			preview.src = contextPath + currentPinData.imageUrl;
			fileName.textContent = "変更されていません";
		}
	});

	// パネル開く
	function openPinDetailEditPanel() {

		if (!loginUserId) {
			location.href = "LoginServlet";
			return;
		}

		if (loginUserId != currentPinData.userId && loginUserId !== "ADMIN") {
			return;
		}

		// フォーム
		document.getElementById("edit_name").value = currentPinData.name;
		document.getElementById("edit_address").value = currentPinData.address;
		document.getElementById("edit_info").value = currentPinData.info;
		document.getElementById("edit_url").value = currentPinData.url;

		currentPinId = currentPinData.id;
		lat = currentPinData.lat;
		lon = currentPinData.lon;

		// タグ
		const tags = currentPinData.tags || [];
		document.querySelectorAll(".tag").forEach(cb => {
			cb.checked = tags.includes(cb.value);
		});

		// 画像表示
		input.value = "";

		deleteCheck.checked = false;
		fileName.textContent = "変更されていません";

		if (currentPinData.imageUrl && !currentPinData.imageUrl.includes("no_img.png")) {
			preview.src = contextPath + currentPinData.imageUrl;
		} else {
			preview.src = noImg;
		}

		updateUI();

		panel.classList.add("open");
	}

});


//function openPinDetailEditPanel() {
//	
//	//ログインチェック
//	if (!loginUserId) {
//		location.href = "LoginServlet";
//		return;
//	}
//	//権限チェック
//	if (loginUserId != currentPinData.userId && loginUserId !== "ADMIN") {
//		return;
//	}
//	const panel = document.getElementById("spot-editPanel");
//
//	document.getElementById("edit_name").value = currentPinData.name;
//	document.getElementById("edit_address").value = currentPinData.address;
//	document.getElementById("edit_info").value = currentPinData.info;
//	document.getElementById("edit_url").value = currentPinData.url;
//	currentPinId = currentPinData.id;
//	lat = currentPinData.lat;
//	lon = currentPinData.lon;
//
//	const tags = currentPinData.tags;
//	document.querySelectorAll(".tag").forEach(cb => {
//		cb.checked = tags.includes(cb.value);
//	});
//
//	//既存画像の削除
//	document.getElementById("delete_image").addEventListener("change", function() {
//		const preview = document.getElementById("edit_preview");
//
//		if (this.checked) {
//			preview.src = "<%=request.getContextPath()%>/img/no_img.png";
//			document.getElementById("edit_image").value = "";
//		} else {
//			preview.src = "<%=request.getContextPath()%>" + currentPinData.imageUrl;
//		}
//	});
//
//	// 既存画像を表示
//	document.getElementById("edit_preview").src =
//		currentPinData.imageUrl || "<%=request.getContextPath()%>/img/no_img.png";
//	// 新たな画像を表示
//	document.getElementById("edit_image").addEventListener("change", function(e) {
//		const file = e.target.files[0];
//		if (file) {
//			const reader = new FileReader();
//			reader.onload = function(ev) {
//				document.getElementById("edit_preview").src = ev.target.result;
//			};
//			reader.readAsDataURL(file);
//		}
//	});
//
//	panel.classList.add("open");
//}

async function updatePin() {
	const formData = new FormData();
	const newAddress = document.getElementById("edit_address").value;



	// ★住所が変わった場合だけジオコーディング
	if (newAddress !== currentPinData.address) {

		if (!newAddress) {
			alert("住所を入力してください");
			return;
		}

		const url = "https://msearch.gsi.go.jp/address-search/AddressSearch"
			+ "?q=" + encodeURIComponent(newAddress);

		const res = await fetch(url);
		const data = await res.json();

		if (!data || data.length === 0) {
			alert("住所から位置を取得できませんでした");
			return;
		}

		lon = data[0].geometry.coordinates[0];
		lat = data[0].geometry.coordinates[1];

		//緯度経度から兵庫県か判定
		const revUrl =
			"https://mreversegeocoder.gsi.go.jp/reverse-geocoder/LonLatToAddress"
			+ "?lat=" + lat + "&lon=" + lon;

		const revRes = await fetch(revUrl);
		const revData = await revRes.json();

		const muniCd = revData.results.muniCd;

		// 兵庫県は 28 から始まる
		if (!muniCd || !muniCd.startsWith("28")) {
			alert("兵庫県内のみ登録できます");
			return;
		}
	}

	formData.append("id", currentPinId);
	formData.append("userId", currentPinData.userId);
	formData.append("name", document.getElementById("edit_name").value);
	formData.append("address", newAddress);
	formData.append("info", document.getElementById("edit_info").value);
	formData.append("url", document.getElementById("edit_url").value);

	formData.append("lat", lat);
	formData.append("lon", lon);

	const file = document.getElementById("edit_image").files[0];
	if (file) {
		formData.append("pict", file);
	}

	formData.append("deleteImage", document.getElementById("delete_image").checked);

	const tags = document.querySelectorAll('input[name="edit_tags"]:checked');
	tags.forEach(tag => formData.append("tags", tag.value));

	fetch("PinEdit", {
		method: "POST",
		body: formData
	})
		.then(res => res.json())
		.then(json => {

			if (!json.success) {
				alert(json.message);
				return;
			}

			// 成功したときだけ次へ
			alert("更新完了");
			// パネル切り替え
			closePinDetailEditPanel();
			updateMarkerPosition(lat, lon); //マーカー位置変更

			//			return fetch("PinServlet?id=" + currentPinData.id);

		})
	//		.then(res => res.json())
	//		.then(json => {
	//
	//			if (!json.success) {
	//				alert(json.message);
	//				return;
	//			}
	//
	//
	//
	//			// 詳細パネル更新
	//			document.getElementById("name").textContent = data.name;
	//			document.getElementById("address").textContent = "住所：" + data.address;
	//			document.getElementById("info").textContent = "紹介：" + data.info;
	//			const date = new Date(data.createDate);
	//
	//			document.getElementById("createDate").textContent =
	//				"作成日：" + date.toLocaleDateString("ja-JP");
	//			//	document.getElementById("image").src = pinData.imageUrl || "<%=request.getContextPath()%>/img/no_img.png";
	//			const base = contextPath;
	//			document.getElementById("image").src =
	//				data.imageUrl
	//					? base + data.imageUrl
	//					: base + "/img/no_img.png";
	//			document.getElementById("url").href = data.url || "#";
	//			const tagArea = document.getElementById("tags");
	//			tagArea.innerHTML = "";
	//
	//			if (!data.tags || data.tags.length === 0) {
	//				const div = document.createElement("div");
	//				div.className = "panel_tag";
	//				div.textContent = "タグなし";
	//				tagArea.appendChild(div);
	//			}
	//
	//			data.tags.forEach(tag => {
	//				const div = document.createElement("div");
	//				div.className = "panel_tag";
	//				div.textContent = tag;
	//				tagArea.appendChild(div);
	//			});
	//
	//
	//			const container = document.getElementById("reviewList");
	//			container.innerHTML = "";
	//			if (!currentReviewsData || currentReviewsData.length === 0) {
	//				container.innerHTML = "<p>なし</p>";
	//			} else {
	//
	//				currentReviewsData.forEach(review => {
	//					const created = new Date(review.createdDate);
	//					const html = `
	//				       <div class="review_auther_wrapper">
	//					   	<h4>${review.title}</h4>
	//				         <p>${review.userId}</p>
	//				          <p>作成日 ${created.toLocaleDateString("ja-JP")}</p>
	//				         <button class="review_edit_btn" onclick="showEditReviewPinDetail(${review.id})">編集/削除</button>
	//				       </div>
	//				       <p>${review.content}</p>
	//				       <div class="review_good_wrapper">
	//					   <button class="add_good" onclick="addGood(${review.id}, this)">
	//					       <img src="${contextPath}/img/add_good.svg"
	//					            class="good_icon off ${review.fav ? '' : 'active'}">
	//					       <img src="${contextPath}/img/add_good-on.svg"
	//					            class="good_icon on ${review.fav ? 'active' : ''}">
	//					     </button>
	//
	//				         <p class="good_count">${review.likeCounts}</p>
	//				       </div>
	//				     </div>
	//				   `;
	//
	//					container.insertAdjacentHTML("beforeend", html);
	//				});
	//			}
	//		})
	//		.catch(err => console.error(err));
}


function updateMarkerPosition(lat, lon) {
	const marker = markerMap[currentPinId];

	if (!marker) return;

	marker.setLatLng([lat, lon]);
}


const markerMap = {};

// マーカー作成時
function addMarker(pin) {
	const marker = L.marker([pin.lat, pin.lon]).addTo(map);
	markerMap[pin.id] = marker;
}


