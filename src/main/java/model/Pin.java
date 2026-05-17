package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Pin implements Serializable {

	private int id;					// ID
	private String userId;			// ユーザーID
	private String name;			// スポット名
	private String address;			// 住所
	private String url;				// URL
	private String lat;				// 緯度
	private String lon;				// 経度
	private String imageUrl;		// 画像の保存先
	private String info;			// 紹介文
	private List<String> tags;		// タグ
	private Date createdDate;		// 作成日
	private int pinGrow;			// 成長度
	private boolean fav;			// お気に入りしているか
	
	// --- コンストラクタ ---

	// 引数なし
	public Pin() {
	}

	// 引数あり　（id, lat, lon, pinGrow） → ピン表示用
	public Pin(int id, String lat, String lon, int pinGrow) {
		this.id = id;
		this.lat = lat;
		this.lon = lon;
		this.pinGrow = pinGrow;

	}
	
	// 引数あり（userId, name, address, url, lat, lon, imageUrl, info） → ピン追加用
	public Pin(String userId, String name, String address, String url,
			String lat, String lon, String imageUrl, String info) {
		this.userId = userId;
		this.name = name;
		this.address = address;
		this.url = url;
		this.lat = lat;
		this.lon = lon;
		this.imageUrl = imageUrl;
		this.info = info;
	}
	
	//仮
	// 引数あり（userId, name, address, url, lat, lon, imageUrl, info, tags） → ピン追加用
	public Pin(String userId, String name, String address, String url,
			 String imageUrl, String info, List<String> tags) {
		this.userId = userId;
		this.name = name;
		this.address = address;
		this.url = url;
		this.imageUrl = imageUrl;
		this.info = info;
		this.tags = tags;
	}
	// 引数あり（userId, name, address, url, lat, lon, imageUrl, info, tags） → ピン追加用
	public Pin(String userId, String name, String address, String url,
			String lat, String lon, String imageUrl, String info, List<String> tags) {
		this.userId = userId;
		this.name = name;
		this.address = address;
		this.url = url;
		this.lat = lat;
		this.lon = lon;
		this.imageUrl = imageUrl;
		this.info = info;
		this.tags = tags;
	}

	// 引数あり（id, userId, name, address, url, lat, lon, imageUrl, info, createdDate, fav）
	// → ピン詳細取得・編集用
	public Pin(int id, String userId, String name, String address, String url,
			String lat, String lon, String imageUrl, String info,
			Date createdDate, boolean fav) {
		this.id = id;
		this.userId = userId;
		this.name = name;
		this.address = address;
		this.url = url;
		this.lat = lat;
		this.lon = lon;
		this.imageUrl = imageUrl;
		this.info = info;
		this.createdDate = createdDate;
		this.fav = fav;
	}
	
	// 引数あり（id, userId, name, address, url, lat, lon, imageUrl, info, tags）
	// → ピン編集用
	public Pin(int id, String userId, String name, String address, String url,
			String lat, String lon, String imageUrl, String info,
			List<String> tags) {
		this.id = id;
		this.userId = userId;
		this.name = name;
		this.address = address;
		this.url = url;
		this.lat = lat;
		this.lon = lon;
		this.imageUrl = imageUrl;
		this.info = info;
		this.tags = tags;
	}
	// 引数あり（id, userId, name, address, url, lat, lon, imageUrl, info, tags, createdDate）
	// → ピン詳細取得・編集用
	public Pin(int id, String userId, String name, String address, String url,
			String lat, String lon, String imageUrl, String info,
			List<String> tags, Date createdDate) {
		this.id = id;
		this.userId = userId;
		this.name = name;
		this.address = address;
		this.url = url;
		this.lat = lat;
		this.lon = lon;
		this.imageUrl = imageUrl;
		this.info = info;
		this.tags = tags;
		this.createdDate = createdDate;
	}
	// 引数あり（id, userId, name, address, url, lat, lon, imageUrl, info, tags, createdDate, fav）
	// → ピン詳細取得・編集用
	public Pin(int id, String userId, String name, String address, String url,
			String lat, String lon, String imageUrl, String info,
			List<String> tags, Date createdDate, boolean fav) {
		this.id = id;
		this.userId = userId;
		this.name = name;
		this.address = address;
		this.url = url;
		this.lat = lat;
		this.lon = lon;
		this.imageUrl = imageUrl;
		this.info = info;
		this.tags = tags;
		this.createdDate = createdDate;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public int getPinGrow() {
		return pinGrow;
	}

	public void setPinGrow(int pinGrow) {
		this.pinGrow = pinGrow;
	}

	public boolean isFav() {
		return fav;
	}

	public void setFav(boolean fav) {
		this.fav = fav;
	}

}
