package model;

import java.io.Serializable;
import java.util.Date;

public class Review implements Serializable {

	private int id; //ID
	private String userId; //ユーザーID
	private int pinId; //スポットID
	private String title; //タイトル
	private String content; //レビュー内容
	private Date createdDate; //作成日
	private int likeCounts; //いいね数
	private boolean fav; //いいねをしているか

	// --- コンストラクタ ---

	// 引数なし
	public Review() {
	}

	// 引数あり（userId, pinId, title, content） → レビュー追加用
	public Review(String userId, int pinId, String title, String content) {
		this.userId = userId;
		this.pinId = pinId;
		this.title = title;
		this.content = content;
	}

	// 引数あり（id, userId, pinId, title, content, createdDate, likeCounts） → レビュー取得・編集用
	public Review(int id, String userId, int pinId, String title, String content
			) {
		this.id = id;
		this.userId = userId;
		this.pinId = pinId;
		this.title = title;
		this.content = content;
	}
	// 引数あり（id, userId, pinId, title, content, createdDate, likeCounts） → レビュー取得・編集用
	public Review(int id, String userId, int pinId, String title, String content,
			Date createdDate, int likeCounts) {
		this.id = id;
		this.userId = userId;
		this.pinId = pinId;
		this.title = title;
		this.content = content;
		this.createdDate = createdDate;
		this.likeCounts = likeCounts;
	}

	// 引数あり（id, userId, pinId, title, content, createdDate, likeCounts, fav） → レビュー取得・編集用
	public Review(int id, String userId, int pinId, String title, String content,
			Date createdDate, int likeCounts, boolean fav) {
		this.id = id;
		this.userId = userId;
		this.pinId = pinId;
		this.title = title;
		this.content = content;
		this.createdDate = createdDate;
		this.likeCounts = likeCounts;
		this.fav = fav;
	}

	// --- getter / setter ---

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getPinId() {
		return pinId;
	}

	public void setPinId(int pinId) {
		this.pinId = pinId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public int getLikeCounts() {
		return likeCounts;
	}

	public void setLikeCounts(int likeCounts) {
		this.likeCounts = likeCounts;
	}

	public boolean isFav() {
		return fav;
	}

	public void setFav(boolean fav) {
		this.fav = fav;
	}

}
