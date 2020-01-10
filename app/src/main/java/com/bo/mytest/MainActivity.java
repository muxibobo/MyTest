package com.bo.mytest;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.bo.mytest.bean.ChargingOrderModel;
import com.bo.mytest.testclass.HttpClineUtils;
import com.google.gson.Gson;

/**
 * Author:jianbo
 * <p>
 * Create Time:2019/12/4 8:24
 * <p>
 * Email:1245092675@qq.com
 * <p>
 * Describe:
 */
public class MainActivity extends AppCompatActivity {

    private TextView tvc;
    private Button btnJie;
    private Button btnJie2;
    private TextView tvc2;
    private Button btnOkhttp;
    private Button btnRetorfit;
    private Button btnShui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tvc = (TextView) findViewById(R.id.tvc);
        btnJie = (Button) findViewById(R.id.btn_jie);
        btnJie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initChargingOrder();
            }
        });
        btnJie2 = (Button) findViewById(R.id.btn_jie2);
        tvc2 = (TextView) findViewById(R.id.tvc2);
        btnJie2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initChargingOrderFast();
            }
        });

        btnOkhttp = (Button) findViewById(R.id.btn_okhttp);
        btnOkhttp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnOkhttp.setText(HttpClineUtils.okHttpCline());
                    }
                });
            }
        });
        btnRetorfit = (Button) findViewById(R.id.btn_retorfit);
        btnRetorfit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpClineUtils.retorfitCline();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        btnRetorfit.setText(HttpClineUtils.retorfitCline());
                    }
                });
            }
        });
        btnShui = (Button) findViewById(R.id.btn_shui);
        btnShui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, shuiActivity.class));
            }
        });
    }

    public void initChargingOrder() {
        ChargingOrderModel jsonObject = null;
        try {
            jsonObject = new Gson().fromJson(tes, ChargingOrderModel.class);
        } catch (Exception e) {
            Log.e("---===Exception===:", e.toString());
        }
        Log.e("---start--->>", "开始了");
        Log.e("---con--->>", "" + jsonObject.getReChargeOrderDetail().toString());
        Log.e("---end--->>", "-------:" + System.currentTimeMillis());
        tvc.setText(jsonObject.getReChargeOrderDetail().toString());
    }

    String tes = "{\n" +
            "        \"reChargeOrderDetail\":{\n" +
            "            \"tradeNo\":\"111111555454\",\n" +
            "            \"tradeAmount\":50,\n" +
            "            \"tradeType\":1,\n" +
            "            \"tradeTypeName\":\"公众号支付\",\n" +
            "            \"payChannel\":1,\n" +
            "            \"payChannelName\":\"wechat\",\n" +
            "            \"accountCategory\":1,\n" +
            "            \"createTime\":\"2019-12-05 15:10:12\"\n" +
            "        }\n" +
            "    }";

    public void initChargingOrderFast() {
        ChargingOrderModel jsonObject = null;
        try {
            jsonObject = JSON.parseObject(tes, ChargingOrderModel.class);
        } catch (Exception e) {
            Log.e("---===Exception=Fast==:", e.toString());
        }
        Log.e("---start-Fast-->>", "开始了");
        Log.e("---con--Fast->>", "" + jsonObject.getReChargeOrderDetail().toString());
        Log.e("---end--Fast->>", "-------:" + System.currentTimeMillis());
        tvc2.setText(jsonObject.getReChargeOrderDetail().toString());
    }

    Context mm = MainActivity.this;

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
        return super.openOrCreateDatabase(name, mode, factory);
    }

    @Override
    public void registerActivityLifecycleCallbacks(@NonNull Application.ActivityLifecycleCallbacks callback) {
        super.registerActivityLifecycleCallbacks(callback);
    }
}
