package com.game.socket.codec;

import java.lang.reflect.Field;
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
	private ConcurrentHashMap<Integer, List<Field>> fieldMap = new ConcurrentHashMap<Integer, List<Field>>();
	/** 消息Map<消息唯一编号, 消息类型> */
	private ConcurrentHashMap<Integer, Class<?>> messageMap = new ConcurrentHashMap<Integer, Class<?>>();

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
			int id = SocketUtil.getMessageId((Class<? extends Message>) clazz);
			// 取字段
			List<Field> list = ReflectUtil.getTransferFields(clazz);
			if (fieldMap.putIfAbsent(id, list) != null) {
				logger.error("xxxxxx 协议号冲突：class={}", clazz.getName());
				throw new RuntimeException("协议号冲突: " + clazz.getName());
			}
			messageMap.put(id, clazz);
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

		int id = message.getId();
		List<Field> fields = fieldMap.get(id);
		if (fields != null) {
			for (Field field : fields) {
				writeValue(buf, ReflectUtil.getFieldValue(field, message), field.getType(),
						ReflectUtil.getWrapperClass(field));
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
		// if (value == null) {
		// System.err.println(type);
		// }
		ICodecAble codec = AbstractCodec.getCodec(type);
		codec.write(buf, value, type, wrapper);
	}

	/**
	 * 将字节数组转换为协议转对象
	 * 
	 * @param id
	 * @param buf
	 * @return
	 */
	public Message bytes2Message(int id, IoBuffer buf) {
		Class<?> type = messageMap.get(id);
		Message msg = null;
		if (type == null) {
			logger.error("未找到对应的消息类型：id={}", id);
		} else {
			try {
				msg = (Message) type.newInstance();
				List<Field> list = fieldMap.get(id);
				for (Field field : list) {
					ReflectUtil.setFieldValue(field, msg, AbstractCodec.getCodec(field.getType()).read(buf,
							field.getType(), ReflectUtil.getWrapperClass(field)));
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return msg;
	}

	/**
	 * 获取入服的解码流量
	 * 
	 * @return
	 */
	public long getInboundBytes() {
		return AbstractCodec.getInboundBytes();
	}

	/**
	 * 获取出服的编码流量
	 * 
	 * @return
	 */
	public long getOutboundBytes() {
		return AbstractCodec.getOutboundBytes();
	}

}
