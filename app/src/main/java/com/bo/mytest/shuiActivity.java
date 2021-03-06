package com.bo.mytest;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bo.mytest.view.TabWaveView1;
import com.bo.mytest.view.WaveView2;

public class shuiActivity extends AppCompatActivity {

    private TabWaveView1 mChargeWaveView, mChargeWaveView2;
    private TextView mTvProgressPoint;
    private WaveView2 wave2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shui);


        initView();
    }

    private void initView() {

        initView2();
        mhandler.postDelayed(timeRunnable, requestResultTime);
    }


    Handler mhandler = new Handler();

    private void initView2() {
        mChargeWaveView = (TabWaveView1) findViewById(R.id.Cwave);
        mChargeWaveView.setFlowNum(50);
        mChargeWaveView.setWaveSpeed(10);
        mChargeWaveView.setViewAboutType(TabWaveView1.ViewType.CIRCLE_VIEW, TabWaveView1.WaveType.CURVE_WAVE);

        mChargeWaveView2 = (TabWaveView1) findViewById(R.id.Cwave2);
        mChargeWaveView2.setFlowNum(100);
        mChargeWaveView2.setWaveSpeed(60);
        mChargeWaveView2.setWaveMargin(2.5f, 8.5f, 2.5f, 5.5f);
        mChargeWaveView2.setViewAboutType(TabWaveView1.ViewType.RECT_VIEW, TabWaveView1.WaveType.CURVE_WAVE);
    }

    //间隔查询时间
    private long oneSecond = 1000;
    private long requestTime = oneSecond * 60;
    private long requestResultTime = oneSecond * 2;
    private long requestResultSumTime = 0;
    Runnable timeRunnable = new Runnable() {
        @Override
        public void run() {
            setDataWare();
        }
    };
    float dianliang = 0f;

    private void setDataWare() {
//        if (dianliang <= 100) {
//            mhandler.postDelayed(timeRunnable, requestResultTime);
//            dianliang += 5;
//            mTvProgressPoint.setText(dianliang + "%");
        //-----------
//            wave2.setFlowNum(dianliang);
//        } else {
//            mWaveView.setShowWave(false);
//            mWaveView.setVisibility(View.INVISIBLE);
//            mWaveHelper.cancel();
//        }
    }
}
