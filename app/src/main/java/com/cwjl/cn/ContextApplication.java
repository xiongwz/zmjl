package com.cwjl.cn;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.StrictMode;

import com.cwjl.cn.pub.Constants;
import com.cwjl.cn.utils.PLog;
import com.cwjl.cn.utils.PathHolder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Created by xiyuan on 2018/7/10.
 * 项目Application
 */

public class ContextApplication extends Application {

    public static List<String> mFloatImageList;
    private List<Activity> mList = new LinkedList<Activity>();
    private static ContextApplication instance;
    public static Context mContext;
    protected static Handler handler;
    protected static int mainThreadId;

    @Override
    public void onCreate() {
        super.onCreate();
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            PathHolder.mkAllPath();
//        }
        instance = this;
        mContext = getApplicationContext();
        loadProperties(getApplicationContext());
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        handler = new Handler();
        mainThreadId = android.os.Process.myTid();

    }

    public static Context init(Context c) {
        if (mContext == null) {
            mContext = c;
        }
        return mContext;
    }

    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    public void removeActivity(Activity activity) {
        try {
            if (activity != null && mList.contains(activity))
                mList.remove(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeOtherActivity(Activity currActivity) {
        try {
            for (Activity activity : mList) {
                if (activity != null && !activity.equals(currActivity))
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
//    public static boolean isLogin(){
//        return loginUserBean!=null;
//    }
    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    public synchronized static ContextApplication getInstance() {
        if (instance == null) {
            PLog.w("[Application] instance is null.");
        }
        return instance;
    }

    public static HashMap<String, String> tokenMap = new HashMap<>();
    //    public static Node mNode;
//    public static CopyOnWriteArrayList<Node> nodes=new_message CopyOnWriteArrayList<>();
//    private static UserInfoBean user;
    public final static int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 0;
    public final static int READ_PHONE_STATE_REQUEST_CODE = WRITE_EXTERNAL_STORAGE_REQUEST_CODE + 1;
    public final static int CALL_PHONE_REQUEST_CODE = READ_PHONE_STATE_REQUEST_CODE + 1;
    public final static int CAMERA_REQUEST_CODE = CALL_PHONE_REQUEST_CODE + 1;


    public static void loadProperties(Context context) {
        long time = System.currentTimeMillis();
        try {
            InputStream is = context.getAssets().open("config.properties");
            Properties prop = new Properties();
            prop.load(is);
            Constants.HOST = prop.getProperty("host");
            Constants.PIC_HOST = prop.getProperty("pic_host");
            Constants.SOCKET_CHAT = prop.getProperty("socket_host");
            PLog.e("共耗时：" + (System.currentTimeMillis() - time));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取上下文对象
     *
     * @return context
     */
    public static Context getContext() {
        return mContext;
    }

    /**
     * 获取全局handler
     *
     * @return 全局handler
     */
    public static Handler getHandler() {
        return handler;
    }
    public static int getMainThreadId() {
        return mainThreadId;
    }
}
