package com.yychat.model;

import java.io.Serializable;

public class User implements Serializable{
	private String userName;//��Ա����
	private String passWord;
	private String userMessageType;
	public String getUserMessageType() {
		return userMessageType;
	}
	public void setUserMessageType(String userMessageType) {
		this.userMessageType = userMessageType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;//�ֲ���������Ա������ֵ
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
	
}
