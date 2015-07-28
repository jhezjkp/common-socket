package com.game.socket.codec;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;
import org.junit.Test;

import com.game.demo.message.request.ReqLoginMessage;

public class CodecServiceTest {

	private byte[] getReqLoginMessageByteData(ReqLoginMessage msg) {
		String userName = msg.getUserName();
		String pass = msg.getPassword();
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
		return ioBytes;
	}

	@Test
	public void testSimpleWrite() {
		ReqLoginMessage msg = new ReqLoginMessage("demo", "pass");
		byte[] bytes = CodecService.getInstance().message2Bytes(msg);
		assertArrayEquals(getReqLoginMessageByteData(msg), bytes);
	}

	@Test
	public void testSimpleRead() {
		ReqLoginMessage msg = new ReqLoginMessage("demo", "pass");
		IoBuffer buf = IoBuffer.allocate(32);
		buf.setAutoExpand(true);
		buf.setAutoShrink(true);
		buf.put(getReqLoginMessageByteData(msg));
		buf.flip();
		ReqLoginMessage rmsg = (ReqLoginMessage) CodecService.getInstance().bytes2Message(msg.getId(), buf);
		assertEquals(msg.getUserName(), rmsg.getUserName());
		assertEquals(msg.getPassword(), rmsg.getPassword());
	}

	private byte[] getDemoMessageByteData(DemoMessage msg) {
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

		String name = msg.getName();
		buf.putShort((short) name.length());
		buf.put(name.getBytes(Charset.forName("utf-8")));

		// ===DemoInfo开始====
		DemoInfo info = msg.getInfo();
		int len = info.getTest() == null ? 0 : info.getTest().length();
		buf.putShort((short) len);
		if (len > 0) {
			buf.put(info.getTest().getBytes(Charset.forName("utf-8")));
		}
		// ===DemoInfo之SubDemo开始====
		SubDemoInfo sdi = info.getSubInfo();
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
		for (byte b : msg.getWeek()) {
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
		return ioBytes;
	}

	private DemoMessage buildDemoMessage() {
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
		itemIds.add(99999L);
		msg.setItemIds(itemIds);

		SubDemoInfo sdi = new SubDemoInfo();
		sdi.setId(888888);
		sdi.setName(name);
		DemoInfo info = new DemoInfo();
		info.setTest("this is a test...");
		info.setSubInfo(sdi);
		info.setList(Arrays.asList(new SubDemoInfo[] { sdi }));

		msg.setInfo(info);
		msg.setInfoArray(new DemoInfo[] { info });
		msg.setInfoList(Arrays.asList(new DemoInfo[] { info }));

		return msg;
	}

	@Test
	public void testWrite() {
		DemoMessage msg = buildDemoMessage();

		byte[] bytes = CodecService.getInstance().message2Bytes(msg);

		assertArrayEquals(getDemoMessageByteData(msg), bytes);
	}

	@Test
	public void testRead() {
		DemoMessage msg = buildDemoMessage();
		IoBuffer buf = IoBuffer.allocate(32);
		buf.setAutoExpand(true);
		buf.setAutoShrink(true);
		buf.put(getDemoMessageByteData(msg));
		buf.flip();

		DemoMessage rmsg = (DemoMessage) CodecService.getInstance().bytes2Message(msg.getId(), buf);
		assertEquals(msg.getFlag(), rmsg.getFlag());
		assertEquals(msg.getLen(), rmsg.getLen());
		assertEquals(msg.getAge(), rmsg.getAge());
		assertEquals(msg.getGold(), rmsg.getGold());
		assertEquals(msg.getRate(), rmsg.getRate(), 0.00001);
		assertEquals(msg.getMoney(), rmsg.getMoney(), 0.00001);
		assertEquals(msg.isAdult(), rmsg.isAdult());

		assertEquals(msg.getFlag2(), rmsg.getFlag2());
		assertEquals(msg.getLen2(), rmsg.getLen2());
		assertEquals(msg.getAge2(), rmsg.getAge2());
		assertEquals(msg.getGold2(), rmsg.getGold2());
		assertEquals(msg.getRate2(), rmsg.getRate2(), 0.00001);
		assertEquals(msg.getMoney2(), rmsg.getMoney2(), 0.00001);
		assertEquals(msg.getIsAdult2(), rmsg.getIsAdult2());

		assertEquals(msg.getName(), rmsg.getName());
		assertEquals(msg.getItemIds(), rmsg.getItemIds());

		assertEquals(msg.getInfo().getTest(), rmsg.getInfo().getTest());
		assertEquals(msg.getInfo(), rmsg.getInfo());
		assertArrayEquals(msg.getWeek(), rmsg.getWeek());
		assertArrayEquals(msg.getInfoArray(), rmsg.getInfoArray());
		assertEquals(msg.getInfoList(), rmsg.getInfoList());
		assertEquals(msg, rmsg);
	}
}
