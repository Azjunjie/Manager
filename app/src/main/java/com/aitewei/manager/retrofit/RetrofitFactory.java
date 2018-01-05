package com.aitewei.manager.retrofit;

import com.aitewei.manager.utils.LogUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zjj on 2017/10/30.
 */

public class RetrofitFactory {

    private static final String TAG = RetrofitFactory.class.getName();

    private static final int DEFAULT_TIMEOUT = 30;//单位：s

    private RetrofitFactory() {
    }

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    LogUtil.e(TAG, message + "");
                }
            }).setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();

    private static ApiService apiService = new Retrofit.Builder()
            .baseUrl(ApiService.BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiService.class);

    public static ApiService getInstance() {
        return apiService;
    }

}
