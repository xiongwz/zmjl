package com.cwjl.cn.utils;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.StringRes;

public class AvToast {

	public static void makeText(Context context, String message){
		if(!isMainThread()){
			return;
		}
		Toast.makeText(context,message, Toast.LENGTH_SHORT).show();
	}
	public static void makeText(Context context, @StringRes int message){
		if(!isMainThread()){
			return;
		}
		Toast.makeText(context,message, Toast.LENGTH_SHORT).show();
	}
	public static boolean isMainThread() {
		return Looper.getMainLooper() == Looper.myLooper();
	}
}
