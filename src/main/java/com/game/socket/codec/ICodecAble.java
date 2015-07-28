package com.game.socket.codec;

import org.apache.mina.core.buffer.IoBuffer;

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

	/**
	 * 编码
	 * 
	 * @param buf
	 *            数组缓存
	 * @param value
	 *            数据
	 * @param type
	 *            数据类型
	 * @param wrapper
	 *            内部类型(如果数据是数组或集合，则有该字段)
	 * @return
	 */
	int write(IoBuffer buf, Object value, Class<?> type, Class<?> wrapper);

	/**
	 * 解码
	 * 
	 * @param buf
	 *            数组缓存
	 * @param type
	 *            数据类型
	 * @param wrapper
	 *            内部类型(如果数据是数组或集合，则有该字段)
	 * @return
	 */
	Object read(IoBuffer buf, Class<?> type, Class<?> wrapper);

}
