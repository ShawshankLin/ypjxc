package com.jxc.dao;

import java.io.BufferedInputStream;   
import java.io.FileInputStream;   
import java.io.FileNotFoundException;   
import java.io.FileOutputStream;   
import java.io.IOException;   
import java.io.InputStream;   
import java.io.OutputStream;   
import java.util.Properties;   

public class PropertiesUtils {
	static String profilepath = "src/dbconfig.properties";
	/**
	 * 采用静态方法
	 */
	private static Properties prop = new Properties();
	static {
		try {
			 prop.load(new FileInputStream(profilepath));   
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			System.exit(-1);
		}
	}

	/**
	 * 更新properties文件的键值对 如果该主键已经存在，更新该主键的值； 如果该主键不存在，则插件一对键值。
	 * 
	 * @param keyname
	 *            键名
	 * @param keyvalue
	 *            键值
	 */
	public static void updateProperties(String keyname, String keyvalue) {
		try {
			 prop.load(new FileInputStream(profilepath));   
			// 调用 Hashtable 的方法 put，使用 getProperty 方法提供并行性。
			// 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
			OutputStream fos = new FileOutputStream(profilepath);
			prop.setProperty(keyname, keyvalue);
			// 以适合使用 load 方法加载到 Properties 表中的格式，
			// 将此 Properties 表中的属性列表（键和元素对）写入输出流
			prop.store(fos, "Update '" + keyname + "' value");
		} catch (IOException e) {
			System.err.println("属性文件更新错误");
		}
	}

	/**
	 * 根据KEY取回相应的value
	 * 
	 * @param key
	 * @return
	 */
	public static String getPropertyValue(String key) {
		return prop.getProperty(key);
	}
}