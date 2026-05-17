package model;

import dao.Reviews_FavoriteDAO;

public class ReviewLikeLogic {
	public boolean switchLike(String userId, int reviewId) {
		Reviews_FavoriteDAO rfDao = new Reviews_FavoriteDAO();
		boolean isSwicthFav = rfDao.updateReviewsFavorite(userId, reviewId);
		return isSwicthFav;
	}
}
