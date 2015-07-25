package com.game.socket.codec;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.game.socket.codec.impl.ArrayCodec;
import com.game.socket.codec.impl.CollectionCodec;
import com.game.socket.codec.impl.WrapperCodec;

public abstract class AbstractCodec implements ICodecAble {

	private static final Logger logger = LoggerFactory.getLogger(AbstractCodec.class);

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
				// 数组
				codec = codecMap.get(ArrayCodec.class);
			} else if (Collection.class.isAssignableFrom(clazz)) {
				// 集合
				codec = codecMap.get(CollectionCodec.class);
			} else {
				codec = codecMap.get(WrapperCodec.class);
			}
			// logger.error("no codec found for " + clazz.getSimpleName());
		}
		return codec;
	}

}
