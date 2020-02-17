package com.cwjl.cn.mvp.home.details;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.cwjl.cn.R;
import com.cwjl.cn.base.BaseActivity;
import com.cwjl.cn.base.BasePresenter;
import com.cwjl.cn.pub.ViewPagerActivity;
import com.cwjl.cn.view.TopViewLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    @OnClick({R.id.iv_image})
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
}
