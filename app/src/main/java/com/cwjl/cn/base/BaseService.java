package com.cwjl.cn.base;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

/**
 * Created by user on 2017/5/15.
 */

public abstract class BaseService<P extends BasePresenter> extends IntentService implements BaseView {
    protected P mvpPresenter;

    public BaseService() {
        super("BaseService");
    }
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */


    public BaseService(String name) {
        super(name);
    }
    @Override
    public void onCreate() {
        mvpPresenter = createPresenter();
        super.onCreate();
    }
    protected abstract P createPresenter();
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }

    @Override
    protected void onHandleIntent( @Nullable Intent intent) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showLoadingFileDialog() {

    }

    @Override
    public void hideLoadingFileDialog() {

    }

    @Override
    public void onProgress(long totalSize, long downSize) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void onErrorCode(BaseModel model) {

    }
}
