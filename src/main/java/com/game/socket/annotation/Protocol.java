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
	 * 模块号
	 * 
	 * @return
	 */
	byte module();

	/**
	 * 命令号
	 * 
	 * @return
	 */
	int cmd();

}
