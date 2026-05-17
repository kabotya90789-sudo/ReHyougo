
const addPinImage = document.getElementById("add_image");
const p = document.getElementById("preview");
const addFileName = document.getElementById("pa_file_name");
const cancelBtn = document.getElementById("cancel_image_btn");
const noImg = contextPath + "/img/no_img.png";

// 初期状態（念のため）
p.src = noImg;
cancelBtn.style.display = "none";

// 追加画像プレビュー
addPinImage.addEventListener("change", () => {
	const f = addPinImage.files[0];
	if (!f) return;
	addFileName.textContent = f.name;
	cancelBtn.style.display = "inline-block";

	const r = new FileReader();
	r.onload = e => {
		p.src = e.target.result;
	};
	r.readAsDataURL(f);
});

// キャンセル
cancelBtn.addEventListener("click", () => {
	addPinImage.value = "";
	p.src = noImg;
	addFileName.textContent = "選択されていません";
	cancelBtn.style.display = "none";
});

// ===== ピンボタン =====
const pinBtn = document.querySelector('.create_pin_btn');
if (pinBtn) {
	
	pinBtn.addEventListener('click', function() {
		document.getElementById("selectPanel").classList.add("open");
		console.log("clicked")
		console.log("pinBtn:", pinBtn);

	});
}



function openAddPanel(type) {
	//ログインチェック
	if (!loginUserId) {
		location.href = "LoginServlet";
		return;
	}
	currentType = type;

	document.getElementById("selectPanel").classList.remove("open");

	// 全パネル閉じる
	document.querySelectorAll(".add-panel").forEach(panel => {
		panel.classList.remove("open");
	});

	// 対象パネル開く
	const panel = document.getElementById(type + "-addPanel");
	if (!panel) {
		console.error("パネルが見つからない:", type);
		return;
	}

	panel.classList.add("open");

	console.log("選択されたタイプ:", type);
}

function closePinAddPanel() {
	if (!currentType) return;

	const panel = document.getElementById(currentType + "-addPanel");
	if (panel) {
		// ===== 入力リセット =====
		//スポット名
		const addPinName = document.getElementById("add_name");
		if (addPinName) addPinName.value = "";

		//住所
		const addPinAddress = document.getElementById("add_address");
		if (addPinAddress) addPinAddress.value = "";

		//URL
		const addPinUrl = document.getElementById("add_url");
		if (addPinUrl) addPinUrl.value = "";

		// 紹介文
		panel.querySelectorAll("input[type='text'], textarea").forEach(el => {
			el.value = "";
		});

		// checkbox
		panel.querySelectorAll("input[type='checkbox']").forEach(el => {
			el.checked = false;
		});

		// file
		if (addPinImage) addPinImage.value = "";

		if (addFileName) addFileName.textContent = "選択されていません";

		// 画像プレビュー

		if (p) p.src = contextPath + "/img/no_img.png";

		// 取消ボタン
		if (cancelBtn) cancelBtn.style.display = "none";

		// ===== パネル閉じる =====
		panel.classList.remove("open");
	}

	document.getElementById("selectPanel").classList.add("open");
}
//
//ピン追加
//===================
async function submitPin() {
	const panel = document.getElementById(currentType + "-addPanel");

	const address = panel.querySelector("#add_address").value;

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

	formData.append("pinName", panel.querySelector("#add_name").value);
	formData.append("pinAddress", address);
	formData.append("pinInfo", panel.querySelector("#add_info").value);
	formData.append("pinUrl", panel.querySelector("#add_url").value);

	formData.append("pinLat", String(lat));
	formData.append("pinLon", String(lon));

	const tags = panel.querySelectorAll('input[name="tags"]:checked');
	tags.forEach(tag => formData.append("tags", tag.value));

	const file = document.getElementById("add_image").files[0];
	if (file) {
		formData.append("pict", file);
	}
	console.log("PIN lat:", lat, "lon:", lon);
	const response = await fetch("PinAdd", {
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


//async function submitPin() {
//	const panel = document.getElementById(currentType + "-addPanel");
//
//	const address = panel.querySelector("#add_address").value;
//
//	if (!address) {
//		alert("住所を入力してください");
//		return;
//	}
//
//	// 国土地理院APIでジオコーディング
//	const url = "https://msearch.gsi.go.jp/address-search/AddressSearch"
//		+ "?q=" + encodeURIComponent(address);
//	console.log("url:", url);
//	const res = await fetch(url);
//	const data = await res.json();
//
//	console.log("GSI結果:", data);
//
//
//	if (!data || data.length === 0) {
//		alert("住所から位置を取得できませんでした");
//		return;
//	}
//
//	// GSIは GeoJSON形式（coordinates: [lon, lat]）
//	const lon = data[0].geometry.coordinates[0];
//	const lat = data[0].geometry.coordinates[1];
//
//	console.log("lat:", lat);
//	console.log("lon:", lon);
//	// サーブレットへ送信
//	const formData = new FormData();
//
//	formData.append("pinName", panel.querySelector("#add_name").value);
//	formData.append("pinAddress", address);
//	formData.append("pinInfo", panel.querySelector("#add_info").value);
//	formData.append("pinUrl", panel.querySelector("#add_url").value);
//
//	formData.append("pinLat", String(lat));
//	formData.append("pinLon", String(lon));
//
//	const tags = panel.querySelectorAll('input[name="tags"]:checked');
//	tags.forEach(tag => formData.append("tags", tag.value));
//
//	const file = document.getElementById("add_image").files[0];
//	if (file) {
//		formData.append("pict", file);
//	}
//
//	const response = await fetch("PinAdd", {
//		method: "POST",
//		body: formData
//	});
//
//	const result = await response.json();
//
//	if (result.success) {
//		alert("登録成功！");
//		location.reload();
//	} else {
//		alert("登録失敗：" + result.message);
//	}
//}
