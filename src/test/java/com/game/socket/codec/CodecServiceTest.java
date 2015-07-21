package com.game.socket.codec;

import org.junit.Test;

import com.game.demo.message.request.ReqLoginMessage;

public class CodecServiceTest {

	@Test
	public void test() {
		ReqLoginMessage msg = new ReqLoginMessage("demo", "pass");
		byte[] bytes = CodecService.getInstance().message2Bytes(msg);

	}

}
