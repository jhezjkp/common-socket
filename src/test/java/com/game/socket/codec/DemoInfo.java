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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((list == null) ? 0 : list.hashCode());
		result = prime * result + ((subInfo == null) ? 0 : subInfo.hashCode());
		result = prime * result + ((test == null) ? 0 : test.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DemoInfo other = (DemoInfo) obj;
		if (list == null) {
			if (other.list != null)
				return false;
		} else if (!list.equals(other.list))
			return false;
		if (subInfo == null) {
			if (other.subInfo != null)
				return false;
		} else if (!subInfo.equals(other.subInfo))
			return false;
		if (test == null) {
			if (other.test != null)
				return false;
		} else if (!test.equals(other.test))
			return false;
		return true;
	}

}
