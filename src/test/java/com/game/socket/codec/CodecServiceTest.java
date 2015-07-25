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
		String userName = "demo";
		String pass = "pass";
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

		byte[] week = new byte[] { 0, 1, 2 };
		msg.setWeek(week);
		List<Long> itemIds = new ArrayList<Long>();
		msg.setItemIds(itemIds);

		SubDemoInfo sdi = new SubDemoInfo();
		DemoInfo info = new DemoInfo();
		info.setSubInfo(sdi);
		info.setList(Arrays.asList(new SubDemoInfo[] { sdi }));

		msg.setInfo(info);
		msg.setInfoArray(new DemoInfo[] { info });
		msg.setInfoList(Arrays.asList(new DemoInfo[] { info }));

		byte[] bytes = CodecService.getInstance().message2Bytes(msg);
		// IoBuffer buf = IoBuffer.allocate(32);
		// buf.setAutoExpand(true);
		// buf.setAutoShrink(true);
		// buf.putShort((short) userName.length());
		// buf.put(userName.getBytes(Charset.forName("utf-8")));
		// buf.putShort((short) pass.length());
		// buf.put(pass.getBytes(Charset.forName("utf-8")));
		// buf.flip();
		// byte[] ioBytes = new byte[buf.limit()];
		// buf.get(ioBytes);
		// assertArrayEquals(ioBytes, bytes);
	}

}
