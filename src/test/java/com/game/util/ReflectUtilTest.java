package com.game.util;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.game.socket.annotation.Protocol;

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
		assertEquals(1, set.size());
	}

}
