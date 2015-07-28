package com.game.socket.codec.impl;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.game.socket.codec.AbstractCodec;

public class StringCodec extends AbstractCodec {

	private final Logger logger = LoggerFactory.getLogger(StringCodec.class);

	private static final Class<?>[] BINDING_CLASSES = new Class<?>[] { String.class };
	private final Charset charset = Charset.forName("utf-8");

	@Override
	public Class<?>[] getBindingClasses() {
		return BINDING_CLASSES;
	}

	@Override
	public void write(IoBuffer buf, Object value, Class<?> type, Class<?> wrapper) {
		String str = (String) value;
		short len = 0;
		if (str != null) {
			byte[] bytes = str.getBytes(charset);
			len = (short) bytes.length;
			buf.putShort(len);
			buf.put(bytes);
		} else {
			buf.putShort((short) 0);
		}
		outboundBytes.addAndGet(Short.BYTES + len);
	}

	@Override
	public Object read(IoBuffer buf, Class<?> type, Class<?> wrapper) {
		short len = buf.getShort();
		inboundBytes.addAndGet(Short.BYTES);
		String value = null;
		if (len > 0) {
			byte[] bytes = new byte[len];
			buf.get(bytes);
			value = new String(bytes, charset);
			inboundBytes.addAndGet(len);
		}
		return value;
	}

}
