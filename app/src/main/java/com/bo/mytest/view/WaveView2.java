package com.bo.mytest.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Author:jianbo
 * <p>
 * Create Time:2020/1/3 10:10
 * <p>
 * Email:1245092675@qq.com
 * <p>
 * Describe:
 */
public class WaveView2 extends SurfaceView implements SurfaceHolder.Callback {

    Point mCentrePoint;
    int mNowHeight = 0;//当前水位
    int mRadius = 0;
    boolean mStart = false;//是否开始
    float mTextSise = 12;//文字大小dp
    Context mContext;
    int mTranX = 0;//水波平移量
    private Paint mCirclePaint;
    private Paint mOutCirclePaint;
    private Paint mWavePaint1;
    private Paint mWavePaint2;
    private Paint mTextPaint;
    private SurfaceHolder holder;
    private RenderThread renderThread;
    private boolean isDraw = false;// 控制绘制的开关
    private int mCircleColor = Color.parseColor("#ff2DBBE9");//背景内圆颜色
    private int mOutCircleColor = Color.parseColor("#ff2DBBE9");//背景外圆颜色
    private int mWaveColor1 = Color.parseColor("#ff239FDC");//水波颜色，上
    private int mWaveColor2 = Color.parseColor("#ff166BB4");//水波颜色，下

    private int mWaterLevel;// 水目标高度
    private float flowNum = 0;//水目标占百分比这里是整数。
    private int mWaveSpeed = 5;//水波起伏速度,越大越快
    private int mUpSpeed = 2;//水面上升速度
    private float minCircleShare = 0.92f;//内圆占有率

    /**
     * @param context
     */
    public WaveView2(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        mContext = context;
        init(mContext);
    }

