// =============================
// ■編集→削除
// =============================
function deletePin() {

	//ログインチェック
	if (!loginUserId) {
		location.href = "LoginServlet";
		return;
	}
	//権限チェック
	if (loginUserId != currentPinData.userId && loginUserId !== "ADMIN") {
		return;
	}
	
	if (!confirm("削除しますか？")) return;

	fetch(`PinDelete?id=${currentPinId}&userId=${currentPinData.userId}`, {
		method: "POST"
	})
		.then(res => res.json())
		.then(data => {
			if (data.success) {
				alert("削除完了");
				location.reload();
			} else {
				alert("削除失敗：" + data.message);
			}
		})
		.catch(err => {
			console.error(err);
			alert("通信エラー");
		});
}