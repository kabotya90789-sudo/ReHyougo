package model;

import java.util.ArrayList;
import java.util.List;

import dao.Pin_TagsDAO;
import dao.PinsDAO;

public class PinLogic {
	//マップ表示時にピン情報取得
	public List<Pin> disp() {
		PinsDAO dao = new PinsDAO();
		List<Pin> pinsList = dao.getPinsList();
		return pinsList;
	}
	//スポットの詳細取得
	public Pin view(String userId, int pinId) {
		PinsDAO pinsDao = new PinsDAO();
		Pin p = pinsDao.getPinDetail(pinId, userId);
		Pin_TagsDAO pinTagsDao = new Pin_TagsDAO();
		List<String> tags = pinTagsDao.getPinTags(pinId);
		Pin pinDetail = new Pin(p.getId(), p.getUserId(), p.getName(), p.getAddress(), p.getUrl(), p.getLat(), p.getLon(), p.getImageUrl(), p.getInfo(), tags, p.getCreatedDate(), p.isFav());
		return pinDetail;
	}
	
//	//スポット追加
	public boolean add(Pin pin) {
		ValidationLogic vl = new ValidationLogic();
		boolean isPinVali = vl.pinVali(pin);
		if (isPinVali) {
			PinsDAO pinsDao = new PinsDAO();
			boolean isDupli = pinsDao.duplicationCheckPin(pin);
			if (isDupli) {
				int num = pinsDao.createPin(pin);
				if (num == -1) {
					return false;
				}
				List<Integer> pinTagsList = new ArrayList<>();
				for (String tag : pin.getTags()) {
					switch (tag) {
						case "食べる":
							pinTagsList.add(1);
							break;
						case "遊ぶ":
							pinTagsList.add(2);
							break;
						case "観る":
							pinTagsList.add(3);
							break;
						case "買う":
							pinTagsList.add(4);
							break;
						case "休む":
							pinTagsList.add(5);
							break;
					}
				}
				Pin_TagsDAO pinTagsDao = new Pin_TagsDAO();
				boolean isPinTagsAdd = pinTagsDao.createPinTags(num, pinTagsList);
				return isPinTagsAdd;
			}
			return isDupli;
		}
		return isPinVali;
	}
//	//スポット情報編集
	public boolean edit(Pin bfPin, Pin afPin) {
		ValidationLogic vl = new ValidationLogic();
		boolean isPinVali = vl.pinVali(afPin);
		boolean isDupli = true;
		PinsDAO pinsDao = new PinsDAO();
		if (isPinVali) {
			if (! bfPin.getName().equals(afPin.getName())) {
				isDupli = pinsDao.duplicationCheckPin(afPin);
			}
			if (isDupli) {
				boolean isPinEdit = pinsDao.editPin(afPin);
				Pin_TagsDAO pinTagsDao = new Pin_TagsDAO();
				List<Integer> pinTagsList = new ArrayList<>();
				for (String tag : afPin.getTags()) {
					switch (tag) {
						case "食べる":
							pinTagsList.add(1);
							break;
						case "遊ぶ":
							pinTagsList.add(2);
							break;
						case "観る":
							pinTagsList.add(3);
							break;
						case "買う":
							pinTagsList.add(4);
							break;
						case "休む":
							pinTagsList.add(5);
							break;
					}
				}
				boolean isPinTagsEdit = pinTagsDao.editPinTags(afPin.getId(), pinTagsList);
				return isPinEdit && isPinTagsEdit;
			}
			return isDupli;
		}
		return isPinVali;
	}
	//スポット削除
	public boolean delete(int pinId) {
		PinsDAO dao = new PinsDAO();
		return dao.deletePin(pinId);
	}
	//自分が追加したスポット一覧取得
	public List<Pin> myPinDisp(String userId) {
		PinsDAO dao = new PinsDAO();
		List<Pin> myPin = dao.dispYourPins(userId);
		return myPin;
	}
}
