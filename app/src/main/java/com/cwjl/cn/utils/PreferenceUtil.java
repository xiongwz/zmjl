package com.cwjl.cn.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 首选项工具类
 * 
 * @author cstdingran@gmail.com
 * 
 */
public class PreferenceUtil {
	private static final String PREFERENCE_NAME = "manager";
	private static PreferenceUtil preferenceUtil;
	public static PreferenceUtil getInstance(Context context) {
		if (preferenceUtil == null) {
			preferenceUtil = new PreferenceUtil(context);
		}
		return preferenceUtil;
	}
	private SharedPreferences sp;

	private Editor ed;

	private PreferenceUtil(Context context) {
		init(context);
	}

	public void destroy() {
		sp = null;
		ed = null;
		preferenceUtil = null;
	}

	public boolean getBoolean(String key, boolean defaultboolean) {
		return sp.getBoolean(key, defaultboolean);
	}

	public int getInt(String key, int defaultInt) {
		return sp.getInt(key, defaultInt);
	}

	public long getLong(String key, long defaultlong) {
		return sp.getLong(key, defaultlong);
	}

	public String getString(Context context, String key, String defaultValue) {
		if (sp == null || ed == null) {
			sp = context.getSharedPreferences(PREFERENCE_NAME, 0);
			ed = sp.edit();
		}
		if (sp != null) {
			return sp.getString(key, defaultValue);
		}
		return defaultValue;
	}

	public String getString(String key, String defaultInt) {
		return sp.getString(key, defaultInt);
	}

	public void init(Context context) {
		if (sp == null || ed == null) {
			try {
				sp = context.getSharedPreferences(PREFERENCE_NAME, 0);
				ed = sp.edit();
			} catch (Exception e) {
			}
		}
	}

	public void remove(String key) {
		ed.remove(key);
		ed.commit();
	}

	public void saveBoolean(String key, boolean value) {
		ed.putBoolean(key, value);
		ed.commit();
	}

	public void saveInt(String key, int value) {
		if (ed != null) {
			ed.putInt(key, value);
			ed.commit();
		}
	}

	public void saveLong(String key, long l) {
		ed.putLong(key, l);
		ed.commit();
	}

	public void saveString(String key, String value) {
		ed.putString(key, value);
		ed.commit();
	}

//	public void saveUserInfo(String key, UserInfoModel model) {
//		String obj = new Gson().toJson(model);
//		ed.putString(key, obj);
//		ed.commit();
//	}
//
//	public UserInfoModel getUserInfo(String key, String defaultString) {
//		String json = sp.getString(key, defaultString);
//		Type type = new TypeToken<UserInfoModel>() {}.getType();
//		return new Gson().fromJson(json, type);
//	}
//
//	public void saveOrdinaryUserInfo(String key, OrdinaryUserModel model) {
//		String obj = new Gson().toJson(model);
//		ed.putString(key, obj);
//		ed.commit();
//	}
//
//	public OrdinaryUserModel getOrdinaryUserInfo(String key, String defaultString) {
//		String json = sp.getString(key, defaultString);
//		Type type = new TypeToken<OrdinaryUserModel>() {}.getType();
//		return new Gson().fromJson(json, type);
//	}
}
