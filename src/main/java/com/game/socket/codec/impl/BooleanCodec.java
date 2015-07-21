package com.game.socket.codec.impl;

import java.lang.reflect.Field;

import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.game.socket.Message;
import com.game.socket.codec.AbstractCodec;
import com.game.util.ReflectUtil;

public class BooleanCodec extends AbstractCodec {

	private final Logger logger = LoggerFactory.getLogger(BooleanCodec.class);

	private static final Class<?>[] BINDING_CLASSES = new Class<?>[] { boolean.class, Boolean.class };

	@Override
	public Class<?>[] getBindingClasses() {
		return BINDING_CLASSES;
	}

	@Override
	public int write(IoBuffer buf, Object value, Class<?> wrapper) {
		buf.put(((Boolean) value) ? (byte) 1 : (byte) 0);
		return Byte.BYTES;
	}

	@Override
	public int read(IoBuffer buf, Message msg, Field field) {
		byte value = buf.get();
		ReflectUtil.setFieldValue(field, msg, value == 1);
		return Byte.BYTES;
	}

}
