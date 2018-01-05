package com.aitewei.manager.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseFragment extends Fragment {

    protected BaseActivity activity;

    //用于butterKnife解绑
    private Unbinder unbinder;
    //管理Destroy取消订阅者
    protected CompositeDisposable compositeDisposable;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (BaseActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        if (isSupportRx()) {
            compositeDisposable = new CompositeDisposable();
        }
        initView(view, savedInstanceState);
        initData();
        return view;
    }

    /**
     * @return 界面布局资源id
     */
    protected abstract int getLayoutId();

    protected abstract void initView(View view, Bundle savedInstanceState);

    protected abstract void initData();

    /**
     * 页面是否使用了rxjava
     *
     * @return 默认true-实例CompositeDisposable
     */
    protected boolean isSupportRx() {
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        activity = null;
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
            compositeDisposable = null;
        }
    }

}
