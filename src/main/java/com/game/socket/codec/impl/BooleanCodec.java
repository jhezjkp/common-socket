package com.game.socket.codec.impl;

import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.game.socket.codec.AbstractCodec;

public class BooleanCodec extends AbstractCodec {

	private final Logger logger = LoggerFactory.getLogger(BooleanCodec.class);

	private static final Class<?>[] BINDING_CLASSES = new Class<?>[] { boolean.class, Boolean.class };

	@Override
	public Class<?>[] getBindingClasses() {
		return BINDING_CLASSES;
	}

	@Override
	public int write(IoBuffer buf, Object value, Class<?> type, Class<?> wrapper) {
		buf.put(((Boolean) value) ? (byte) 1 : (byte) 0);
		return Byte.BYTES;
	}

	@Override
	public Object read(IoBuffer buf, Class<?> type, Class<?> wrapper) {
		byte value = buf.get();
		return value == 1;
	}

}
