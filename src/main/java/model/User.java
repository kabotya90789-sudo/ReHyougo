package model;

import java.io.Serializable;

public class User implements Serializable {
	//member data
	private String userId; //ユーザーID
	private String password;// パスワード
	private int userStatus;// 登録状況

	//constructor
	public User() {
	}

	public User(String userId, String password) {
		this.userId = userId;
		this.password = password;
	}
	
	public User(String userId, String password, int userStatus) {
		this.userId = userId;
		this.password = password;
		this.userStatus = userStatus;
	}

	//getter,setter
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}

}
