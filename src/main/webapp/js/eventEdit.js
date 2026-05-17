
// ■詳細→編集

function closeEventDetailEditPanel() {
	document.getElementById("event-editPanel").classList.remove("open");
	loadEventDetail(currentEventData.id);
}
let elat;
let elon;

document.addEventListener("DOMContentLoaded", function() {

	const epanel = document.getElementById("event-editPanel");
	const preview = document.getElementById("edit_event_preview");
	const input = document.getElementById("edit_event_image");
	const deleteCheck = document.getElementById("delete_event_image");
	const deleteWrap = document.getElementById("event_delete_wrap");
	const cancelBtn = document.getElementById("edit_cancel_event_image_btn");
	const fileName = document.getElementById("event_file_name");
	const editBtn = document.getElementById("eventEditBtn");

	// UI制御
	function updateUI() {

		const hasOriginalImage =
			currentEventData?.imageUrl &&
			!currentEventData.imageUrl.includes("no_img.png");

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
		editBtn.addEventListener("click", openEventDetailEditPanel);
	}

	// =========================
	// 画像選択時
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
		if (currentEventData?.imageUrl && currentEventData.imageUrl !== "/img/no_img.png") {
			preview.src = contextPath + currentEventData.imageUrl;
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
			preview.src = contextPath + currentEventData.imageUrl;
			fileName.textContent = "変更されていません";
		}
	});

	// パネル開く
	function openEventDetailEditPanel() {

		if (!loginUserId) {
			location.href = "LoginServlet";
			return;
		}

		if (loginUserId != currentEventData.userId && loginUserId !== "ADMIN") {
			return;
		}

		// フォーム反映
		document.getElementById("edit_event_name").value = currentEventData.name;
		document.getElementById("edit_event_address").value = currentEventData.address;
		document.getElementById("edit_event_info").value = currentEventData.info;
		document.getElementById("edit_event_url").value = currentEventData.url;
		document.getElementById("edit_event_start").value = currentEventData.start;
		document.getElementById("edit_event_end").value = currentEventData.end;

		elat = currentEventData.lat;
		elon = currentEventData.lon;

		// 画像表示
		input.value = "";

		deleteCheck.checked = false;
		fileName.textContent = "変更されていません";

		if (currentEventData.imageUrl && !currentEventData.imageUrl.includes("no_img.png")) {
			preview.src = contextPath + currentEventData.imageUrl;
		} else {
			preview.src = noImg;
		}

		updateUI();

		epanel.classList.add("open");
	}

});

//function openEventDetailEditPanel() {
//	
//	if (!loginUserId) {
//			location.href = "LoginServlet";
//			return;
//		}
//		if (loginUserId != currentEventData.userId && loginUserId !== "ADMIN") {
//			return;
//		}
//	
//	const epanel = document.getElementById("event-editPanel");
//
//	document.getElementById("event_edit_name").value = currentEventData.name;
//	document.getElementById("event_edit_address").value = currentEventData.address;
//	document.getElementById("event_edit_info").value = currentEventData.info;
//	document.getElementById("event_edit_url").value = currentEventData.url;
//	document.getElementById("event_edit_start").value = currentEventData.start;
//	document.getElementById("event_edit_end").value = currentEventData.end;
//
//	//	currentEventId = currentEventData.id;
//	elat = currentEventData.lat;
//	elon = currentEventData.lon;
//
//
//	//既存画像の削除
//	document.getElementById("delete_event_image").addEventListener("change", function() {
//		const eventPreview = document.getElementById("edit_event_preview");
//
//		if (this.checked) {
//			eventPreview.src = "<%=request.getContextPath()%>/img/no_img.png";
//			document.getElementById("edit_event_image").value = "";
//		} else {
//			eventPreview.src = "<%=request.getContextPath()%>" + currentEventData.imageUrl;
//		}
//	});
//
//	// 既存画像を表示
//	document.getElementById("edit_event_preview").src =
//		//		currentEventData.imageUrl || "<%=request.getContextPath()%>/img/no_img.png";
//		currentEventData.imageUrl;
//	// 新たな画像を表示
//	document.getElementById("event_edit_image").addEventListener("change", function(e) {
//		const eefile = e.target.files[0];
//		if (eefile) {
//			const eereader = new FileReader();
//			eereader.onload = function(ev) {
//				document.getElementById("edit_event_preview").src = ev.target.result;
//			};
//			eereader.readAsDataURL(eefile);
//		}
//	});
//
//	epanel.classList.add("open");
//}

async function updateEvent() {
	const eformData = new FormData();
	const enewAddress = document.getElementById("edit_event_address").value;



	// ★住所が変わった場合だけジオコーディング
	if (enewAddress !== currentEventData.address) {

		if (!enewAddress) {
			alert("住所を入力してください");
			return;
		}

		const eurl = "https://msearch.gsi.go.jp/address-search/AddressSearch"
			+ "?q=" + encodeURIComponent(enewAddress);

		const eres = await fetch(eurl);
		const edata = await eres.json();

		if (!edata || edata.length === 0) {
			alert("住所から位置を取得できませんでした");
			return;
		}

		elon = edata[0].geometry.coordinates[0];
		elat = edata[0].geometry.coordinates[1];

		//緯度経度から兵庫県か判定
		const revUrl =
			"https://mreversegeocoder.gsi.go.jp/reverse-geocoder/LonLatToAddress"
			+ "?lat=" + elat + "&lon=" + elon;

		const revRes = await fetch(revUrl);
		const revData = await revRes.json();
		console.log(revData);

		const muniCd = revData.results.muniCd;

		// 兵庫県は 28 から始まる
		if (!muniCd || !muniCd.startsWith("28")) {
			alert("兵庫県内のみ登録できます");
			return;
		}
	}

	eformData.append("id", currentEventId);
	eformData.append("userId", currentEventData.userId);
	eformData.append("name", document.getElementById("edit_event_name").value);
	eformData.append("address", enewAddress);
	eformData.append("info", document.getElementById("edit_event_info").value);
	eformData.append("url", document.getElementById("edit_event_url").value);

	eformData.append("lat", elat);
	eformData.append("lon", elon);

	const eFile = document.getElementById("edit_event_image").files[0];
	if (eFile) {
		eformData.append("event_edit_pict", eFile);
	}

	eformData.append("deleteImage", document.getElementById("delete_event_image").checked);

	eformData.append("start", document.getElementById("edit_event_start").value);
	eformData.append("end", document.getElementById("edit_event_end").value);


	fetch("EventEdit", {
		method: "POST",
		body: eformData
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
			closeEventDetailEditPanel();
		updateEventMarkerPosition(elat, elon, json.imageUrl + "?t=" + Date.now()); //マーカー位置変更

		})
}

function updateEventMarkerPosition(lat, lon, imageUrl) {
	const marker = markerMap[currentEventId];

	if (!marker) return;

	marker.setLatLng([lat, lon]);

	// ★完全リセット方式
	const icon = createEventIcon("");
	marker.setIcon(icon);

	// 少し遅延して画像差し替え
	setTimeout(() => {
		const newIcon = createEventIcon(contextPath + imageUrl + "?t=" + Date.now());
		marker.setIcon(newIcon);
	}, 50);
}