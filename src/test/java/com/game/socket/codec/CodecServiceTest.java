package com.game.socket.codec;

import static org.junit.Assert.assertArrayEquals;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.junit.Test;

import com.game.demo.message.request.ReqLoginMessage;

public class CodecServiceTest {

	@Test
	public void test() {
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

}
