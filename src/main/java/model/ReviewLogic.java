package model;

import java.util.List;

import dao.ReviewsDAO;

public class ReviewLogic {
	//スポットに対するすべてのレビュー取得
	public List<Review> view(int pinId) {
		ReviewsDAO dao = new ReviewsDAO();
		List<Review> reviewsList = dao.getReviews(pinId);
		return reviewsList;
	}
	//レビュー追加
	public boolean add(Review review) {
		ValidationLogic vl = new ValidationLogic();
		boolean isReviewVali = vl.reviewVali(review);
		if (isReviewVali) {
			ReviewsDAO dao = new ReviewsDAO();
			boolean isReviewAdd = dao.addReview(review);
			return isReviewAdd;
		}
		return isReviewVali;
	}
	//レビュー編集
	public boolean edit(Review review) {
		ValidationLogic vl = new ValidationLogic();
		boolean isReviewVali = vl.reviewVali(review);
		if (isReviewVali) {
			ReviewsDAO dao = new ReviewsDAO();
			boolean isReviewEdit = dao.editReview(review);
			return isReviewEdit;
		}
		return isReviewVali;
	}
	//レビュー削除
	public boolean delete(int reviewId) {
		ReviewsDAO dao = new ReviewsDAO();
		boolean isRevieDelete = dao.deleteReview(reviewId);
		return isRevieDelete;
	}
}
