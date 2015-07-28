package com.game.socket.codec;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.game.socket.codec.impl.ArrayCodec;
import com.game.socket.codec.impl.CollectionCodec;
import com.game.socket.codec.impl.WrapperCodec;

/**
 * 抽象编解码器
 * 
 * @author 依形掠影
 *
 */
public abstract class AbstractCodec implements ICodecAble {

	private static final Logger logger = LoggerFactory.getLogger(AbstractCodec.class);

	/** 编解码器Map<属性类型，对应的编解码器> */
	private static final Map<Class<?>, ICodecAble> codecMap = new HashMap<Class<?>, ICodecAble>();
	/** 入服流量 */
	protected static final AtomicLong inboundBytes = new AtomicLong(0);
	/** 出服流量 */
	protected static final AtomicLong outboundBytes = new AtomicLong(0);

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

	/**
	 * 获取入服的解码流量
	 * 
	 * @return
	 */
	protected static long getInboundBytes() {
		return inboundBytes.get();
	}

	/**
	 * 获取出服的编码流量
	 * 
	 * @return
	 */
	protected static long getOutboundBytes() {
		return outboundBytes.get();
	}

}
