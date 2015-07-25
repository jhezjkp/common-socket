package com.game.socket.codec.impl;

import java.lang.reflect.Field;
import java.util.Collection;

import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.game.socket.Message;
import com.game.socket.codec.AbstractCodec;
import com.game.util.ReflectUtil;

public class CollectionCodec extends AbstractCodec {

	private final Logger logger = LoggerFactory.getLogger(Collection.class);

	private static final Class<?>[] BINDING_CLASSES = new Class<?>[] { CollectionCodec.class };

	@Override
	public Class<?>[] getBindingClasses() {
		return BINDING_CLASSES;
	}

	@Override
	public int write(IoBuffer buf, Object value, Class<?> type, Class<?> wrapper) {
		// 获取数组长度
		Collection<?> collection = (Collection<?>) value;
		if (collection != null && collection.size() > 0) {
			buf.putShort((short) collection.size());
			for (Object o : collection) {
				getCodec(wrapper).write(buf, o, o.getClass(), null);
			}
		} else {
			buf.putShort((short) 0);
			return Short.BYTES;
		}
		return Byte.BYTES;
	}

	@Override
	public int read(IoBuffer buf, Message msg, Field field) {
		byte value = buf.get();
		ReflectUtil.setFieldValue(field, msg, value);
		return Byte.BYTES;
	}

}
