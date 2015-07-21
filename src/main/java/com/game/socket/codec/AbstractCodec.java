package com.game.socket.codec;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractCodec implements ICodecAble {

	private final Logger logger = LoggerFactory.getLogger(AbstractCodec.class);

	private static final Map<Class<?>, ICodecAble> codecMap = new HashMap<Class<?>, ICodecAble>();

	@Override
	public void registerCodec() {
		Class<?>[] clazzArray = getBindingClasses();
		if (clazzArray != null) {
			for (Class<?> clazz : clazzArray) {
				codecMap.put(clazz, this);
				logger.info("register {} to {}", clazz.getSimpleName(), this.getClass().getSimpleName());
			}
		}
	}

	public static ICodecAble getCodec(Class<?> clazz) {
		ICodecAble codec = codecMap.get(clazz);
		if (codec == null) {
			if (clazz.isArray()) {

			}

		}
		return codec;
	}

}
