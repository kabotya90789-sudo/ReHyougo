package model;

import java.util.List;

import dao.Pins_FavoriteDAO;

public class PinBookmarkLogic {
	public boolean swicthBookmark(String userId, int pinId) {
		Pins_FavoriteDAO pinFavDao = new Pins_FavoriteDAO();
		boolean isSwicthFav = pinFavDao.updatePinsFavorite(userId, pinId);
		return isSwicthFav;
	}
	
	public List<Pin> searchYourBookmark(String userId) {
		Pins_FavoriteDAO pinFavDao = new Pins_FavoriteDAO();
		List<Pin> yourFavPins = pinFavDao.dispYourFavoritePins(userId);
		return yourFavPins;
	}
}
