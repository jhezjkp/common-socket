package com.game.socket.codec;

import java.util.Arrays;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((age2 == null) ? 0 : age2.hashCode());
		result = prime * result + flag;
		result = prime * result + ((flag2 == null) ? 0 : flag2.hashCode());
		result = prime * result + (int) (gold ^ (gold >>> 32));
		result = prime * result + ((gold2 == null) ? 0 : gold2.hashCode());
		result = prime * result + ((info == null) ? 0 : info.hashCode());
		result = prime * result + Arrays.hashCode(infoArray);
		result = prime * result + ((infoList == null) ? 0 : infoList.hashCode());
		result = prime * result + (isAdult ? 1231 : 1237);
		result = prime * result + ((isAdult2 == null) ? 0 : isAdult2.hashCode());
		result = prime * result + ((itemIds == null) ? 0 : itemIds.hashCode());
		result = prime * result + len;
		result = prime * result + ((len2 == null) ? 0 : len2.hashCode());
		long temp;
		temp = Double.doubleToLongBits(money);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((money2 == null) ? 0 : money2.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Float.floatToIntBits(rate);
		result = prime * result + ((rate2 == null) ? 0 : rate2.hashCode());
		result = prime * result + Arrays.hashCode(week);
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
		DemoMessage other = (DemoMessage) obj;
		if (age != other.age)
			return false;
		if (age2 == null) {
			if (other.age2 != null)
				return false;
		} else if (!age2.equals(other.age2))
			return false;
		if (flag != other.flag)
			return false;
		if (flag2 == null) {
			if (other.flag2 != null)
				return false;
		} else if (!flag2.equals(other.flag2))
			return false;
		if (gold != other.gold)
			return false;
		if (gold2 == null) {
			if (other.gold2 != null)
				return false;
		} else if (!gold2.equals(other.gold2))
			return false;
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		if (!Arrays.equals(infoArray, other.infoArray))
			return false;
		if (infoList == null) {
			if (other.infoList != null)
				return false;
		} else if (!infoList.equals(other.infoList))
			return false;
		if (isAdult != other.isAdult)
			return false;
		if (isAdult2 == null) {
			if (other.isAdult2 != null)
				return false;
		} else if (!isAdult2.equals(other.isAdult2))
			return false;
		if (itemIds == null) {
			if (other.itemIds != null)
				return false;
		} else if (!itemIds.equals(other.itemIds))
			return false;
		if (len != other.len)
			return false;
		if (len2 == null) {
			if (other.len2 != null)
				return false;
		} else if (!len2.equals(other.len2))
			return false;
		if (Double.doubleToLongBits(money) != Double.doubleToLongBits(other.money))
			return false;
		if (money2 == null) {
			if (other.money2 != null)
				return false;
		} else if (!money2.equals(other.money2))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Float.floatToIntBits(rate) != Float.floatToIntBits(other.rate))
			return false;
		if (rate2 == null) {
			if (other.rate2 != null)
				return false;
		} else if (!rate2.equals(other.rate2))
			return false;
		if (!Arrays.equals(week, other.week))
			return false;
		return true;
	}

}
