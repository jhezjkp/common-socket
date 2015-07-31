package com.game.socket.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 协议号注解
 * 
 * @author 依形掠影
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Protocol {

	/**
	 * 模块号，取值范围为[1, 511]<br/>
	 * (占10位，考虑到最终的协议号要保留符号位故最大值为2<sup>9</sup>-1)
	 * 
	 * @return
	 */
	byte module();

	/**
	 * 命令号，取值范围为[1, 4193303]<br/>
	 * (占22位，由于是低位，不用考虑符号位故最大值为2<sup>22</sup>-1)
	 * 
	 * @return
	 */
	int cmd();

}
