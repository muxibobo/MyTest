package com.bo.mytest.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;


/**
 * Author:jianbo
 * <p>
 * Create Time:2020/1/3 10:10
 * <p>
 * Email:1245092675@qq.com
 * <p>
 * Describe:zdy
 */
public class TabWaveView2 extends AppCompatImageView {
    Point mCentrePoint;
    int mNowHeight = 0;//当前水位
    int mRadius = 0;
    //水波是否已满
    boolean isDrawWaveFull = false;
    float mTextSize = 24;//文字大小
    Context mContext;
    int mTranX = 0;//水波平移量
    private Paint mCirclePaint;
    private Paint mOutCirclePaint;
    private Paint mWavePaint1;
    private Paint mWavePaint2;
    private Paint mTextPaint;

    private int mCircleColor = Color.parseColor("#FF2DBBE9");//背景内圆颜色
    private int mOutCircleColor = Color.parseColor("#B0E97D7A");//背景外圆颜色
    private int mWaveColor1 = Color.parseColor("#40E0E951");//水波颜色，上
    private int mWaveColor2 = Color.parseColor("#8000C34B");//水波颜色，下
    private int mBitmapColor = Color.parseColor("#3676B4");
    private int mWaterLevel;// 水目标高度
    private float flowNum = 0;//水目标占百分比这里是整数。
    private float flowNumCount = 100f;//百分比基数
    private int mWaveSpeed = 20;//水波起伏移动速度,越大越快
    private float mUpSpeed = 2f;//水面上升速度,越大越快
    private Paint mBitPaint;
    private WaveType mWaveType = WaveType.CURVE_WAVE;
    ViewType mViewType = ViewType.RECT_VIEW;
    //外圆环宽度
    private float mCircleStrokeWidth;
    //外矩形宽度
    private float mRectStrokeWidth;
    //显示文案
    private String mTextContent = "";
    //更新变化到目标水位的时间
    private long mUpSpeedTime = 1;
    //刷新时间
    private long mhanderTime = 60;

    /**
     * @param context
     */
    public TabWaveView2(Context context) {
        super(context);
        mContext = context;
        init(mContext);
    }

