package com.cwjl.cn.mvp.main;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cwjl.cn.R;
import com.cwjl.cn.base.BaseActivity;
import com.cwjl.cn.base.BasePresenter;
import com.cwjl.cn.mvp.home.HomeFragment;
import com.cwjl.cn.mvp.personalcenter.PersonalCenterFragment;
import com.hjm.bottomtabbar.BottomTabBar;

public class MainActivity extends BaseActivity {

    @BindView(R.id.bottom_bar)
    BottomTabBar mBottomBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        setViews();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    private void setViews() {
        mBottomBar.setFontSize(getResources().getDimension(R.dimen.font_12));
        mBottomBar.init(getSupportFragmentManager(), 750, 1334);
        mBottomBar.addTabItem("首页", R.drawable.ic_tab_home_select, R.drawable.ic_tab_home_unselect, HomeFragment.class)
                .addTabItem("我的", R.drawable.ic_tab_personal_center_select, R.drawable.ic_tab_personal_center_unselect, PersonalCenterFragment.class)
                .setSpot(0, 0, false)
                .setSpot(1, 0, false);
        mBottomBar.setOnTabChangeListener(new BottomTabBar.OnTabChangeListener() {
            @Override
            public void onTabChange(int position, String name, View view) {
//                if (position == 1)
//                    mBottomBar.setSpot(2, 3, true);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int position = intent.getIntExtra("position", 0);
        mBottomBar.setCurrentTab(position);
    }

}
