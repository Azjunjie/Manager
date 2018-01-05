package com.aitewei.manager.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.aitewei.manager.base.BaseFragment;

import java.util.List;

/**
 * Created by zhangjunjie on 2017/11/6.
 */

public class FragmentViewPagerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> fragments;

    public FragmentViewPagerAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        if (fragments != null) {
            return fragments.size();
        }
        return 0;
    }

}
