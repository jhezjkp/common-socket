package com.game.socket.codec;

import java.util.List;

import com.game.socket.Message;
import com.game.socket.annotation.Protocol;

@Protocol(module = 88, cmd = 888)
public class DemoMessage extends Message {

	// 基础类型
	private byte flag;
	private short len;
	private int age;
	private long gold;
	private float rate;
	private double money;
	private boolean isAdult;

	// 包装类型
	private Byte flag2;
	private Short len2;
	private Integer age2;
	private Long gold2;
	private Float rate2;
	private Double money2;
	private Boolean isAdult2;

	// 对象
	private String name;
	private DemoInfo info;

	// 数组集合
	private byte[] week;
	private List<Long> itemIds;
	private DemoInfo[] infoArray;
	private List<DemoInfo> infoList;

	public byte getFlag() {
		return flag;
	}

	public void setFlag(byte flag) {
		this.flag = flag;
	}

	public short getLen() {
		return len;
	}

	public void setLen(short len) {
		this.len = len;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public long getGold() {
		return gold;
	}

	public void setGold(long gold) {
		this.gold = gold;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public boolean isAdult() {
		return isAdult;
	}

	public void setAdult(boolean isAdult) {
		this.isAdult = isAdult;
	}

	public Byte getFlag2() {
		return flag2;
	}

	public void setFlag2(Byte flag2) {
		this.flag2 = flag2;
	}

	public Short getLen2() {
		return len2;
	}

	public void setLen2(Short len2) {
		this.len2 = len2;
	}

	public Integer getAge2() {
		return age2;
	}

	public void setAge2(Integer age2) {
		this.age2 = age2;
	}

	public Long getGold2() {
		return gold2;
	}

	public void setGold2(Long gold2) {
		this.gold2 = gold2;
	}

	public Float getRate2() {
		return rate2;
	}

	public void setRate2(Float rate2) {
		this.rate2 = rate2;
	}

	public Double getMoney2() {
		return money2;
	}

	public void setMoney2(Double money2) {
		this.money2 = money2;
	}

	public Boolean getIsAdult2() {
		return isAdult2;
	}

	public void setIsAdult2(Boolean isAdult2) {
		this.isAdult2 = isAdult2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DemoInfo getInfo() {
		return info;
	}

	public void setInfo(DemoInfo info) {
		this.info = info;
	}

	public byte[] getWeek() {
		return week;
	}

	public void setWeek(byte[] week) {
		this.week = week;
	}

	public List<Long> getItemIds() {
		return itemIds;
	}

	public void setItemIds(List<Long> itemIds) {
		this.itemIds = itemIds;
	}

	public DemoInfo[] getInfoArray() {
		return infoArray;
	}

	public void setInfoArray(DemoInfo[] infoArray) {
		this.infoArray = infoArray;
	}

	public List<DemoInfo> getInfoList() {
		return infoList;
	}

	public void setInfoList(List<DemoInfo> infoList) {
		this.infoList = infoList;
	}

}
