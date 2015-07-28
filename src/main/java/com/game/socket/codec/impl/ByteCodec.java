package com.game.socket.codec.impl;

import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.game.socket.codec.AbstractCodec;

public class ByteCodec extends AbstractCodec {

	private final Logger logger = LoggerFactory.getLogger(ByteCodec.class);

	private static final Class<?>[] BINDING_CLASSES = new Class<?>[] { byte.class, Byte.class };

	@Override
	public Class<?>[] getBindingClasses() {
		return BINDING_CLASSES;
	}

	@Override
	public void write(IoBuffer buf, Object value, Class<?> type, Class<?> wrapper) {
		buf.put((Byte) value);
		outboundBytes.addAndGet(Byte.BYTES);
	}

	@Override
	public Object read(IoBuffer buf, Class<?> type, Class<?> wrapper) {
		byte value = buf.get();
		inboundBytes.addAndGet(Byte.BYTES);
		return value;
	}

}
