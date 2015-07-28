package com.game.socket.codec.impl;

import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.game.socket.codec.AbstractCodec;

public class FloatCodec extends AbstractCodec {

	private final Logger logger = LoggerFactory.getLogger(FloatCodec.class);

	private static final Class<?>[] BINDING_CLASSES = new Class<?>[] { float.class, Float.class };

	@Override
	public Class<?>[] getBindingClasses() {
		return BINDING_CLASSES;
	}

	@Override
	public void write(IoBuffer buf, Object value, Class<?> type, Class<?> wrapper) {
		buf.putFloat((Float) value);
		outboundBytes.addAndGet(Float.BYTES);
	}

	@Override
	public Object read(IoBuffer buf, Class<?> type, Class<?> wrapper) {
		float value = buf.getFloat();
		inboundBytes.addAndGet(Float.BYTES);
		return value;
	}

}
