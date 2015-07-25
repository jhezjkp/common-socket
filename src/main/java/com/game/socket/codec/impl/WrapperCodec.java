package com.game.socket.codec.impl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.game.socket.Message;
import com.game.socket.codec.AbstractCodec;
import com.game.util.ReflectUtil;

public class WrapperCodec extends AbstractCodec {

	private final Logger logger = LoggerFactory.getLogger(WrapperCodec.class);

	private static final Class<?>[] BINDING_CLASSES = new Class<?>[] { WrapperCodec.class };

	/** 属性Map<Bean类型，该Bean需要传输的字段> */
	private ConcurrentHashMap<Class<?>, List<Field>> fieldMap = new ConcurrentHashMap<Class<?>, List<Field>>();

	@Override
	public Class<?>[] getBindingClasses() {
		return BINDING_CLASSES;
	}

	@Override
	public int write(IoBuffer buf, Object value, Class<?> wrapper) {
		List<Field> list = fieldMap.get(wrapper);
		if (list == null) {
			list = ReflectUtil.getTransferFields(wrapper);
			logger.info("register bean {}", wrapper.getSimpleName());
		}
		for (Field field : list) {
			Object v = ReflectUtil.getFieldValue(field, value);
			AbstractCodec.getCodec(field.getType()).write(buf, v, field.getType());
		}
		return 0;
	}

	@Override
	public int read(IoBuffer buf, Message msg, Field field) {
		byte value = buf.get();
		ReflectUtil.setFieldValue(field, msg, value);
		return Byte.BYTES;
	}

}
