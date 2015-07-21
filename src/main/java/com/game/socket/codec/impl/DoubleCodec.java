package com.game.socket.codec.impl;

import java.lang.reflect.Field;

import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.game.socket.Message;
import com.game.socket.codec.AbstractCodec;
import com.game.util.ReflectUtil;

public class DoubleCodec extends AbstractCodec {

	private final Logger logger = LoggerFactory.getLogger(DoubleCodec.class);

	private static final Class<?>[] BINDING_CLASSES = new Class<?>[] { double.class, Double.class };

	@Override
	public Class<?>[] getBindingClasses() {
		return BINDING_CLASSES;
	}

	@Override
	public int write(IoBuffer buf, Object value, Class<?> wrapper) {
		buf.putDouble((Double) value);
		return Double.BYTES;
	}

	@Override
	public int read(IoBuffer buf, Message msg, Field field) {
		double value = buf.getDouble();
		ReflectUtil.setFieldValue(field, msg, value);
		return Double.BYTES;
	}

}
