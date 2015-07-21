package com.game.socket.codec;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.game.socket.Message;

/**
 * 编码服务
 * 
 * @author 依形掠影
 *
 */
public class CodecService {

	private static Logger logger = LoggerFactory.getLogger(CodecService.class);

	/** 单例 */
	private static CodecService instance;

	/** 属性Map<消息唯一编号，该消息需要传输的字段> */
	private ConcurrentHashMap<Long, List<Field>> fieldMap = new ConcurrentHashMap<Long, List<Field>>();

	private CodecService() {
	}

	public static CodecService getInstance() {
		if (instance != null) {
			return instance;
		}
		synchronized (CodecService.class) {
			if (instance == null) {
				instance = new CodecService();
			}
		}
		return instance;
	}

	/**
	 * 将协议转对象转换为字节数组
	 * 
	 * @param message
	 * @return
	 */
	public byte[] message2Bytes(Message message) {
		IoBuffer buf = IoBuffer.allocate(64);
		buf.setAutoExpand(true);
		buf.setAutoShrink(true);

		for (Field field : message.getClass().getDeclaredFields()) {
			if (Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())
					|| Modifier.isTransient(field.getModifiers())) {
				// final、static、transient修饰的字段不传输
				continue;
			}
		}

		return buf.array();
	}

	/**
	 * 将字节数组转换为协议转对象
	 * 
	 * @param message
	 * @return
	 */
	public Message bytes2Message(byte[] bytes) {
		return null;
	}

}
