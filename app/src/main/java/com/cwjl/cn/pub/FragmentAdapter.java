package com.cwjl.cn.pub;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by xiyuan on 2018/8/10.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    String mTitle[];
    ArrayList<Fragment> mFragments;
    public FragmentAdapter(FragmentManager fm) {
        super(fm);

    }
    public FragmentAdapter(FragmentManager fm, ArrayList<Fragment> lists, String[] titles) {
        super(fm);
        this.mTitle=titles;
        this.mFragments=lists;
    }
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(mTitle==null)
            return null;
        return mTitle[position];
    }
}
