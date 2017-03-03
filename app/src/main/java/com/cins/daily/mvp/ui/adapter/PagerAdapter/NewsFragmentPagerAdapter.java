package com.cins.daily.mvp.ui.adapter.PagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cins.daily.mvp.ui.fragment.NewsListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 2017/1/19.
 */

public class NewsFragmentPagerAdapter extends FragmentPagerAdapter {

    private final List<String> mTitles;
    private List<Fragment> mNewsFragmentList = new ArrayList<>();

    public NewsFragmentPagerAdapter(FragmentManager fm, List<String> titles,List<Fragment> newsFragmentList) {
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

    @Override
    public long getItemId(int position) {
        super.getItemId(position);
        if (mNewsFragmentList != null) {
            if (position < mNewsFragmentList.size()) {
                //不同的Fragment分配的HashCode不同，从而实现刷新adapter中的fragment
                return mNewsFragmentList.get(position).hashCode();
            }
        }
        return super.getItemId(position);
    }
}
