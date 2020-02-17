package com.cwjl.cn.pub;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cwjl.cn.R;
import com.cwjl.cn.base.BaseActivity;
import com.cwjl.cn.base.BasePresenter;
import com.cwjl.cn.view.TopViewLayout;
import com.cwjl.cn.view.ZoomImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ViewPagerActivity extends BaseActivity {

    private TopViewLayout mTopview;
    private List<String> pics = new ArrayList<>();
    private ViewPager mViewPager;
    private ImageView[] mImageViews;
    boolean isloact;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pic_view_pager;
    }

    @Override
    public void initData() {
        mTopview = findViewById(R.id.topview);
        mTopview .setFinishActivity(this);

        isloact = getIntent().getBooleanExtra("isloact", false);
        pics.addAll((List<String>) getIntent().getSerializableExtra("pics"));
        mImageViews = new ImageView[pics.size()];
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        mViewPager.setAdapter(new PagerAdapter() {

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = LayoutInflater.from(ViewPagerActivity.this).inflate(R.layout.item_image_viewpage, null);
                ZoomImageView imageView = (ZoomImageView) view.findViewById(R.id.zoomView);
                TextView textView = (TextView) view.findViewById(R.id.tv_index);

                textView.setText((position + 1) + "/" + (pics.size()));

                if (isloact) {
//                    if (pics.get(position).startsWith(GridPicAdapter.START_TAG)) {
//                        Glide.with(ViewPagerActivity.this).load(Constants.PIC_HOST + pics.get(position).replace(GridPicAdapter.START_TAG, "")).error(R.mipmap.default_square).into(imageView);
//                    } else {
                        Glide.with(ViewPagerActivity.this).load(new File(pics.get(position))).error(R.mipmap.default_square).into(imageView);
//                    }
                } else {
                    if (!pics.get(position).contains("http"))
                        Glide.with(ViewPagerActivity.this).load(Constants.PIC_HOST + pics.get(position)).error(R.mipmap.default_square).into(imageView);
                    else
                        Glide.with(ViewPagerActivity.this).load(pics.get(position)).error(R.mipmap.default_square).into(imageView);
                }
                container.addView(view);
                mImageViews[position] = imageView;
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                container.removeView(mImageViews[position]);
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return pics.size();
            }
        });
        mViewPager.setCurrentItem(getIntent().getIntExtra("position", 0));
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

}
