package com.game.socket.codec;

import java.util.List;

public class DemoInfo {

	private String test;
	private SubDemoInfo subInfo;
	private List<SubDemoInfo> list;

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public SubDemoInfo getSubInfo() {
		return subInfo;
	}

	public void setSubInfo(SubDemoInfo subInfo) {
		this.subInfo = subInfo;
	}

	public List<SubDemoInfo> getList() {
		return list;
	}

	public void setList(List<SubDemoInfo> list) {
		this.list = list;
	}

}
