package com.game.socket.codec;

import static org.junit.Assert.assertArrayEquals;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;
import org.junit.Test;

import com.game.demo.message.request.ReqLoginMessage;

public class CodecServiceTest {

	@Test
	public void testSimpleWrite() {
		String userName = "demo";
		String pass = "pass";
		ReqLoginMessage msg = new ReqLoginMessage(userName, pass);
		byte[] bytes = CodecService.getInstance().message2Bytes(msg);
		IoBuffer buf = IoBuffer.allocate(32);
		buf.setAutoExpand(true);
		buf.setAutoShrink(true);
		buf.putShort((short) userName.length());
		buf.put(userName.getBytes(Charset.forName("utf-8")));
		buf.putShort((short) pass.length());
		buf.put(pass.getBytes(Charset.forName("utf-8")));
		buf.flip();
		byte[] ioBytes = new byte[buf.limit()];
		buf.get(ioBytes);
		assertArrayEquals(ioBytes, bytes);
	}

	@Test
	public void testWrite() {
		String name = "kvs";
		DemoMessage msg = new DemoMessage();
		byte n = 1;
		msg.setFlag(n++);
		msg.setLen(n++);
		msg.setAge(n++);
		msg.setGold(n++);
		msg.setRate(n++);
		msg.setMoney(n++);
		msg.setAdult(false);

		msg.setFlag2(n++);
		msg.setLen2(new Short(n++));
		msg.setAge2(new Integer(n++));
		msg.setGold2(new Long(n++));
		msg.setRate2(new Float(n++));
		msg.setMoney2(new Double(n++));
		msg.setIsAdult2(false);

		msg.setName(name);

		byte[] week = new byte[] { 0, 1, 2 };
		msg.setWeek(week);
		List<Long> itemIds = new ArrayList<Long>();
		msg.setItemIds(itemIds);

		SubDemoInfo sdi = new SubDemoInfo();
		sdi.setId(888888);
		sdi.setName(name);
		DemoInfo info = new DemoInfo();
		info.setSubInfo(sdi);
		info.setList(Arrays.asList(new SubDemoInfo[] { sdi }));

		msg.setInfo(info);
		msg.setInfoArray(new DemoInfo[] { info });
		msg.setInfoList(Arrays.asList(new DemoInfo[] { info }));

		byte[] bytes = CodecService.getInstance().message2Bytes(msg);

		IoBuffer buf = IoBuffer.allocate(32);
		buf.setAutoExpand(true);
		buf.setAutoShrink(true);
		buf.put(msg.getFlag());
		buf.putShort(msg.getLen());
		buf.putInt(msg.getAge());
		buf.putLong(msg.getGold());
		buf.putFloat(msg.getRate());
		buf.putDouble(msg.getMoney());
		buf.put(msg.isAdult() ? (byte) 1 : (byte) 0);

		buf.put(msg.getFlag2());
		buf.putShort(msg.getLen2());
		buf.putInt(msg.getAge2());
		buf.putLong(msg.getGold2());
		buf.putFloat(msg.getRate2());
		buf.putDouble(msg.getMoney2());
		buf.put(msg.getIsAdult2() ? (byte) 1 : (byte) 0);

		buf.putShort((short) name.length());
		buf.put(name.getBytes(Charset.forName("utf-8")));

		// ===DemoInfo开始====
		int len = info.getTest() == null ? 0 : info.getTest().length();
		buf.putShort((short) len);
		if (len > 0) {
			buf.put(info.getTest().getBytes(Charset.forName("utf-8")));
		}
		// ===DemoInfo之SubDemo开始====
		buf.putLong(sdi.getId());
		len = sdi.getName() == null ? 0 : sdi.getName().length();
		buf.putShort((short) len);
		if (len > 0) {
			buf.put(sdi.getName().getBytes(Charset.forName("utf-8")));
		}
		// ===DemoInfo之SubDemo结束====
		buf.putShort((short) info.getList().size());
		// ===DemoInfo之lis的SubDemo开始====
		buf.putLong(sdi.getId());
		len = sdi.getName() == null ? 0 : sdi.getName().length();
		buf.putShort((short) len);
		if (len > 0) {
			buf.put(sdi.getName().getBytes(Charset.forName("utf-8")));
		}
		// ===DemoInfo之lis的SubDemo结束====
		// ===DemoInfo结束====

		// week数组
		buf.putShort((short) msg.getWeek().length);
		for (byte b : week) {
			buf.put(b);
		}

		// 长整型列表
		buf.putShort((short) msg.getItemIds().size());
		for (Long i : msg.getItemIds()) {
			buf.putLong(i);
		}

		// DemoInfo数组
		buf.putShort((short) msg.getInfoArray().length);
		for (DemoInfo di : msg.getInfoArray()) {
			// ===DemoInfo开始====
			len = di.getTest() == null ? 0 : di.getTest().length();
			buf.putShort((short) len);
			if (len > 0) {
				buf.put(di.getTest().getBytes(Charset.forName("utf-8")));
			}
			// ===DemoInfo之SubDemo开始====
			buf.putLong(sdi.getId());
			len = sdi.getName() == null ? 0 : sdi.getName().length();
			buf.putShort((short) len);
			if (len > 0) {
				buf.put(sdi.getName().getBytes(Charset.forName("utf-8")));
			}
			// ===DemoInfo之SubDemo结束====
			buf.putShort((short) di.getList().size());
			// ===DemoInfo之lis的SubDemo开始====
			buf.putLong(sdi.getId());
			len = sdi.getName() == null ? 0 : sdi.getName().length();
			buf.putShort((short) len);
			if (len > 0) {
				buf.put(sdi.getName().getBytes(Charset.forName("utf-8")));
			}
			// ===DemoInfo之lis的SubDemo结束====
			// ===DemoInfo结束====
		}

		// DemoInfo列表
		buf.putShort((short) msg.getInfoList().size());
		for (DemoInfo di : msg.getInfoList()) {
			// ===DemoInfo开始====
			len = di.getTest() == null ? 0 : di.getTest().length();
			buf.putShort((short) len);
			if (len > 0) {
				buf.put(di.getTest().getBytes(Charset.forName("utf-8")));
			}
			// ===DemoInfo之SubDemo开始====
			buf.putLong(sdi.getId());
			len = sdi.getName() == null ? 0 : sdi.getName().length();
			buf.putShort((short) len);
			if (len > 0) {
				buf.put(sdi.getName().getBytes(Charset.forName("utf-8")));
			}
			// ===DemoInfo之SubDemo结束====
			buf.putShort((short) di.getList().size());
			// ===DemoInfo之lis的SubDemo开始====
			buf.putLong(sdi.getId());
			len = sdi.getName() == null ? 0 : sdi.getName().length();
			buf.putShort((short) len);
			if (len > 0) {
				buf.put(sdi.getName().getBytes(Charset.forName("utf-8")));
			}
			// ===DemoInfo之lis的SubDemo结束====
			// ===DemoInfo结束====
		}
		// buf.put(pass.getBytes(Charset.forName("utf-8")));
		buf.flip();
		byte[] ioBytes = new byte[buf.limit()];
		buf.get(ioBytes);
		assertArrayEquals(ioBytes, bytes);
	}

}
