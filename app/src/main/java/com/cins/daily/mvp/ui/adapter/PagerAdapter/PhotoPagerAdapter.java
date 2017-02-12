package com.cins.daily.mvp.ui.adapter.PagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cins.daily.mvp.ui.fragment.PhotoDetailFragment;

import java.util.List;

/**
 * Created by Eric on 2017/2/10.
 */
public class PhotoPagerAdapter extends FragmentStatePagerAdapter {
    private List<PhotoDetailFragment> mFragmentList;

    public PhotoPagerAdapter(FragmentManager fm, List<PhotoDetailFragment> fragmentList) {
        super(fm);
        mFragmentList = fragmentList;
    }



    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }
}
