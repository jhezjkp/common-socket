package com.game.util;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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
	public static Set<Class<?>> scanWithAnnotation(Class<? extends Annotation> annotation, String... packageNames) {
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
	 * 扫描获取指定包名下的指定类的子类(不包含本身)
	 * 
	 * @param annotation
	 * @param packageNames
	 * @return
	 */
	public static Set<Class<?>> scanSubclasses(Class<?> superClass, String... packageNames) {
		Set<Class<?>> classSet = scan(packageNames);
		Iterator<Class<?>> iter = classSet.iterator();
		while (iter.hasNext()) {
			Class<?> clazz = iter.next();
			if (!superClass.isAssignableFrom(clazz) || superClass.equals(clazz)) {
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

	/**
	 * 获取属性值
	 * 
	 * @param field
	 * @param target
	 * @return
	 */
	public static Object getFieldValue(Field field, Object target) {
		try {
			field.setAccessible(true);
			return field.get(target);
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 设置属性值
	 * 
	 * @param field
	 * @param target
	 * @param value
	 */
	public static void setFieldValue(Field field, Object target, Object value) {
		try {
			field.setAccessible(true);
			field.set(target, value);
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 实例化类对象
	 * 
	 * @param clazz
	 * @return
	 */
	public static <T> T newInstance(Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 获取指定类型中需要传输的字段列表
	 * 
	 * @param clazz
	 * @return
	 */
	public static List<Field> getTransferFields(Class<?> clazz) {
		List<Field> list = new ArrayList<Field>(clazz.getDeclaredFields().length);
		for (Field field : clazz.getDeclaredFields()) {
			if (Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())
					|| Modifier.isTransient(field.getModifiers())) {
				// final、static、transient修饰的字段不传输
				continue;
			}
			list.add(field);
		}
		return list;
	}

	/**
	 * 获取数组或集合中的数据类型，普通的直接返回原型
	 * 
	 * @param field
	 * @return
	 */
	public static Class<?> getWrapperClass(Field field) {
		Class<?> wrapper = field.getType();
		if (Collection.class.isAssignableFrom(wrapper)) {
			wrapper = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
		} else if (field.getType().isArray()) {
			wrapper = field.getType().getComponentType();
		}
		return wrapper;
	}

}
