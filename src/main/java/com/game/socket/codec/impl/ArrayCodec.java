package com.game.socket.codec.impl;

import java.lang.reflect.Array;

import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.game.socket.codec.AbstractCodec;

public class ArrayCodec extends AbstractCodec {

	private final Logger logger = LoggerFactory.getLogger(ArrayCodec.class);

	private static final Class<?>[] BINDING_CLASSES = new Class<?>[] { ArrayCodec.class };

	@Override
	public Class<?>[] getBindingClasses() {
		return BINDING_CLASSES;
	}

	@Override
	public void write(IoBuffer buf, Object value, Class<?> type, Class<?> wrapper) {
		// 获取数组长度
		if (value == null) {
			buf.putShort((short) 0);
			outboundBytes.addAndGet(Short.BYTES);
		} else {
			int len = Array.getLength(value);
			buf.putShort((short) len);
			outboundBytes.addAndGet(Short.BYTES);
			int index = 0;
			while (index < len) {
				Object v = Array.get(value, index);
				getCodec(wrapper).write(buf, v, v.getClass(), null);
				index++;
			}
		}
	}

	@Override
	public Object read(IoBuffer buf, Class<?> type, Class<?> wrapper) {
		Object value = null;
		int len = buf.getShort();
		inboundBytes.addAndGet(Short.BYTES);
		if (len > 0) {
			value = Array.newInstance(wrapper, len);
			int index = 0;
			while (index < len) {
				Array.set(value, index, getCodec(wrapper).read(buf, wrapper, null));
				index++;
			}
		}
		return value;
	}

}
