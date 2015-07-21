package com.game.socket.codec;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.game.socket.Message;
import com.game.socket.annotation.Protocol;
import com.game.socket.util.SocketUtil;
import com.game.util.ReflectUtil;

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
		// 编解码器扫描
		Collection<Class<?>> codecs = ReflectUtil.scanSubclasses(AbstractCodec.class, "com.game.socket.codec.impl");
		for (Class<?> clazz : codecs) {
			((ICodecAble) ReflectUtil.newInstance(clazz)).registerCodec();
		}
		// 协议扫描
		Collection<Class<?>> messages = ReflectUtil.scanSubclasses(Message.class, "com.game");
		for (Class<?> clazz : messages) {
			if (!clazz.isAnnotationPresent(Protocol.class)) {
				logger.error("xxxxxx 非法的协议消息: class={}", clazz.getName());
				throw new RuntimeException("非法的协议消息: " + clazz.getName());
			}
			long id = SocketUtil.getMessageId((Class<? extends Message>) clazz);
			// 取字段
			List<Field> list = new ArrayList<Field>(clazz.getDeclaredFields().length);
			for (Field field : clazz.getDeclaredFields()) {
				if (Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())
						|| Modifier.isTransient(field.getModifiers())) {
					// final、static、transient修饰的字段不传输
					continue;
				}
				list.add(field);
			}
			if (fieldMap.putIfAbsent(id, list) != null) {
				logger.error("xxxxxx 协议号冲突：class={}", clazz.getName());
				throw new RuntimeException("协议号冲突: " + clazz.getName());
			}
		}
		logger.info("====== 成功加载{}个协议消息", fieldMap.size());
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

		long id = message.getId();
		List<Field> fields = fieldMap.get(id);
		if (fields != null) {
			for (Field field : fields) {
				writeValue(buf, ReflectUtil.getFieldValue(field, message), field.getType(), null);
			}
		} else {
			// TODO 告警并注册
		}

		buf.flip();
		byte[] data = new byte[buf.limit()];
		buf.get(data);
		return data;
	}

	public void writeValue(IoBuffer buf, Object value, Class<?> type, Class<?> wrapper) {
		ICodecAble codec = AbstractCodec.getCodec(type);
		codec.write(buf, value, wrapper);
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
