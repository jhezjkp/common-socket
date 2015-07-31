package com.game.socket.util;

import com.game.socket.Message;
import com.game.socket.annotation.Protocol;

/**
 * socket工具类
 * 
 * @author 依形掠影
 *
 */
public class SocketUtil {

	private SocketUtil() {
	}

	/**
	 * 获取协议消息的唯一编号
	 * 
	 * @param clazz
	 * @return
	 */
	public static int getMessageId(Class<? extends Message> clazz) {
		byte module = -1;
		int cmd = -1;
		Protocol protocol = clazz.getAnnotation(Protocol.class);
		if (protocol != null) {
			module = protocol.module();
			cmd = protocol.cmd();
		}
		return (0x1ff & module) << 22 | cmd;
	}

}