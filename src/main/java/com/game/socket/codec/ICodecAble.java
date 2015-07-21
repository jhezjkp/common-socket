package com.game.socket.codec;

import java.lang.reflect.Field;

import org.apache.mina.core.buffer.IoBuffer;

import com.game.socket.Message;

/**
 * 编解码接口
 * 
 * @author 依形掠影
 *
 */
public interface ICodecAble {

	/**
	 * 注册编解码器
	 */
	void registerCodec();

	/**
	 * 获取绑定的类
	 * 
	 * @return
	 */
	Class<?>[] getBindingClasses();

	int write(IoBuffer buf, Object value, Class<?> wrapper);

	int read(IoBuffer buf, Message msg, Field field);

}