    /**
     * @param context
     * @param attrs
     */
    public WaveView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        mContext = context;
        init(mContext);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public WaveView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
        mContext = context;
        init(mContext);
    }


    private void init(Context context) {
        mContext = context;
        setZOrderOnTop(true);
        holder = this.getHolder();
        holder.addCallback(this);
        holder.setFormat(PixelFormat.TRANSLUCENT);
        renderThread = new RenderThread();

        mCirclePaint = new Paint();
        mCirclePaint.setColor(mCircleColor);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setAntiAlias(true);

        mOutCirclePaint = new Paint();
        mOutCirclePaint.setColor(mOutCircleColor);
        mOutCirclePaint.setStyle(Paint.Style.FILL);
        mOutCirclePaint.setAntiAlias(true);

        mWavePaint1 = new Paint();
        mWavePaint1.setStrokeWidth(1.0F);
        mWavePaint1.setColor(mWaveColor1);
        mWavePaint1.setStyle(Paint.Style.FILL);
        mWavePaint1.setAntiAlias(true);

        mWavePaint2 = new Paint();
        mWavePaint2.setStrokeWidth(1.0F);
        mWavePaint2.setColor(mWaveColor2);
        mWavePaint2.setStyle(Paint.Style.FILL);
        mWavePaint2.setAntiAlias(true);

        mTextPaint = new Paint();
        mTextPaint.setStrokeWidth(1.0F);
        mTextPaint.setColor(Color.WHITE);
        setTextSise(mTextSise);
        mTextPaint.setTextSize(mTextSise);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setAntiAlias(true);


    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mRadius = (int) (0.5 * width * minCircleShare);
        mCentrePoint = new Point(width / 2, height / 2);
        initWaterLevel();
        Log.e("------====----刷新----->>", flowNum + "---" + mWaterLevel);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isDraw = true;
        if (renderThread != null && !renderThread.isAlive())
            renderThread.start();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isDraw = false;

    }

    private void initWaterLevel() {
        if (flowNum < 100) {
            mWaterLevel = (int) (2 * mRadius * flowNum / 100f);//算出目标水位高
        } else {
            mWaterLevel = (int) (2 * mRadius / minCircleShare);
        }
    }

    /**
     * 绘制界面的线程
     *
     * @author Administrator
     */
    private class RenderThread extends Thread {
        @Override
        public void run() {
            // 不停绘制界面，这里是异步绘制，不采用外部通知开启绘制的方式，水波根据数据更新才会开始增长
            while (isDraw) {
                if (mWaterLevel > mNowHeight) {
                    mNowHeight = mNowHeight + mUpSpeed;
                }
                if (mStart) {
                    if (mTranX > mRadius) {
                        mTranX = 0;
                    }
                    mTranX = mTranX - mWaveSpeed;
                }
                drawUI();
            }
            super.run();
        }
    }

    /**
     * 界面绘制
     */
    public void drawUI() {
        Canvas canvas = holder.lockCanvas();
        try {
            drawCanvas(canvas);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null)
                holder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawCanvas(Canvas canvas) {
        //画背景圆圈
        canvas.drawCircle(mCentrePoint.x, mCentrePoint.y, mRadius / minCircleShare, mOutCirclePaint);//内圆
        canvas.drawCircle(mCentrePoint.x, mCentrePoint.y, mRadius, mCirclePaint);//外圆
        Log.e("----------水位----->>", flowNum + "---" + mWaterLevel);
        if (mStart) {
            Log.e("----------水位---开始-->>", flowNum + "----" + mWaterLevel);
            //计算正弦曲线的路径
            int mH = mCentrePoint.y + mRadius - mNowHeight;
            int left = -mRadius /4;
            int length = 8 * mRadius;
            //波浪线2
            Path wavePath2 = new Path();
            wavePath2.moveTo(left, mH);

            for (int i = left; i < length; i++) {
                int x = i;
//                sin函数，x横坐标，y纵坐标，mTranX每次偏移量， 波形起伏mRadius / 4
                int y = (int) (Math.sin(Math.toRadians(x + mTranX/2) ) * mRadius / 10);
                wavePath2.lineTo(x, mH + y);
            }
            wavePath2.lineTo(length, mH);
            wavePath2.lineTo(length, mCentrePoint.y + mRadius);
            wavePath2.lineTo(0, mCentrePoint.y + mRadius);
            wavePath2.lineTo(0, mH);

            //波浪线1
            mH -= mRadius/10;
            int xF = -mRadius*2;
            Path wavePath1 = new Path();
            wavePath1.moveTo(left + xF, mH);

            for (int i = left; i < length; i++) {
                int x = i;
                int y = (int) (Math.sin(Math.toRadians(x + mTranX/2) ) * mRadius / 10);
                wavePath1.lineTo(x + xF, mH + y);
            }
            wavePath1.lineTo(length + xF, mH);
            wavePath1.lineTo(length + xF, mCentrePoint.y + mRadius+mH);

            wavePath1.lineTo(0 + xF, mCentrePoint.y + mRadius+mH);
            wavePath1.lineTo(0 + xF, mH);

            canvas.save();
            //这里与圆形取交集，除去正弦曲线多画的部分
            Path pc = new Path();
            pc.addCircle(mCentrePoint.x, mCentrePoint.y, mRadius, Path.Direction.CCW);
            canvas.clipPath(pc, Region.Op.INTERSECT);
            canvas.drawPath(wavePath1, mWavePaint1);
            canvas.drawPath(wavePath2, mWavePaint2);
            canvas.restore();
            //绘制文字
            canvas.drawText(flowNum + "%", mCentrePoint.x, mCentrePoint.y, mTextPaint);
        }
    }

    public void setFlowNum(float num) {
        flowNum = num;
        mStart = true;
        initWaterLevel();
    }

    public void setTextSise(float s) {
        mTextSise = (int) (s * (mContext.getResources().getDisplayMetrics().density) + 0.5f);
        mTextPaint.setTextSize(mTextSise);
    }

    //设置水波起伏速度
    public void setWaveSpeed(int speed) {
        mWaveSpeed = speed;
    }

    //设置水面上升速度
    public void setUpSpeed(int speed) {
        mUpSpeed = speed;
    }

    /**
     * @param waveColor1     波浪线1
     * @param waveColor2     波浪线2
     * @param circleColor    圆颜色
     * @param outcircleColor 外圈颜色
     */
    public void setColor(int waveColor1, int waveColor2, int circleColor, int outcircleColor) {
        mWaveColor1 = waveColor1;
        mWaveColor2 = waveColor2;
        mCircleColor = circleColor;
        mOutCircleColor = outcircleColor;
        mWavePaint1.setColor(mWaveColor1);
        mWavePaint2.setColor(mWaveColor2);
        mCirclePaint.setColor(mCircleColor);
        mOutCirclePaint.setColor(mOutCircleColor);
    }
//————————————————
//    版权声明：本文为CSDN博主「gengqiquan」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
//    原文链接：https://blog.csdn.net/gengqiquan/article/details/51577185
}
