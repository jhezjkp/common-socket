package com.game.socket.codec.impl;

import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.game.socket.codec.AbstractCodec;

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
	public Object read(IoBuffer buf, Class<?> type, Class<?> wrapper) {
		long value = buf.getLong();
		return value;
	}

}
