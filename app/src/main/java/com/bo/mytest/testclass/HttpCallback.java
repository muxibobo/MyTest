package com.bo.mytest.testclass;

import android.util.Log;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author:jianbo
 * <p>
 * Create Time:2020/1/2 15:49
 * <p>
 * Email:1245092675@qq.com
 * <p>
 * Describe:
 */
public class HttpCallback<T> implements Callback<T> {

    @Override
    public void onFailure(Call call, Throwable t) {

    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        try {
            Log.e("--Base=onResponse=:", "code---:" + response.code() + "===============>>\n" + ((ResponseBody) response.body()).string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
