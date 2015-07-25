package com.game.socket.codec.impl;

import java.lang.reflect.Field;
import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.game.socket.Message;
import com.game.socket.codec.AbstractCodec;
import com.game.util.ReflectUtil;

public class StringCodec extends AbstractCodec {

	private final Logger logger = LoggerFactory.getLogger(StringCodec.class);

	private static final Class<?>[] BINDING_CLASSES = new Class<?>[] { String.class };
	private final Charset charset = Charset.forName("utf-8");

	@Override
	public Class<?>[] getBindingClasses() {
		return BINDING_CLASSES;
	}

	@Override
	public int write(IoBuffer buf, Object value, Class<?> type, Class<?> wrapper) {
		String str = (String) value;
		short len = 0;
		if (str != null) {
			len = (short) str.length();
			buf.putShort(len);
			buf.put(str.getBytes(charset));
		} else {
			buf.putShort((short) 0);
		}
		return Short.BYTES + len;
	}

	@Override
	public int read(IoBuffer buf, Message msg, Field field) {
		short len = buf.getShort();
		String value = "";
		if (len > 0) {
			byte[] bytes = new byte[len];
			buf.get(bytes);
			value = new String(bytes, charset);
		}
		ReflectUtil.setFieldValue(field, msg, value);
		return Short.BYTES + value.length();
	}

}
