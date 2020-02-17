package com.cwjl.cn.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * 该类用于调试调用输出log，尽量用默认的tag标签，即只传入msg内容，如遇到临时输出，可以用自定义的tag，不要直接用Log
 * 
 * @author zzm 2011-5-18 下午02:50:51
 */
public class PLog {
	/**
	 * 显示调试输出的开
	 */
	public static final boolean DEBUGGING =true;
	/**
	 * 默认的tag
	 */
	public static final String TAG = "info";

	public static void d(String msg) {
		if (DEBUGGING) {
			Log.d(TAG, msg);
		}
	}

	public static void d(String tag, String msg) {
		if (DEBUGGING) {
			if (tag == null || tag.length() == 0) {
				i(msg);
			} else {
				Log.d(tag, msg);
			}
		}
	}

	public static void e(String msg) {
		if(TextUtils.isEmpty(msg))
			return;
		if (DEBUGGING) {
			Log.e(TAG, msg);
		}
	}

	public static void e(String tag, String msg) {
		if (DEBUGGING) {
			if (tag == null || tag.length() == 0) {
				i(msg);
			} else {
				Log.e(tag, msg);
			}
		}
	}

	public static void e(String tag, String msg, Throwable tr) {
		if (DEBUGGING) {
			if (tag == null || tag.length() == 0) {
				i(msg);
			} else {
				Log.e(tag, msg, tr);
			}
		}
	}

	public static void i(String msg) {
		if(TextUtils.isEmpty(msg))
			return;
		if (DEBUGGING) {
			Log.i(TAG, msg);
		}
	}

	public static void i(String tag, String msg) {
		if (DEBUGGING) {
			if (tag == null || tag.length() == 0) {
				i(msg);
			} else {
				Log.i(tag, msg);
			}
		}
	}

	public static void v(String msg) {
		if (DEBUGGING) {
			Log.v(TAG, msg);
		}
	}

	public static void v(String tag, String msg) {
		if (DEBUGGING) {
			if (tag == null || tag.length() == 0) {
				i(msg);
			} else {
				Log.v(tag, msg);
			}
		}
	}

	public static void w(String msg) {
		if (DEBUGGING) {
			Log.w(TAG, msg);
		}
	}

	public static void w(String tag, String msg) {
		if (DEBUGGING) {
			if (tag == null || tag.length() == 0) {
				i(msg);
			} else {
				Log.w(tag, msg);
			}
		}
	}
	
}
