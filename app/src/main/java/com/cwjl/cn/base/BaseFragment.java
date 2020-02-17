package com.cwjl.cn.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cwjl.cn.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;

/**
 * Created by xiyuan on 2018/7/11.
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView {

    public Context mContext;
    private LoadDialog mLoadingDialog;
    public Toast mToast;
    protected P mPresenter;

    protected abstract int getLayoutId();

    public abstract void initData();

    protected abstract P createPresenter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mPresenter = createPresenter();
        View view = inflater.inflate(getLayoutId(), null);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    /**
     * @param s
     */
    public void showToast(String s) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, s, Toast.LENGTH_LONG);
        }
        mToast.setText(s);
        mToast.show();
    }

    public void hideFileDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    private void closeLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    private void showLoadingDialog() {

        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadDialog(mContext, R.style.MyDialog, R.layout.dialog_loading);
        }
        mLoadingDialog.setCancelable(false);
        mLoadingDialog.show();
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        closeLoadingDialog();
    }

    @Override
    public void showError(String msg) {
        showToast(msg);
    }

    @Override
    public void onErrorCode(BaseModel model) {
    }

    @Override
    public void showLoadingFileDialog() {
    }

    @Override
    public void hideLoadingFileDialog() {
        hideFileDialog();
    }

    @Override
    public void onProgress(long totalSize, long downSize) {
    }
}
