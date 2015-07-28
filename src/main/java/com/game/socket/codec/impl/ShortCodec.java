package com.game.socket.codec.impl;

import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.game.socket.codec.AbstractCodec;

public class ShortCodec extends AbstractCodec {

	private final Logger logger = LoggerFactory.getLogger(ShortCodec.class);

	private static final Class<?>[] BINDING_CLASSES = new Class<?>[] { short.class, Short.class };

	@Override
	public Class<?>[] getBindingClasses() {
		return BINDING_CLASSES;
	}

	@Override
	public int write(IoBuffer buf, Object value, Class<?> type, Class<?> wrapper) {
		buf.putShort((Short) value);
		return Short.BYTES;
	}

	@Override
	public Object read(IoBuffer buf, Class<?> type, Class<?> wrapper) {
		short value = buf.getShort();
		return value;
	}

}
