package com.cwjl.cn.mvp.home.details;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
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
import com.cwjl.cn.mvp.floating.FloatingImageDisplayService;
import com.cwjl.cn.pub.ViewPagerActivity;
import com.cwjl.cn.view.TopViewLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
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
                if (FloatingImageDisplayService.isStarted) {
                    return;
                }
                if (!Settings.canDrawOverlays(this)) {
                    Toast.makeText(this, "当前无权限，请授权", Toast.LENGTH_SHORT);
                    startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 1);
                } else {
                    startFloatImage();
                }
                break;
        }
    }

    private void startFloatImage() {
        List<String> list = new ArrayList<>();
        list.add(mModel.image);
        ContextApplication.mFloatImageList = list;
        Intent intent = new Intent(this, FloatingImageDisplayService.class);
        startService(intent);
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
                startFloatImage();
            }
        } 
    }
}
