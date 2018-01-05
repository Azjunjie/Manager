package com.aitewei.manager.rxjava;

import android.text.TextUtils;

import com.aitewei.manager.base.BaseEntity;
import com.aitewei.manager.utils.LogUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by zjj on 2017/10/30.
 */

public abstract class BaseObserver<T extends BaseEntity> implements Observer<T> {

    private CompositeDisposable compositeDisposable;

    private String TAG = "BaseObserver";

    public BaseObserver(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void onSubscribe(Disposable disposable) {
        if (compositeDisposable != null) {
            compositeDisposable.add(disposable);
        }
    }

    @Override
    public void onNext(T value) {
        LogUtil.e(TAG, "onNext");
        String code = value.getCode();
        String msg = value.getMsg();
        if ("1".equals(code)) {
            onHandleSuccess(value);
        } else {
            if (!TextUtils.isEmpty(msg)) {
                onHandleRequestError(code, msg);
            } else {
                onHandleRequestError("", "网络异常，请稍后重试");
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        LogUtil.e(TAG, "onError=" + e.getMessage());
        onHandleRequestError("", "网络异常，请稍后重试");
    }

    @Override
    public void onComplete() {
        LogUtil.e(TAG, "onComplete");
    }

    /**
     * 数据请求成功
     *
     * @param entity 网络请求结果的实体集
     */
    protected abstract void onHandleSuccess(T entity);

    /**
     * 数据请求失败
     *
     * @param code 异常码
     * @param msg  异常提示信息
     */
    protected abstract void onHandleRequestError(String code, String msg);

}
