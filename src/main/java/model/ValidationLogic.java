package model;

public class ValidationLogic {
	//ログインやユーザー登録時のバリデーションチェック
	public boolean userVali(User user) {
		if (user.getUserId().matches("[A-Za-z0-9]{4,20}") && user.getPassword().matches("[A-Za-z0-9]{4,20}")) {
			return true;
		}
		return false;
	}

	//スポット追加・編集時のバリデーションチェック
	public boolean pinVali(Pin pin) {
		String url = pin.getUrl();
		url = (url == null) ? null : url.trim();
		if (pin.getName().matches(".{1,50}") && (pin.getAddress().matches(".{1,50}"))
				&& (url == null || url.isEmpty() || (url.length() <= 400 && url.matches("https?://.+")))
				&& (pin.getImageUrl() == null || (pin.getImageUrl().matches(".{0,400}")
						&& pin.getImageUrl().matches(".*\\.(jpg|jpeg|png|gif)$")))
				&& pin.getInfo().matches(".{1,250}")) {
			return true;
		}
		return false;
	}

	//	//スポット追加・編集時のバリデーションチェック
	//	public boolean pinVali(Pin pin) {
	//		if (pin.getName().matches(".{1,50}") && (pin.getAddress().contains("兵庫県") && pin.getAddress().matches(".{1,50}")) && (pin.getUrl() == null || pin.getUrl().matches(".{0,400}")) && (pin.getImageUrl() == null || pin.getImageUrl().matches(".{0,400}")) && pin.getInfo().matches(".{1,250}")) {
	//			return true;
	//		}
	//		return false;
	//	}
	//	//イベント追加・編集時のバリデーションチェック
	//	public boolean eventVali(Event event) {
	//		if (event.getName().matches(".{1,50}") && (event.getAddress().contains("兵庫県") && event.getAddress().matches(".{1,50}")) && (event.getUrl() == null || event.getUrl().matches(".{0,400}")) && (event.getImageUrl() == null || event.getImageUrl().matches(".{0,400}")) && event.getInfo().matches(".{1,250}")) {
	//			return true;
	//		}
	//		return false;
	//	}
	//イベント追加・編集時のバリデーションチェック
	public boolean eventVali(Event event) {
		String url = event.getUrl();
		url = (url == null) ? null : url.trim();
		if (event.getName().matches(".{1,50}") && (event.getAddress().matches(".{1,50}"))
				&& (url == null || url.isEmpty() || (url.length() <= 400 && url.matches("https?://.+")))
				&& (event.getImageUrl() == null || (event.getImageUrl().matches(".{0,400}")
						&& event.getImageUrl().matches(".*\\.(jpg|jpeg|png|gif)$")))
				&& event.getInfo().matches(".{1,250}")) {
			return true;
		}
		return false;
	}

	//レビュー追加・編集時のバリデーションチェック
	public boolean reviewVali(Review review) {
		if (review.getTitle().matches(".{1,20}") && review.getContent().matches(".{1,250}")) {
			return true;
		}
		return false;
	}
}

//何でfalseになったかわかるようにするべきかy