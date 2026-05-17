package model;

import java.util.List;

import dao.Events_FavoriteDAO;

public class EventFavoriteLogic {
	  // お気に入りの ON/OFF 切り替え
    public boolean swicthLike(String userId, int eventId) {
        Events_FavoriteDAO favDao = new Events_FavoriteDAO();
        boolean isSwitch = favDao.updateEventsFavorite(userId, eventId);
        return isSwitch;
    }

    // 自分のお気に入りイベント一覧取得
    public List<Event> searchYourLike(String userId) {
        Events_FavoriteDAO favDao = new Events_FavoriteDAO();
        List<Event> yourFavEvents = favDao.dispYourFavoriteEvents(userId);
        return yourFavEvents;
    }
}