    /**
     * @param context
     * @param attrs
     */
    public TabWaveView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(mContext);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public TabWaveView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(mContext);
    }

    private void init(Context context) {
        mContext = context;
        mCirclePaint = new Paint();
        mCirclePaint.setColor(mCircleColor);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setAntiAlias(true);

        mOutCirclePaint = new Paint();
        mOutCirclePaint.setColor(mOutCircleColor);
        mOutCirclePaint.setStyle(Paint.Style.STROKE);
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
        setTextSize(mTextSize, UnitType.DP);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setAntiAlias(true);

        mBitPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitPaint.setFilterBitmap(true);
        mBitPaint.setColor(mBitmapColor);
        mBitPaint.setDither(true);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        drawCycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initDrawChanged(w, h);
    }

    private int mWidth, mHeight;

    public void initDrawChanged(int width, int height) {
        mWidth = width;
        mHeight = height;
        initWaveChange();
    }

    private void initWaveChange() {
        if (mViewType == ViewType.CIRCLE_VIEW) {
            mRadius = (int) ((mWidth - mCircleStrokeWidth) / 2);
        } else {
            mRadius = (int) ((mHeight > mWidth ? (mHeight / 2) : (mWidth / 2)) - mRectStrokeWidth);
        }
        mCentrePoint = new Point(mWidth / 2, mHeight / 2);
        mWaterLevel = (int) (2 * mRadius * flowNum / flowNumCount);//算出目标水位高度
        mUpSpeed = (float) (mWaterLevel / (mUpSpeedTime * mhanderTime));
        mUpSpeed=mUpSpeed>1?mUpSpeedTime:1;
        if (flowNum < flowNumCount) {
            //不满
            isDrawWaveFull = false;
        } else {
            //水满
            isDrawWaveFull = true;
        }
    }

    public void drawCycle() {
        mhandler.removeCallbacksAndMessages(null);
        if (getVisibility() == VISIBLE) {
            mhandler.postDelayed(mRun, mhanderTime);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mhandler.removeCallbacksAndMessages(null);
    }

    //-----------------循环刷新-----------------

    /**
     * 绘制界面的线程
     *
     * @author Administrator
     */
    private class RenderThread extends Thread {
        @Override
        public void run() {
            // 不停绘制界面，这里是异步绘制，不采用外部通知开启绘制的方式，水波根据数据更新才会开始增长
            switch (mViewType) {
                case RECT_VIEW:
                    if (mNowHeight > mWaterLevel) {
                        //水满重新设置水位高
                        mNowHeight = 0;
                    } else {
                        mNowHeight += mUpSpeed;
                    }
                    break;
                case CIRCLE_VIEW:
                    if (mNowHeight < mWaterLevel) {
                        //未满一直加水
                        mNowHeight += mUpSpeed;
                    }
                    break;
            }

            if (WaveType.CURVE_WAVE == mWaveType) {
                if (mTranX > mRadius) {
                    mTranX = 0;
                }
                mTranX -= mWaveSpeed;
            }
            invalidate();
            drawCycle();
        }
    }

    RenderThread mRun = new RenderThread();
    android.os.Handler mhandler = new android.os.Handler(Looper.getMainLooper());
    //---------------end-------------------

    /**
     * 设置显示文案
     *
     * @param text
     */
    public void setTextContent(String text) {
        mTextContent = text;
        invalidate();
    }

    /**
     * 设置水位占比(50等价于50%)
     *
     * @param num
     */
    public void setFlowNum(float num) {
        flowNum = num;
        initWaveChange();
        invalidate();
    }

    /**
     * @param size     尺寸大小
     * @param unitType 单位（1：dp,2:sp）
     */
    public void setTextSize(float size, UnitType unitType) {
        switch (unitType) {
            case DP:
                mTextSize = dpToPx(mContext, size);
                break;
            case SP:
                mTextSize = spToPx(mContext, size);
                break;
            default:
                mTextSize = size;
                break;
        }
        mTextPaint.setTextSize(mTextSize);
    }

    private int mLeftMargin, mTopMargin, mRightMargin, mBottomMargin;

    /**
     * 设置波浪外边距(单位：dp)
     *
     * @param leftMargin
     * @param topMargin
     * @param rightMargin
     * @param bottomMargin
     */
    public void setRectWaveMargin(float leftMargin, float topMargin, float rightMargin, float bottomMargin) {
        mLeftMargin = dpToPx(mContext, leftMargin);
        mTopMargin = dpToPx(mContext, topMargin);
        mRightMargin = dpToPx(mContext, rightMargin);
        mBottomMargin = dpToPx(mContext, bottomMargin);
    }

    /**
     * 外环宽度
     *
     * @param strokeWidth
     */
    public void setCircleStrokeWidth(float strokeWidth) {
        mCircleStrokeWidth = dpToPx(mContext, strokeWidth);
        initWaveChange();
    }

    /**
     * 矩形宽度
     *
     * @param strokeWidth
     */
    public void setRectStrokeWidth(float strokeWidth) {
        mRectStrokeWidth = dpToPx(mContext, strokeWidth);
        initWaveChange();
    }

    //设置水波起伏速度
    public void setWaveSpeed(int speed) {
        mWaveSpeed = speed;
    }

    //设置水面上升时间(毫秒)
    public TabWaveView2 setUpSpeedTime(long speedTime) {
        mUpSpeedTime = speedTime;
        return this;
    }

    public void startAnimation() {
        initWaveChange();
        invalidate();
    }

    /**
     * @param waveColor1     上波浪颜色
     * @param waveColor2     下波浪颜色
     * @param circleColor    内圆颜色
     * @param outCircleColor 外圈颜色
     */
    public void setViewColor(int waveColor1, int waveColor2, int circleColor, int outCircleColor) {
        mWaveColor1 = waveColor1;
        mWaveColor2 = waveColor2;
        mCircleColor = circleColor;
        mOutCircleColor = outCircleColor;
        mWavePaint1.setColor(mWaveColor1);
        mWavePaint2.setColor(mWaveColor2);
        mCirclePaint.setColor(mCircleColor);
        mOutCirclePaint.setColor(mOutCircleColor);
    }

    /**
     * 将dp换成px，保证尺寸不变
     *
     * @param context
     * @param dpValue
     * @return
     */
    public int dpToPx(Context context, float dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }

    /**
     * 将sp换成px，保证尺寸不变
     *
     * @param context
     * @param spValue
     * @return
     */
    public int spToPx(Context context, float spValue) {
        float scaleDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scaleDensity + 0.5f);
    }

    /**
     * 设置视图形状参数
     *
     * @param mViewType 视图类型
     * @param mWaveType 波浪类型
     */
    public void setViewAboutType(ViewType mViewType, WaveType mWaveType) {
        this.mViewType = mViewType;
        this.mWaveType = mWaveType;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mViewType) {
            case CIRCLE_VIEW:
                drawCircleCanvas(canvas);
                break;
            case RECT_VIEW:
                drawRectCanvas(canvas);
                break;
        }
        drawContentText(canvas);
    }

    /**
     * 居中的文案
     *
     * @param canvas
     */
    private void drawContentText(Canvas canvas) {
        //绘制文字
        mTextContent=mUpSpeed+"_"+mUpSpeedTime+"\n_"+mWaterLevel;
        if (!TextUtils.isEmpty(mTextContent)) {
            Rect mRect = new Rect();
            mTextPaint.getTextBounds(mTextContent, 0, mTextContent.length(), mRect);
            canvas.drawText(mTextContent, mCentrePoint.x, mCentrePoint.y + mRect.height() / 2, mTextPaint);
        }
    }

    /**
     * 水波
     *
     * @param canvas
     */
    private void drawWaveCanvas(Canvas canvas) {
        //计算正弦曲线的路径
        int mH = mCentrePoint.y + mRadius - mNowHeight;
        int left = -mRadius / 2;
        int length = 4 * mRadius;
        Path wavePath1 = new Path();
        wavePath1.moveTo(left, mH);
        if (WaveType.CURVE_WAVE == mWaveType) {
            for (int i = left; i < length; i++) {
                //正弦
                int x = i;
                int y = (int) (Math.sin(Math.toRadians(x + mTranX) / 2) * mRadius / 10);

                wavePath1.lineTo(x, mH + y);
            }
        }
        wavePath1.lineTo(length, mH);
        wavePath1.lineTo(length, mCentrePoint.y + mRadius);
        wavePath1.lineTo(0, mCentrePoint.y + mRadius);
        wavePath1.lineTo(0, mH);

        //-----------------
        Path wavePath2 = new Path();
        wavePath2.moveTo(left, mH);
        if (WaveType.CURVE_WAVE == mWaveType) {
            for (int i = left; i < length; i++) {
                int x = i;
                //余弦
                int y = (int) (Math.cos(Math.toRadians(x + mTranX) / 2) * mRadius / 10);
                wavePath2.lineTo(x, mH + y);
            }
        }
        wavePath2.lineTo(length, mH);
        wavePath2.lineTo(length, mCentrePoint.y + mRadius);
        wavePath2.lineTo(0, mCentrePoint.y + mRadius);
        wavePath2.lineTo(0, mH);
        //------------
        canvas.save();
        //这里与圆形或矩形取交集，除去正弦曲线多画的部分
        Path pc = new Path();
        switch (mViewType) {
            case CIRCLE_VIEW:
                pc.addCircle(mCentrePoint.x, mCentrePoint.y, mRadius, Path.Direction.CCW);
                break;
            case RECT_VIEW:
//                pc.addRect(new RectF(mLeftMargin, mTopMargin, mWidth - mRightMargin, mHeight - mBottomMargin), Path.Direction.CCW);
                pc.addRect(new RectF((int) mRectStrokeWidth + mLeftMargin, (int) mRectStrokeWidth + mTopMargin, (int) (mWidth - mRectStrokeWidth) - mRightMargin, (int) (mHeight - mRectStrokeWidth) - mBottomMargin), Path.Direction.CCW);
                break;
        }
        canvas.clipPath(pc, Region.Op.INTERSECT);
        //clipPath裁剪画布图形，之后在此裁剪后的画布上绘制需要图形
        canvas.drawPath(wavePath1, mWavePaint1);
        canvas.drawPath(wavePath2, mWavePaint2);
        canvas.restore();
    }

    /**
     * 画圆
     *
     * @param canvas
     */
    private void drawCircleCanvas(Canvas canvas) {
        //画背景圆圈
        //画外圆环
        if (mCircleStrokeWidth > 0) {
            mOutCirclePaint.setStrokeWidth(mCircleStrokeWidth);
            canvas.drawCircle(mCentrePoint.x, mCentrePoint.y, mRadius + mCircleStrokeWidth / 2, mOutCirclePaint);
        }
        //画内圆
        canvas.drawCircle(mCentrePoint.x, mCentrePoint.y, mRadius, mCirclePaint);
        if (!isDrawWaveFull) {
            drawWaveCanvas(canvas);
        } else {
            //满了就直接铺满
            canvas.drawCircle(mCentrePoint.x, mCentrePoint.y, mRadius, mWavePaint2);
        }

    }

    /**
     * 画矩形
     *
     * @param canvas
     */
    private void drawRectCanvas(Canvas canvas) {
        //画外矩形
        if (mRectStrokeWidth > 0) {
//            mOutCirclePaint.setStrokeWidth(mRectStrokeWidth);
            canvas.drawRect(new Rect(0, 0, mWidth, mHeight), mOutCirclePaint);
//            canvas.drawRect(new Rect()mCentrePoint.x, mCentrePoint.y, mRadius + mRectStrokeWidth / 2, mOutCirclePaint);
        }
        //画内矩形
        canvas.drawRect(new Rect((int) mRectStrokeWidth, (int) mRectStrokeWidth, (int) (mWidth - mRectStrokeWidth), (int) (mHeight - mRectStrokeWidth)), mCirclePaint);
        drawWaveCanvas(canvas);
    }

    /**
     * 画的view图形类型(矩形，圆形)
     */
    public enum ViewType {
        RECT_VIEW, CIRCLE_VIEW
    }

    /**
     * 波浪线类型（水平线，曲线波浪线）
     */
    public enum WaveType {
        LINE_WAVE, CURVE_WAVE
    }

    /**
     * 单位
     */
    public enum UnitType {
        SP, DP
    }
}
