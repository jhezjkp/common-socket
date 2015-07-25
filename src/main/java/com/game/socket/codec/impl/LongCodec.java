package com.game.socket.codec.impl;

import java.lang.reflect.Field;

import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.game.socket.Message;
import com.game.socket.codec.AbstractCodec;
import com.game.util.ReflectUtil;

public class LongCodec extends AbstractCodec {

	private final Logger logger = LoggerFactory.getLogger(LongCodec.class);

	private static final Class<?>[] BINDING_CLASSES = new Class<?>[] { long.class, Long.class };

	@Override
	public Class<?>[] getBindingClasses() {
		return BINDING_CLASSES;
	}

	@Override
	public int write(IoBuffer buf, Object value, Class<?> type, Class<?> wrapper) {
		buf.putLong((Long) value);
		return Long.BYTES;
	}

	@Override
	public int read(IoBuffer buf, Message msg, Field field) {
		long value = buf.getLong();
		ReflectUtil.setFieldValue(field, msg, value);
		return Long.BYTES;
	}

}
