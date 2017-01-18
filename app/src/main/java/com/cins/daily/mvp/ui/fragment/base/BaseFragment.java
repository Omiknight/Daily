package com.cins.daily.mvp.ui.fragment.base;

import android.support.v4.app.Fragment;

import com.cins.daily.App;
import com.cins.daily.mvp.presenter.base.BasePresenter;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by Eric on 2017/1/16.
 */

public class BaseFragment<T extends BasePresenter> extends Fragment {

    protected T mPresenter;
    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = App.getRefWatcher(getActivity());
        refWatcher.watch(this);
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }
}
