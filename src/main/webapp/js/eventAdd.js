
const addEventImage = document.getElementById("add_event_image");
const ep = document.getElementById("event_preview");
const addEventFileName = document.getElementById("ea_file_name");
const eventCancelBtn = document.getElementById("event_cancel_image_btn");
const eNoImg = contextPath + "/img/no_img.png";

function closeEventAddPanel() {
	if (!currentType) return;

	const panel = document.getElementById(currentType + "-addPanel");
	if (panel) {
		// ===== 入力リセット =====
		//スポット名
		const addEventName = document.getElementById("add_event_name");
		if (addEventName) addEventName.value = "";

		//開始日
		const addEventStart = document.getElementById("add_event_start");
		if (addEventStart) addEventStart.value = "";
		//終了日
		const addEventEnd = document.getElementById("add_event_end");
		if (addEventEnd) addEventEnd.value = "";
		//住所
		const addEventAddress = document.getElementById("add_event_address");
		if (addEventAddress) addEventAddress.value = "";

		//URL
		const addEventUrl = document.getElementById("add_event_url");
		if (addEventUrl) addEventUrl.value = "";

		// 紹介文
		panel.querySelectorAll("input[type='text'], textarea").forEach(el => {
			el.value = "";
		});

		// checkbox
		panel.querySelectorAll("input[type='checkbox']").forEach(el => {
			el.checked = false;
		});

		// file
		if (addEventImage) addEventImage.value = "";

		if (addEventFileName) addEventFileName.textContent = "選択されていません";

		// 画像プレビュー

		if (ep) ep.src = contextPath + "/img/no_img.png";

		// 取消ボタン
		if (eventCancelBtn) eventCancelBtn.style.display = "none";

		// ===== パネル閉じる =====
		panel.classList.remove("open");
	}

	document.getElementById("selectPanel").classList.add("open");
}

// 初期状態（念のため）
ep.src = eNoImg;
eventCancelBtn.style.display = "none";

// 追加画像プレビュー
addEventImage.addEventListener("change", () => {
	const f = addEventImage.files[0];
	if (!f) return;
	addEventFileName.textContent = f.name;
	eventCancelBtn.style.display = "inline-block";

	const r = new FileReader();
	r.onload = e => {
		ep.src = e.target.result;
	};
	r.readAsDataURL(f);
});

// キャンセル
eventCancelBtn.addEventListener("click", () => {
	addEventImage.value = "";
	addEventFileName.textContent = "選択されていません";
	ep.src = eNoImg;
	eventCancelBtn.style.display = "none";
});

async function submitEvent() {

	//ログインチェック
	if (!loginUserId) {
		location.href = "LoginServlet";
		return;
	}

	const spanel = document.getElementById(currentType + "-addPanel");

	const address = spanel.querySelector("#add_event_address").value;

	if (!address) {
		alert("住所を入力してください");
		return;
	}

	// 国土地理院APIでジオコーディング
	const url = "https://msearch.gsi.go.jp/address-search/AddressSearch"
		+ "?q=" + encodeURIComponent(address);
	console.log("url:", url);
	const res = await fetch(url);
	const data = await res.json();

	console.log("GSI結果:", data);


	if (!data || data.length === 0) {
		alert("住所から位置を取得できませんでした");
		return;
	}

	// GSIは GeoJSON形式（coordinates: [lon, lat]）
	const lon = data[0].geometry.coordinates[0];
	const lat = data[0].geometry.coordinates[1];

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

	// サーブレットへ送信
	const formData = new FormData();

	formData.append("eventName", spanel.querySelector("#add_event_name").value);
	formData.append("eventAddress", address);
	formData.append("eventInfo", spanel.querySelector("#add_event_info").value);
	formData.append("eventUrl", spanel.querySelector("#add_event_url").value);

	formData.append("eventLat", String(lat));
	formData.append("eventLon", String(lon));
	formData.append("eventStart", spanel.querySelector("#add_event_start").value);
	formData.append("eventEnd", spanel.querySelector("#add_event_end").value);


	const eventAddFile = document.getElementById("add_event_image").files[0];
	if (eventAddFile) {
		formData.append("event_pict", eventAddFile);
	}

	const response = await fetch("EventAdd", {
		method: "POST",
		body: formData
	});

	const result = await response.json();

	if (result.success) {
		alert("登録成功！");
		location.reload();
	} else {
		alert("登録失敗：" + result.message);
	}
}