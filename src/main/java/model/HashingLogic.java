package model;

import org.apache.commons.codec.digest.DigestUtils;

public class HashingLogic {
	public User hashing(User user) {
		String password = user.getPassword();
		for (int i = 0; i < 50; i++) {
            password = DigestUtils.sha512Hex(password);
        }
		String salt = user.getUserId();
		String saltedPass = DigestUtils.sha512Hex(password + salt);
		for (int i = 0; i < 49; i++) {
            saltedPass = DigestUtils.sha512Hex(saltedPass);
        }
		user.setPassword(saltedPass);
		return user;
	}
}
