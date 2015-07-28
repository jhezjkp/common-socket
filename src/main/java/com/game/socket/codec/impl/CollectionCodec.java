package com.game.socket.codec.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.game.socket.codec.AbstractCodec;

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
	public Object read(IoBuffer buf, Class<?> type, Class<?> wrapper) {
		int len = buf.getShort();
		Collection<Object> value = null;
		if (len > 0) {
			if (Set.class.isAssignableFrom(type)) {
				value = new HashSet<Object>();
			} else if (List.class.isAssignableFrom(type)) {
				value = new ArrayList<Object>();
			} else {
				try {
					value = (Collection<Object>) type.newInstance();
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
			int index = 0;
			while (index < len) {
				value.add(getCodec(wrapper).read(buf, wrapper, null));
				index++;
			}
		}
		return value;
	}

}
