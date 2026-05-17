package model;

import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable {

	private int id;				//ID
	private String userId;		//ユーザーID
	private String name;		//イベント名
	private String address;		//住所
	private String url;			//URL
	private String lat;			//緯度
	private String lon;			//経度	
	private String imageUrl;    //画像の保存先
	private String info;		//紹介文
	private Date start;			//イベント開始日
	private Date end;			//イベント終了日
	private Date createdDate;	//作成日
	private boolean fav;		//お気に入りしているか

	// --- コンストラクタ ---

	// 引数なし
	public Event() {
	}

	// 引数あり（id, lat, lon, imageUrl） → イベント表示用
	public Event(int id, String lat, String lon, String imageUrl) {
		this.id = id;
		this.lat = lat;
		this.lon = lon;
		this.imageUrl = imageUrl;
	}

	// 引数あり（userId, name, address, url, lat, lon, imageUrl, info, start, end） → イベント追加用
	public Event(String userId, String name, String address, String url,
			String lat, String lon, String imageUrl, String info,
			Date start, Date end) {
		this.userId = userId;
		this.name = name;
		this.address = address;
		this.url = url;
		this.lat = lat;
		this.lon = lon;
		this.imageUrl = imageUrl;
		this.info = info;
		this.start = start;
		this.end = end;
	}

	// 引数あり（id, userId, name, address, url, lat, lon, imageUrl, info, start, end, createdDate）
	// → イベント編集用
	public Event(int id, String userId, String name, String address, String url,
			String lat, String lon, String imageUrl, String info,
			Date start, Date end) {
		this.id = id;
		this.userId = userId;
		this.name = name;
		this.address = address;
		this.url = url;
		this.lat = lat;
		this.lon = lon;
		this.imageUrl = imageUrl;
		this.info = info;
		this.start = start;
		this.end = end;
	}
		// 引数あり（id, userId, name, address, url, lat, lon, imageUrl, info, start, end, createdDate）
		// → イベント編集用
		public Event(int id, String userId, String name, String address, String url,
				String lat, String lon, String imageUrl, String info,
				Date start, Date end, Date createdDate) {
			this.id = id;
			this.userId = userId;
			this.name = name;
			this.address = address;
			this.url = url;
			this.lat = lat;
			this.lon = lon;
			this.imageUrl = imageUrl;
			this.info = info;
			this.start = start;
			this.end = end;
			this.createdDate = createdDate;
	}
	// 引数あり（id, userId, name, address, url, lat, lon, imageUrl, info, start, end, createdDate, fav）
	// → イベント詳細取得・編集用
	public Event(int id, String userId, String name, String address, String url,
			String lat, String lon, String imageUrl, String info,
			Date start, Date end, Date createdDate, boolean fav) {
		this.id = id;
		this.userId = userId;
		this.name = name;
		this.address = address;
		this.url = url;
		this.lat = lat;
		this.lon = lon;
		this.imageUrl = imageUrl;
		this.info = info;
		this.start = start;
		this.end = end;
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

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public boolean isFav() {
		return fav;
	}

	public void setFav(boolean fav) {
		this.fav = fav;
	}

}
