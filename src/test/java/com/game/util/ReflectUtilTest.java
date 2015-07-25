package com.game.util;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.game.demo.message.request.ReqLoginMessage;
import com.game.socket.annotation.Protocol;
import com.game.socket.codec.DemoInfo;
import com.game.socket.codec.DemoMessage;

public class ReflectUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testScanPath() {
		Set<Class<?>> set = ReflectUtil.scan("com.game.socket.annotation");
		assertEquals(1, set.size());
		assertEquals(Protocol.class, set.iterator().next());
	}

	@Test
	public void testScanJar() {
		Set<Class<?>> set = ReflectUtil.scan("org.slf4j.spi");
		assertEquals(4, set.size());
	}

	@Test
	public void testScan() {
		Set<Class<?>> set = ReflectUtil.scan("com.game.socket.annotation", "org.slf4j.spi");
		assertEquals(5, set.size());
	}

	@Test
	public void testScanWithAnnotation() {
		Set<Class<?>> set = ReflectUtil.scanWithAnnotation(Protocol.class, "com.game");
		assertEquals(2, set.size());
	}

	@Test
	public void testSetFieldValue() throws NoSuchFieldException, SecurityException {
		ReqLoginMessage msg = new ReqLoginMessage();
		String pwd = "pass";
		ReflectUtil.setFieldValue(ReqLoginMessage.class.getDeclaredField("password"), msg, pwd);
		assertEquals(pwd, msg.getPassword());
	}

	@Test
	public void testGetWrapperClass() throws NoSuchFieldException, SecurityException {
		Field field = DemoMessage.class.getDeclaredField("infoArray");
		assertEquals(DemoInfo.class, ReflectUtil.getWrapperClass(field));

		field = DemoMessage.class.getDeclaredField("infoList");
		assertEquals(DemoInfo.class, ReflectUtil.getWrapperClass(field));
	}

}
