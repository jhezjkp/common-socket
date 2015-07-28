package com.game.socket.codec.impl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	public int write(IoBuffer buf, Object value, Class<?> type, Class<?> wrapper) {
		List<Field> list = fieldMap.get(type);
		if (list == null) {
			list = ReflectUtil.getTransferFields(type);
			fieldMap.put(type, list);
			logger.info("register bean {}", type.getSimpleName());
		}
		for (Field field : list) {
			Object v = ReflectUtil.getFieldValue(field, value);
			AbstractCodec.getCodec(field.getType()).write(buf, v, field.getType(), ReflectUtil.getWrapperClass(field));
		}
		return 0;
	}

	@Override
	public Object read(IoBuffer buf, Class<?> type, Class<?> wrapper) {
		Object value = null;
		try {
			value = type.newInstance();
			List<Field> list = fieldMap.get(type);
			if (list == null) {
				list = ReflectUtil.getTransferFields(type);
				fieldMap.put(type, list);
				logger.info("register bean {}", type.getSimpleName());
			}
			for (Field field : list) {
				ReflectUtil.setFieldValue(field, value,
						getCodec(field.getType()).read(buf, field.getType(), ReflectUtil.getWrapperClass(field)));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return value;
	}

}
