package com.game.util;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 反射工具类
 * 
 * @author 依形掠影
 *
 */
public class ReflectUtil {

	private static Logger logger = LoggerFactory.getLogger(ReflectUtil.class);

	private ReflectUtil() {
	}

	/**
	 * 扫描获取指定包名下的带指定注释的类文件
	 * 
	 * @param annotation
	 * @param packageNames
	 * @return
	 */
	public static Set<Class<?>> scan(Class<? extends Annotation> annotation, String... packageNames) {
		Set<Class<?>> classSet = scan(packageNames);
		Iterator<Class<?>> iter = classSet.iterator();
		while (iter.hasNext()) {
			if (!iter.next().isAnnotationPresent(annotation)) {
				iter.remove();
			}
		}
		return classSet;
	}

	/**
	 * 扫描获取指定包名下的类文件
	 * 
	 * @param packageNames
	 *            待扫描的包名列表
	 * @return
	 */
	public static Set<Class<?>> scan(String... packageNames) {
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		for (String packageName : packageNames) {
			String path = packageName.replace(".", "/");
			try {
				final Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(path);
				while (urls.hasMoreElements()) {
					URL url = urls.nextElement();
					if ("file".equals(url.getProtocol())) {
						File file = new File(url.getPath());
						if (!file.isDirectory()) {
							throw new RuntimeException("package [" + packageName + "] is not directory!");
						}
						classSet.addAll(getClassFromPath(file, packageName));
					} else if ("jar".equals(url.getProtocol())) {
						JarFile jarFile = ((JarURLConnection) url.openConnection()).getJarFile();
						classSet.addAll(getClassFromJar(jarFile, packageName));
					}
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return classSet;
	}

	/**
	 * 从指定路径下装载指定包名的类
	 * 
	 * @param directory
	 *            路径文件
	 * @param packageName
	 *            前缀包名
	 * @return
	 */
	private static Set<Class<?>> getClassFromPath(final File directory, final String packageName) {
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		Stack<File> directories = new Stack<File>();
		directories.add(directory);
		String basePath = packageName.replace(".", "/");

		while (!directories.isEmpty()) {
			File path = directories.pop();
			for (File file : path.listFiles()) {
				if (file.isDirectory()) {
					directories.push(file);
				} else if (file.getName().endsWith(".class")) {
					String fullPath = file.getAbsolutePath().replace("\\", "/");
					if (fullPath.indexOf(basePath) == -1) {
						// 包名不匹配
						continue;
					}
					try {
						Class<?> clazz = Class.forName(
								fullPath.substring(fullPath.indexOf(basePath)).replace(".class", "").replace("/", "."));
						classSet.add(clazz);
					} catch (ClassNotFoundException e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		}
		return classSet;
	}

	/**
	 * 从指定jar文件中装载指定包名的类
	 * 
	 * @param jarFile
	 * @param packageName
	 * @return
	 */
	private static Set<Class<?>> getClassFromJar(final JarFile jarFile, final String packageName) {
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		Enumeration<JarEntry> entries = jarFile.entries();

		String basePath = packageName.replace(".", "/");
		while (entries.hasMoreElements()) {
			JarEntry je = entries.nextElement();
			String name = je.getName();
			if (name.endsWith(".class") && name.startsWith(basePath)) {
				try {
					Class<?> clazz = Class.forName(name.replace(".class", "").replace("/", "."));
					classSet.add(clazz);
				} catch (ClassNotFoundException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}

		return classSet;
	}

}
