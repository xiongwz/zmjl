package com.cwjl.cn.base;

import android.content.Intent;

import com.cwjl.cn.ContextApplication;
import com.cwjl.cn.pub.Constants;
import com.cwjl.cn.utils.PLog;
import com.cwjl.cn.utils.PreferenceUtil;
import com.cwjl.cn.utils.StringUtil;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * 作者： ch
 * 时间： 2016/12/27.13:56
 * 描述：
 * 来源：
 */

public abstract class BaseObserver<T> extends DisposableObserver<T> {
    protected BaseView view;
    private int mPageIndex;
    /**
     * 解析数据失败
     */
    public static final int PARSE_ERROR = 1001;
    /**
     * 网络问题
     */
    public static final int BAD_NETWORK = 1002;
    /**
     * 连接错误
     */
    public static final int CONNECT_ERROR = 1003;
    /**
     * 连接超时
     */
    public static final int CONNECT_TIMEOUT = 1004;


    public
    BaseObserver(BaseView view, int pageIndex) {
        this.view = view;
        this.mPageIndex = pageIndex;
    }

    @Override
    protected void onStart() {
        if (view != null && mPageIndex <= 1) {
//            view.showLoading();
        }
    }

    @Override
    public void onNext(T o) {
        try {
            //{"code":"SYS0004","msg":"登录已过期，请重新登录"}
            onSuccess(o);
//            BaseModel model = (BaseModel) o;
//            if ("0".equals(model.code)) {
//                onSuccess(o);
//            } else if ("SYS0004".equals(model.code) || "SYS0003".equals(model.code)/* || "ZTX0002".equals(model.code)*/){
//                PreferenceUtil preference = PreferenceUtil.getInstance(ContextApplication.mContext);
//                preference.saveBoolean(Constants.AUTO_LOGIN, false);
//            } else if ("SYS0005".equals(model.code)) { // 信息不完善
//                onSuccess(o);
//            } else {
//                onError(StringUtil.delHTMLTag(model.msg));
//            }
        } catch (Exception e) {
            e.printStackTrace();
            onError(e.toString());
            PLog.e("异常", e.toString());
        }
    }

    @Override
    public void onError(Throwable e) {
        if (view != null) {
            view.hideLoading();
        }
        if (e instanceof HttpException) {
            //   HTTP错误
            onException(BAD_NETWORK);
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {
            //   连接错误
            onException(CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {
            //  连接超时
            onException(CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            //  解析错误
            onException(PARSE_ERROR);
        } else {
            if (e != null) {
                onError(e.toString());
            } else {
                onError("未知错误");
            }
        }

    }

    private void onException(int unknownError) {
        switch (unknownError) {
            case CONNECT_ERROR:
                onError("连接错误");
                break;

            case CONNECT_TIMEOUT:
                onError("连接超时");
                break;

            case BAD_NETWORK:
//                onError("网络问题");
                onError("网络请求异常");
                break;

            case PARSE_ERROR:
                onError("解析数据失败");
                break;

            default:
                break;
        }
    }


    @Override
    public void onComplete() {
        if (view != null) {
            view.hideLoading();
        }

    }


    public abstract void onSuccess(T o);

    public abstract void onError(String msg);


}
