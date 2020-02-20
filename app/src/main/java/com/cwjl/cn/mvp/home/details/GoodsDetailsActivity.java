package com.cwjl.cn.mvp.home.details;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.cwjl.cn.ContextApplication;
import com.cwjl.cn.R;
import com.cwjl.cn.base.BaseActivity;
import com.cwjl.cn.mvp.downzip.CallBackListener;
import com.cwjl.cn.mvp.downzip.DownloadZipService;
import com.cwjl.cn.mvp.floating.FloatingImageDisplayService;
import com.cwjl.cn.pub.Constants;
import com.cwjl.cn.pub.ViewPagerActivity;
import com.cwjl.cn.utils.AvToast;
import com.cwjl.cn.view.TopViewLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import butterknife.BindView;
import butterknife.OnClick;

public class GoodsDetailsActivity extends BaseActivity<GoodsDetailsPresenter> implements GoodsDetailsView {

    @BindView(R.id.topview)
    TopViewLayout mTopview;
    @BindView(R.id.iv_image)
    ImageView mImageIv;
    @BindView(R.id.tv_use_count)
    TextView mUseCountTv;
    @BindView(R.id.tv_like_count)
    TextView mLikeCountTv;
    @BindView(R.id.tv_name)
    TextView mNameTv;

    private ServiceConnection sc;

    private String mId;
    private GoodsDetailsModel mModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_goods_details;
    }

    @Override
    public void initData() {
        mTopview.setFinishActivity(this);

        mId = getIntent().getStringExtra("id");
        if (!TextUtils.isEmpty(mId)) {
            showLoading();
            mPresenter.getGoodsDetails(mId);
        }
    }

    @Override
    protected GoodsDetailsPresenter createPresenter() {
        return new GoodsDetailsPresenter(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick({R.id.iv_image, R.id.btn_float})
    void click(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.iv_image:
                List<String> paths = new ArrayList<>();
                paths.add(mModel.image);
                intent.setClass(mContext, ViewPagerActivity.class);
                intent.putExtra("pics", (Serializable) paths);
                intent.putExtra("position", 0);
                startActivity(intent);
                break;
            case R.id.btn_float:
//                if (FloatingImageDisplayService.isStarted) {
//                    return;
//                }
                if (!Settings.canDrawOverlays(this)) {
                    Toast.makeText(this, "当前无权限，请授权", Toast.LENGTH_SHORT);
                    startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 1);
                } else {
                    downloadZIP();
                }
                break;
        }
    }

    @Override
    public void getGoodsDetails(GoodsDetailsModel model) {
        hideLoading();
        if (null != model) {
            mModel = model;
            if (!TextUtils.isEmpty(mModel.image)) {
                Glide.with(mContext).load(mModel.image).error(R.mipmap.default_square).placeholder(R.mipmap.default_square).into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        Drawable current = resource.getCurrent();
                        mImageIv.setImageDrawable(current);
                    }
                });
            }

            mUseCountTv.setText("使用量：" + mModel.useCount);
            mLikeCountTv.setText("点赞量：" + mModel.likeCount);
            if (!TextUtils.isDigitsOnly(mModel.name))
                mNameTv.setText(mModel.name);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
                downloadZIP();
            }
        }
    }

    //  权限相关回调接口
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 101:
                for (int ret : grantResults) {
                    if (ret != PackageManager.PERMISSION_GRANTED) {
                        return;
                    } else {
                        startDownload();
                    }
                }
                break;
            default:
                break;
        }
    }


    /**
     * 下载热更新需要的文件
     */
    private void downloadZIP() {
        String[] permission = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE

        };
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(GoodsDetailsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PermissionChecker.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(GoodsDetailsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PermissionChecker.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(GoodsDetailsActivity.this, permission, 101);
            } else {
                startDownload();
            }
        } else {
            startDownload();
        }
    }

    private void startDownload() {
        showLoading();
        if (null != sc) {
            unbindService(sc);
        }
        sc = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                DownloadZipService.Binder binder = (DownloadZipService.Binder) iBinder;
                binder.getService().setUpdateProgressListner(new CallBackListener() {
                    @Override

                    public void CallBack(int code, Object object) {
                        Message msg = new Message();
                        msg.what = 1;
                        handler.sendMessage(msg);
                    }
                });
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
            }
        };
        Intent intent = new Intent(GoodsDetailsActivity.this, DownloadZipService.class);
        intent.putExtra("zipurl", mModel.zip);
        if (sc != null) {
            GoodsDetailsActivity.this.bindService(intent, sc, Context.BIND_AUTO_CREATE);
        }
    }

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            hideLoading();
            startFloatImage();
        }
    };

    private void startFloatImage() {
        Intent intent = new Intent(this, FloatingImageDisplayService.class);
        startService(intent);
    }
}
