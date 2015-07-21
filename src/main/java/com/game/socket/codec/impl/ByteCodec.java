package com.game.socket.codec.impl;

import java.lang.reflect.Field;

import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.game.socket.Message;
import com.game.socket.codec.AbstractCodec;
import com.game.util.ReflectUtil;

public class ByteCodec extends AbstractCodec {

	private final Logger logger = LoggerFactory.getLogger(ByteCodec.class);

	private static final Class<?>[] BINDING_CLASSES = new Class<?>[] { byte.class, Byte.class };

	@Override
	public Class<?>[] getBindingClasses() {
		return BINDING_CLASSES;
	}

	@Override
	public int write(IoBuffer buf, Object value, Class<?> wrapper) {
		buf.put((Byte) value);
		return Byte.BYTES;
	}

	@Override
	public int read(IoBuffer buf, Message msg, Field field) {
		byte value = buf.get();
		ReflectUtil.setFieldValue(field, msg, value);
		return Byte.BYTES;
	}

}
