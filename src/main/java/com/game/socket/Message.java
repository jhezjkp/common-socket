package com.game.socket;

import java.io.Serializable;

import com.game.socket.annotation.Protocol;
import com.game.socket.util.SocketUtil;

/**
 * 协议消息基类
 * 
 * @author 依形掠影
 *
 */
public abstract class Message implements Serializable {

	private static final long serialVersionUID = 6113628808134608525L;

	/**
	 * 获取模块编号
	 * 
	 * @return
	 */
	public byte getModule() {
		byte module = -1;
		Protocol protocol = getClass().getAnnotation(Protocol.class);
		if (protocol != null) {
			module = protocol.module();
		}
		return module;
	}

	/**
	 * 获取命令编号
	 * 
	 * @return
	 */
	public int getCmd() {
		int cmd = -1;
		Protocol protocol = getClass().getAnnotation(Protocol.class);
		if (protocol != null) {
			cmd = protocol.cmd();
		}
		return cmd;
	}

	/**
	 * 获取唯一编号，用于消息编/解码
	 * 
	 * @return
	 */
	public long getId() {
		return SocketUtil.getMessageId(getClass());
	}

}
