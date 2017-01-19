package com.cins.daily.mvp.ui.fragment.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cins.daily.App;
import com.cins.daily.di.component.FragmentComponent;
import com.cins.daily.di.module.FragmentModule;
import com.cins.daily.mvp.presenter.base.BasePresenter;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by Eric on 2017/1/16.
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment {

    protected FragmentComponent mFragmentComponent;
    protected T mPresenter;

    public abstract void initInjecor();
    public abstract void initViews(View view);
    public abstract int getLayoutId();

    public FragmentComponent getFragmentComponent() {
        return mFragmentComponent;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentComponent = DaggerFragmentComponent.builder()
                .applicationComponent(((App) getActivity().getApplication()).getApplicationComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
        initInjecor();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

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
