package com.game.demo.message.request;

import com.game.socket.Message;
import com.game.socket.annotation.Protocol;

@Protocol(module = 1, cmd = 1000)
public class ReqLoginMessage extends Message {

	private String userName;
	private String password;

	public ReqLoginMessage() {
	}

	public ReqLoginMessage(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
