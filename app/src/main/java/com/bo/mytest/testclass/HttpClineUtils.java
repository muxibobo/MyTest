package com.bo.mytest.testclass;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Author:jianbo
 * <p>
 * Create Time:2020/1/2 10:21
 * <p>
 * Email:1245092675@qq.com
 * <p>
 * Describe:
 */
public class HttpClineUtils {
    public static String okHttpCline() {
//        RequestBody requestBody =new FormBody.Builder()
//                .add("phone","13530150946")
//                .add("verify_code","123456")
//                .build()
//                .create(MediaType.parse("Content-Type"),"application/json");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestbodyValue);
//        FormBody.create(MediaType.parse("application/json"), requestbodyValue);
        Request.Builder rebuilder = new Request.Builder();
        rebuilder.url("http://120.79.132.71/mobile");
        rebuilder.post(requestBody);
        rebuilder.addHeader("kcyz", headvalue.trim());
        Request request = rebuilder.build();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.callTimeout(15000, TimeUnit.MILLISECONDS);
        OkHttpClient okHttpClient = builder.build();
        final String[] va = {null};
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("---okhttp==onFailure=:", "--start--");
                Log.e("---okhttp==onFailure=:", e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("---okhttp==onResponse=:", "--start--");
                Log.e("---okhttp==onResponse=:", va[0] = response.body().string());
            }
        });
        Log.e("---okhttp==onResponse=:", "_____________________________________________\n" + va[0]);
        return va[0];
    }

    static String requestbodyValue = "{\"currPage\":1,\"distanceSort\":1,\"isOnlyShowFree\":0,\"latitude\":37.421998333333335,\"longitude\":-122.08400000000002,\"pageSize\":20,\"pileType\":0}";
    static String headvalue = "{\"client-ip\":\"192.168.200.2\",\"client-type\":3,\"client-version\":\"1.0.0\"," +
            "\"currTime\":\"2020-01-02 01:07:49 444\",\"device-id\":\"59C55475FF97D098006F70C27A0B4672\"," +
            "\"device-type\":1,\"language\":\"en_US\",\"protocol-code\":\"0201\",\"session-id\":\"F0E3ADB41774AB19BBA4398C860BF13B\"}}\n";

    public static String retorfitCline() {
        final String[] va = {null};
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl("http://120.79.132.71/")
//                .client(okHttpClient)
                .build();
        RetorfitHttpService httpService = retrofit.create(RetorfitHttpService.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), requestbodyValue);
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json;charset=UTF-8");
        headerMap.put("kcyz", headvalue.trim());
        httpService.get0201(headvalue.trim(), "13530150946", "123456")
//        httpService.get0201HeaderMap(headerMap, body)
//        httpService.get0201Header(headvalue.trim(), body)
//                .enqueue(new HttpCallback<ResponseBody>() {
//                    @Override
//                    public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
//                        try {
//                            Log.e("--retrofit=onResponse=:", "start---:" + response.headers() + response.raw() + response.message() + response.body().string());
//                            va[0] = response.body().string();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        Log.e("--retrofit=onResponse=:", va[0]);
//                    }
//
//                    @Override
//                    public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
//                        Log.e("--retrofit=onFailure=:", "start");
//                        Log.e("--retrofit=onFailure=:", t.toString());
//                        va[0] = t.toString();
//                    }
//                });
//        httpService.get0201Header(headvalue.trim(), body)
                .enqueue(new HttpCallback<ResponseBody>() {
                    @Override
                    public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                        super.onResponse(call, response);
                        try {
                            Log.e("--retrofit=onResponse=:", "start---:" + response.headers() + response.raw() + response.message() + response.body().string());
                            va[0] = response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.e("--retrofit=onResponse=:", va[0]);
                    }

                    @Override
                    public void onFailure(retrofit2.Call call, Throwable t) {
                        super.onFailure(call, t);
                    }
                });
        return va[0];
    }

    public interface RetorfitHttpService {
        //该接口只支持json格式提交，需要用RequestBody提交
        /*
         * 表单提交
         * */
        @POST("mobile")/*这里的{id} 表示是一个变量*/
//        @Headers({"Content-Type: application/json;charset=UTF-8"})
        @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
        @FormUrlEncoded
        retrofit2.Call<ResponseBody> get0201(@Header("kcyz") String headers, @Field("phone") String phone, @Field("verify_code") String verify_code);

        //------------------------------------------------------------------
        /*
         * RequestBody提交json格式数据
         * */
        @POST("mobile")
        retrofit2.Call<ResponseBody> get0201HeaderMap(@HeaderMap Map<String, String> headers, @Body RequestBody body);

        @Headers({"Content-Type: application/json"})
        @POST("mobile")
        retrofit2.Call<ResponseBody> get0201Header(@Header("kcyz") String headers, @Body RequestBody body);
    }
}
