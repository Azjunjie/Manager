package com.aitewei.manager.common;

import android.content.Context;
import android.text.TextUtils;

import com.aitewei.manager.utils.LogUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangjunjie on 2017/11/8.
 */

public class User implements Serializable {
    private static final long serialVersionUID = 6849794470754667710L;

    private String userId;
    private String userName;
    private List<String> data;

    private static User user = new User();

    public static User newInstance() {
        return user;
    }

    public static boolean isLogin() {
        return !TextUtils.isEmpty(User.newInstance().getUserName());
    }

    private User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userId = userName;
        this.userName = userName;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        if ("jhy".equals(userName)) {//计划员
            data.clear();
            data.add("12");//查看船舶列表
            data.add("7");//报表查看权限
        } else if ("zdy".equals(userName)) {//指导员
            data.clear();
            data.add("12");//查看船舶列表
            data.add("56");//设置船舱位置
            data.add("78");//设置清舱状态
            data.add("21");//设置完成状态
            data.add("7");//报表查看权限
        } else if ("ptry".equals(userName)) {//普通人员
            data.clear();
            data.add("12");//查看船舶列表
            data.add("7");//报表查看权限
        }
        this.data = data;
    }

    public static void onSaveUser(final Context context) {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e1) throws Exception {
                File file = new File(context.getCacheDir(), "user.data");
                FileOutputStream out = null;
                ObjectOutputStream objOut = null;
                try {
                    out = new FileOutputStream(file);
                    objOut = new ObjectOutputStream(out);
                    objOut.writeObject(User.newInstance());
                    objOut.flush();
                    e1.onNext(true);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (objOut != null) {
                        try {
                            objOut.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        LogUtil.e("saveUser=" + aBoolean);
                    }
                });
    }

    public static void onReadUser(final Context context) {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e1) throws Exception {
                File file = new File(context.getCacheDir(), "user.data");
                FileInputStream in = null;
                ObjectInputStream objIn = null;
                try {
                    in = new FileInputStream(file);
                    objIn = new ObjectInputStream(in);
                    User user = (User) objIn.readObject();
                    User.newInstance().setUserName(user.getUserName());
                    User.newInstance().setData(user.getData());
                    e1.onNext(true);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (objIn != null) {
                        try {
                            objIn.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        LogUtil.e("readUser=" + aBoolean);
                    }
                });
    }

}
