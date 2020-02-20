package com.cwjl.cn.utils;

import android.os.Environment;

import java.io.File;

public class PathHolder {
	public final static String ROOT = getRoot() + "/cmjl/";

	public final static String TEMP = getRoot() + "/cmjl/temp/";
	public final static String CATCH = getRoot() + "/cmjl/catch/";  // 缓存文件，清理缓存时将被清理
	public final static String FILE_CACHE = TEMP + "file/";
	public final static String FILE_CACHE_ZIP = FILE_CACHE + "zip/";
	public final static String IMAGE_CACHE = TEMP + "image/";
	public final static String CHAtS_CACHE = TEMP + "chats/";

	public final static String SYSTEM = ROOT + "sys/";
	public final static String IMAGE_LOADER_PATH=SYSTEM+"images/";
	public static String getRoot() {
		String root = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		if (root.endsWith("/")) {
			root = root.substring(0, root.length() - 1);
		}
		return root;
	}
	/**
	 * 
	 * @return
	 */
	public static boolean mkAllPath() {
		String[] paths = {ROOT, TEMP, FILE_CACHE, FILE_CACHE_ZIP, IMAGE_CACHE,SYSTEM,IMAGE_LOADER_PATH };
		for (int k = 0; k < paths.length; k++) {
			if (!mkdir(new File(paths[k]))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @param file
	 * @return
	 */
	static boolean mkdir(File file) {
		if (!file.exists()) {
			return file.mkdirs();
		}
		return true;
	}

}
