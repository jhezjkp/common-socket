package com.game.socket.codec.impl;

import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.game.socket.codec.AbstractCodec;

public class DoubleCodec extends AbstractCodec {

	private final Logger logger = LoggerFactory.getLogger(DoubleCodec.class);

	private static final Class<?>[] BINDING_CLASSES = new Class<?>[] { double.class, Double.class };

	@Override
	public Class<?>[] getBindingClasses() {
		return BINDING_CLASSES;
	}

	@Override
	public void write(IoBuffer buf, Object value, Class<?> type, Class<?> wrapper) {
		buf.putDouble((Double) value);
		outboundBytes.addAndGet(Double.BYTES);
	}

	@Override
	public Object read(IoBuffer buf, Class<?> type, Class<?> wrapper) {
		double value = buf.getDouble();
		inboundBytes.addAndGet(Double.BYTES);
		return value;
	}

}
