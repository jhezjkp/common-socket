package com.game.socket.codec.impl;

import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.game.socket.codec.AbstractCodec;

public class IntegerCodec extends AbstractCodec {

	private final Logger logger = LoggerFactory.getLogger(IntegerCodec.class);

	private static final Class<?>[] BINDING_CLASSES = new Class<?>[] { int.class, Integer.class };

	@Override
	public Class<?>[] getBindingClasses() {
		return BINDING_CLASSES;
	}

	@Override
	public int write(IoBuffer buf, Object value, Class<?> type, Class<?> wrapper) {
		buf.putInt((Integer) value);
		return Integer.BYTES;
	}

	@Override
	public Object read(IoBuffer buf, Class<?> type, Class<?> wrapper) {
		int value = buf.getInt();
		return value;
	}

}
