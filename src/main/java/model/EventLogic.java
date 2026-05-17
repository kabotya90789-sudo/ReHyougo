package model;

import java.util.List;

import dao.EventsDAO;

public class EventLogic {

	// --- イベント一覧表示 ---
	public List<Event> disp() {
		EventsDAO dao = new EventsDAO();
		return dao.getEventsList();
	}

	// --- イベント詳細表示 ---
	public Event view(String userId, int eventId) {
		EventsDAO dao = new EventsDAO();
		return dao.getEventDetail(userId, eventId);
	}

	// --- イベント追加 ---
	public boolean add(Event event) {

		// バリデーション
		ValidationLogic vali = new ValidationLogic();
		if (!vali.eventVali(event)) {
			return false;
		}
		// DAO 実行
		EventsDAO dao = new EventsDAO();

		// 名前重複チェック
		boolean isDupli = dao.duplicationCheckEvent(event);
		if (isDupli) {
			// 追加実行
			return dao.createEvent(event);
		}
		return isDupli;
	}

	// --- イベント編集 ---
	public boolean edit(Event bfEvent, Event afEvent) {

		// バリデーション
		ValidationLogic vali = new ValidationLogic();
		if (!vali.eventVali(afEvent)) {
			return false;
		}

		EventsDAO dao = new EventsDAO();

//		// 作成者本人かチェック
//		Event currentEvent = dao.getEventDetail(event.getId());
//		if (currentEvent == null || !currentEvent.getUserId().equals(loginUserId)) {
//			return false; // 本人でない、またはデータがない場合は拒否
//		}

		// 自分以外のデータで、その名前が使われていないかチェック
		boolean isDupli = true;
		if (!bfEvent.getName().equals(afEvent.getName())) {
			isDupli = dao.duplicationCheckEvent(afEvent);
		}
		if (isDupli) {
			// 編集実行
			return dao.editEvent(afEvent);
		}
		return isDupli;
	}

	// --- イベント削除（ステータス変更） ---
	public boolean delete(int eventId) {
		EventsDAO dao = new EventsDAO();
		return dao.deleteEventStatus(eventId);
	}

	// --- イベント終了ステータス更新 ---
	public boolean editStatus() {
		EventsDAO dao = new EventsDAO();
		return dao.fixEventsEnd ();
	}

	// --- 自分のイベント一覧表示 ---
	public List<Event> myEventDisp(String userId) {
		EventsDAO dao = new EventsDAO();
		return dao.dispYourEvents(userId);
	}
}
