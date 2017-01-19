package com.cins.daily.mvp.ui.adapter.PagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 2017/1/19.
 */

public class NewsFragmentPagerAdapter extends FragmentPagerAdapter{

    private final List<String> mTitles;
    private List<Fragment> mNewsFragmentList = new ArrayList<>();


    public NewsFragmentPagerAdapter(FragmentManager fm,List<String> titles,List<Fragment> newsFragmentList) {
        super(fm);
        mTitles = titles;
        mNewsFragmentList = newsFragmentList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mNewsFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mNewsFragmentList.size();
    }
}
