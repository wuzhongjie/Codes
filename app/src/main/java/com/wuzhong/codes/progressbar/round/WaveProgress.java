package com.wuzhong.codes.progressbar.round;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.wuzhong.codes.BuildConfig;
import com.wuzhong.codes.R;
import com.wuzhong.codes.utils.ConstantUtil;
import com.wuzhong.codes.utils.MiscUtil;


/**
 * 水波进度条
 * Created by littlejie on 2017/2/26.
 */

public class WaveProgress extends View {

    private static final String TAG = WaveProgress.class.getSimpleName();

    //浅色波浪方向
    private static final int L2R = 0;
    //深色波浪方向
    private static final int R2L = 1;
    //默认尺寸
    private int mDefaultSize;
    //圆心
    private Point mCenterPoint;
    //半径
    private float mRadius;
    //圆的外接矩形
    private RectF mRectF;
    //深色波浪移动距离
    private float mDarkWaveOffset;
    //浅色波浪移动距离
    private float mLightWaveOffset;
    //浅色波浪方向
    private boolean isR2L;
    //是否锁定波浪不随进度移动
    private boolean lockWave;
    //是否开启抗锯齿
    private boolean antiAlias;
    //最大值
    private float mMaxValue;
    //当前值
    private float mValue;
    //当前进度
    private float mPercent;
    //绘制提示信息,提示信息的画笔
    private TextPaint mHintPaint;
    //提示信息的内容
    private CharSequence mHint;
    //提示信息的颜色
    private int mHintColor;
    //提示信息的字号
    private float mHintSize;
    //比例的画笔
    private Paint mPercentPaint;
    //当前值的大小
    private float mValueSize;
    //当前值的颜色
    private int mValueColor;
    //圆环宽度
    private float mCircleWidth;
    //圆环
    private Paint mCirclePaint;
    //圆环颜色
    private int mCircleColor;
    //背景圆环颜色
    private int mBgCircleColor;
    //水波路径
    //水波最小路径
    private Path mWaveLimitPath;
    //水波路径
    private Path mWavePath;
    //水波高度
    private float mWaveHeight;
    //圈内水波波峰数量
    private int mWaveNum;
    //深色水波画笔
    private Paint mWavePaint;
    //深色水波颜色
    private int mDarkWaveColor;
    //浅色水波颜色
    private int mLightWaveColor;
    //深色水波贝塞尔曲线上的起始点、控制点
    private Point[] mDarkPoints;
    //浅色水波贝塞尔曲线上的起始点、控制点
    private Point[] mLightPoints;
    //贝塞尔曲线点的总个数
    private int mAllPointCount;
    //贝塞尔曲线的中点
    private int mHalfPointCount;
    //水波进度动画
    //动画和动画时间
    private ValueAnimator mProgressAnimator;
    //动画时间
    private long mDarkWaveAnimTime;
    //深色水波动画
    private ValueAnimator mDarkWaveAnimator;
    //浅色水波动画时间
    private long mLightWaveAnimTime;
    //浅色水波动画
    private ValueAnimator mLightWaveAnimator;

    public WaveProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        //默认大小计算
        mDefaultSize = MiscUtil.dipToPx(context, ConstantUtil.DEFAULT_SIZE);
        //初始化外框和中点
        mRectF = new RectF();
        mCenterPoint = new Point();
        //初始化属性
        initAttrs(context, attrs);
        //初始化画笔
        initPaint();
        //初始化路径
        initPath();
    }


    /*初始化属性,从xml中读取属性*/
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaveProgress);
        //从xml中获取所有属性的值.
        antiAlias = typedArray.getBoolean(R.styleable.WaveProgress_antiAlias, true);
        mDarkWaveAnimTime = typedArray.getInt(R.styleable.WaveProgress_darkWaveAnimTime, ConstantUtil.DEFAULT_ANIM_TIME);
        mLightWaveAnimTime = typedArray.getInt(R.styleable.WaveProgress_lightWaveAnimTime, ConstantUtil.DEFAULT_ANIM_TIME);
        mMaxValue = typedArray.getFloat(R.styleable.WaveProgress_maxValue, ConstantUtil.DEFAULT_MAX_VALUE);
        mValue = typedArray.getFloat(R.styleable.WaveProgress_value, ConstantUtil.DEFAULT_VALUE);
        mValueSize = typedArray.getDimension(R.styleable.WaveProgress_valueSize, ConstantUtil.DEFAULT_VALUE_SIZE);
        mValueColor = typedArray.getColor(R.styleable.WaveProgress_valueColor, Color.BLACK);

        mHint = typedArray.getString(R.styleable.WaveProgress_hint);
        mHintColor = typedArray.getColor(R.styleable.WaveProgress_hintColor, Color.BLACK);
        mHintSize = typedArray.getDimension(R.styleable.WaveProgress_hintSize, ConstantUtil.DEFAULT_HINT_SIZE);

        mCircleWidth = typedArray.getDimension(R.styleable.WaveProgress_circleWidth, ConstantUtil.DEFAULT_ARC_WIDTH);
        mCircleColor = typedArray.getColor(R.styleable.WaveProgress_circleColor, Color.GREEN);
        mBgCircleColor = typedArray.getColor(R.styleable.WaveProgress_bgCircleColor, Color.WHITE);

        mWaveHeight = typedArray.getDimension(R.styleable.WaveProgress_waveHeight, ConstantUtil.DEFAULT_WAVE_HEIGHT);
        mWaveNum = typedArray.getInt(R.styleable.WaveProgress_waveNum, 1);
        mDarkWaveColor = typedArray.getColor(R.styleable.WaveProgress_darkWaveColor,
                getResources().getColor(android.R.color.holo_blue_dark));
        mLightWaveColor = typedArray.getColor(R.styleable.WaveProgress_lightWaveColor,
                getResources().getColor(android.R.color.holo_green_light));

        isR2L = typedArray.getInt(R.styleable.WaveProgress_lightWaveDirect, R2L) == R2L;
        lockWave = typedArray.getBoolean(R.styleable.WaveProgress_lockWave, false);

        typedArray.recycle();
    }

    /*初始化画笔*/
    private void initPaint() {
        //文字画笔
        mHintPaint = new TextPaint();
        // 设置抗锯齿,会消耗较大资源，绘制图形速度会变慢。
        mHintPaint.setAntiAlias(antiAlias);
        // 设置绘制文字大小
        mHintPaint.setTextSize(mHintSize);
        // 设置画笔颜色
        mHintPaint.setColor(mHintColor);
        // 从中间向两边绘制，不需要再次计算文字
        mHintPaint.setTextAlign(Paint.Align.CENTER);
        //圆画笔
        mCirclePaint = new Paint();
        //抗锯齿/宽度/样式/线冒样式
        mCirclePaint.setAntiAlias(antiAlias);
        mCirclePaint.setStrokeWidth(mCircleWidth);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeCap(Paint.Cap.ROUND);
        //水波画笔
        mWavePaint = new Paint();
        //抗锯齿/样式
        mWavePaint.setAntiAlias(antiAlias);
        mWavePaint.setStyle(Paint.Style.FILL);
        //比例画笔
        mPercentPaint = new Paint();
        //字体锯齿/动画锯齿/颜色/字体大小
        mPercentPaint.setTextAlign(Paint.Align.CENTER);
        mPercentPaint.setAntiAlias(antiAlias);
        mPercentPaint.setColor(mValueColor);
        mPercentPaint.setTextSize(mValueSize);
    }

    //初始化路径
    private void initPath() {
        mWaveLimitPath = new Path();
        mWavePath = new Path();
    }

    /* 测量方法 */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MiscUtil.measure(widthMeasureSpec, mDefaultSize),
                MiscUtil.measure(heightMeasureSpec, mDefaultSize));
    }

    /*尺寸发生变化*/
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChanged: w = " + w + "; h = " + h + "; oldw = " + oldw + "; oldh = " + oldh);
        //直径最小值 = [屏幕宽-左右padding-两倍的圆环线圈宽度]&&[屏幕高-上下padding-两倍圆环宽度]  中的较小的一个
        int minSize = Math.min(getMeasuredWidth() - getPaddingLeft() - getPaddingRight() - 2 * (int) mCircleWidth,
                getMeasuredHeight() - getPaddingTop() - getPaddingBottom() - 2 * (int) mCircleWidth);
        //半径 = 直径最小值/2
        mRadius = minSize / 2;
        //中点为父控件正中心
        mCenterPoint.x = getMeasuredWidth() / 2;
        mCenterPoint.y = getMeasuredHeight() / 2;
        //绘制圆弧的边界,决定外接矩形的尺寸
        mRectF.left = mCenterPoint.x - mRadius - mCircleWidth / 2;
        mRectF.top = mCenterPoint.y - mRadius - mCircleWidth / 2;
        mRectF.right = mCenterPoint.x + mRadius + mCircleWidth / 2;
        mRectF.bottom = mCenterPoint.y + mRadius + mCircleWidth / 2;
        Log.d(TAG, "onSizeChanged: 控件大小 = " + "(" + getMeasuredWidth() + ", " + getMeasuredHeight() + ")"
                + ";圆心坐标 = " + mCenterPoint.toString()
                + ";圆半径 = " + mRadius
                + ";圆的外接矩形 = " + mRectF.toString());
        //初始化波浪点
        initWavePoints();
        //设置当前值,开始动画
        setValue(mValue);
        startWaveAnimator();
    }

    /*初始化波浪点*/
    private void initWavePoints() {
        //当前波浪宽度 = 直径/波峰数量(波峰数量越多,相应的宽度应该越小)
        float waveWidth = (mRadius * 2) / mWaveNum;
        //所有点数量 = 8*圈内波峰数量+1  (圈内波峰*2=全部波峰数量,每个水波循环有4个点,最后结尾有一个点)
        //当波数:所有:半数 = [1:9:4][2:17:8][3:25:12]
        mAllPointCount = 8 * mWaveNum + 1;
        //半数点数量 = 所有点/2
        mHalfPointCount = mAllPointCount / 2;
        //深色/浅色波浪 = getPoint ,得到点的数组
        mDarkPoints = getPoint(false, waveWidth);
        mLightPoints = getPoint(isR2L, waveWidth);
    }

    /**
     * 从左往右或者从右往左获取贝塞尔点
     */
    private Point[] getPoint(boolean isR2L, float waveWidth) {
        //创建点数组
        Point[] points = new Point[mAllPointCount];
        //第1个点特殊处理，即数组的中点  圆心.x ± 半径,圆心.y   如果是从右往左运动,点在右边,反之在左边.
        points[mHalfPointCount] = new Point((int) (mCenterPoint.x + (isR2L ? mRadius : -mRadius)), mCenterPoint.y);
        //屏幕内的贝塞尔曲线点
        //循环,半点往圈内的第一个点 ~ 最后的点,每次循环加4个点    + width
        for (int i = mHalfPointCount + 1; i < mAllPointCount; i += 4) {
            //偏移量的计算
            /*i为中点在整个数组中的位置.因为数组从0开始计,所以points[mHalfPointCount]恰好是波线中央点.
            * 当显示1个波峰的时候,i=5,2个波峰i=9....
            * waveWidth为当前波浪宽度,整个后半部分为
            * */
            float width = points[mHalfPointCount].x+ waveWidth * (i / 4 - mWaveNum);
            //X = [1/4个波线的宽度+偏移量]    Y = [中点y - 波峰高度].
            points[i] = new Point((int) (waveWidth / 4 + width), (int) (mCenterPoint.y - mWaveHeight));
            //X = [1/2个波线的宽度+偏移量]    Y = [中点y]
            points[i + 1] = new Point((int) (waveWidth / 2 + width), mCenterPoint.y);
            //X = [3/4个波线的宽度+偏移量]    Y = [中点y + 波峰高度].
            points[i + 2] = new Point((int) (waveWidth * 3 / 4 + width), (int) (mCenterPoint.y + mWaveHeight));
            //X = [1个波线的宽度+偏移量]    Y = [中点y - 波峰高度].
            points[i + 3] = new Point((int) (waveWidth + width), mCenterPoint.y);
        }
        //屏幕外的贝塞尔曲线点
        for (int i = 0; i < mHalfPointCount; i++) {
            int reverse = mAllPointCount - i - 1;
            points[i] = new Point((isR2L ? 2 : 1) * points[mHalfPointCount].x - points[reverse].x,
                    points[mHalfPointCount].y * 2 - points[reverse].y);
        }
        //对从右向左的贝塞尔点数组反序，方便后续处理
        return isR2L ? MiscUtil.reverse(points) : points;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas);
        drawLightWave(canvas);
        drawDarkWave(canvas);
        drawProgress(canvas);
    }

    /**
     * 绘制圆环
     *
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        canvas.save();
        canvas.rotate(270, mCenterPoint.x, mCenterPoint.y);
        int currentAngle = (int) (360 * mPercent);
        //画背景圆环
        mCirclePaint.setColor(mBgCircleColor);
        canvas.drawArc(mRectF, currentAngle, 360 - currentAngle, false, mCirclePaint);
        //画圆环
        mCirclePaint.setColor(mCircleColor);
        canvas.drawArc(mRectF, 0, currentAngle, false, mCirclePaint);
        canvas.restore();
    }

    /**
     * 绘制深色波浪(贝塞尔曲线)
     *
     * @param canvas
     */
    private void drawDarkWave(Canvas canvas) {
        mWavePaint.setColor(mDarkWaveColor);
        drawWave(canvas, mWavePaint, mDarkPoints, mDarkWaveOffset);
    }

    /**
     * 绘制浅色波浪(贝塞尔曲线)
     *
     * @param canvas
     */
    private void drawLightWave(Canvas canvas) {
        mWavePaint.setColor(mLightWaveColor);
        //从右向左的水波位移应该被减去
        drawWave(canvas, mWavePaint, mLightPoints, isR2L ? -mLightWaveOffset : mLightWaveOffset);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void drawWave(Canvas canvas, Paint paint, Point[] points, float waveOffset) {
        mWaveLimitPath.reset();
        mWavePath.reset();
        float height = lockWave ? 0 : mRadius - 2 * mRadius * mPercent;
        //moveTo和lineTo绘制出水波区域矩形
        mWavePath.moveTo(points[0].x + waveOffset, points[0].y + height);

        for (int i = 1; i < mAllPointCount; i += 2) {
            mWavePath.quadTo(points[i].x + waveOffset, points[i].y + height,
                    points[i + 1].x + waveOffset, points[i + 1].y + height);
        }
        //mWavePath.lineTo(points[mAllPointCount - 1].x, points[mAllPointCount - 1].y + height);
        //不管如何移动，波浪与圆路径的交集底部永远固定，否则会造成上移的时候底部为空的情况
        mWavePath.lineTo(points[mAllPointCount - 1].x, mCenterPoint.y + mRadius);
        mWavePath.lineTo(points[0].x, mCenterPoint.y + mRadius);
        mWavePath.close();
        mWaveLimitPath.addCircle(mCenterPoint.x, mCenterPoint.y, mRadius, Path.Direction.CW);
        //取该圆与波浪路径的交集，形成波浪在圆内的效果
        mWaveLimitPath.op(mWavePath, Path.Op.INTERSECT);
        canvas.drawPath(mWaveLimitPath, paint);
    }

    //前一次绘制时的进度
    private float mPrePercent;
    //当前进度值
    private String mPercentValue;

    private void drawProgress(Canvas canvas) {
        float y = mCenterPoint.y - (mPercentPaint.descent() + mPercentPaint.ascent()) / 2;
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "mPercent = " + mPercent + "; mPrePercent = " + mPrePercent);
        }
        if (mPrePercent == 0.0f || Math.abs(mPercent - mPrePercent) >= 0.01f) {
            mPercentValue = String.format("%.0f%%", mPercent * 100);
            mPrePercent = mPercent;
        }
        canvas.drawText(mPercentValue, mCenterPoint.x, y, mPercentPaint);

        if (mHint != null) {
            float hy = mCenterPoint.y * 2 / 3 - (mHintPaint.descent() + mHintPaint.ascent()) / 2;
            canvas.drawText(mHint.toString(), mCenterPoint.x, hy, mHintPaint);
        }
    }

    public float getMaxValue() {
        return mMaxValue;
    }

    public void setMaxValue(float maxValue) {
        mMaxValue = maxValue;
    }

    public float getValue() {
        return mValue;
    }

    /**
     * 设置当前值
     *
     * @param value
     */
    public void setValue(float value) {
        if (value > mMaxValue) {
            value = mMaxValue;
        }
        float start = mPercent;
        float end = value / mMaxValue;
        Log.d(TAG, "setValue, value = " + value + ";start = " + start + "; end = " + end);
        startAnimator(start, end, mDarkWaveAnimTime);
    }

    private void startAnimator(final float start, float end, long animTime) {
        Log.d(TAG, "startAnimator,value = " + mValue
                + ";start = " + start + ";end = " + end + ";time = " + animTime);
        //当start=0且end=0时，不需要启动动画
        if (start == 0 && end == 0) {
            return;
        }
        mProgressAnimator = ValueAnimator.ofFloat(start, end);
        mProgressAnimator.setDuration(animTime);
        mProgressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPercent = (float) animation.getAnimatedValue();
                if (mPercent == 0.0f || mPercent == 1.0f) {
                    stopWaveAnimator();
                } else {
                    startWaveAnimator();
                }
                mValue = mPercent * mMaxValue;
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "onAnimationUpdate: percent = " + mPercent
                            + ";value = " + mValue);
                }
                invalidate();
            }
        });
        mProgressAnimator.start();
    }

    private void startWaveAnimator() {
        startLightWaveAnimator();
        startDarkWaveAnimator();
    }

    private void stopWaveAnimator() {
        if (mDarkWaveAnimator != null && mDarkWaveAnimator.isRunning()) {
            mDarkWaveAnimator.cancel();
            mDarkWaveAnimator.removeAllUpdateListeners();
            mDarkWaveAnimator = null;
        }
        if (mLightWaveAnimator != null && mLightWaveAnimator.isRunning()) {
            mLightWaveAnimator.cancel();
            mLightWaveAnimator.removeAllUpdateListeners();
            mLightWaveAnimator = null;
        }
    }

    private void startLightWaveAnimator() {
        if (mLightWaveAnimator != null && mLightWaveAnimator.isRunning()) {
            return;
        }
        mLightWaveAnimator = ValueAnimator.ofFloat(0, 2 * mRadius);
        mLightWaveAnimator.setDuration(mLightWaveAnimTime);
        mLightWaveAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mLightWaveAnimator.setInterpolator(new LinearInterpolator());
        mLightWaveAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mLightWaveOffset = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        mLightWaveAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mLightWaveOffset = 0;
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mLightWaveAnimator.start();
    }

    private void startDarkWaveAnimator() {
        if (mDarkWaveAnimator != null && mDarkWaveAnimator.isRunning()) {
            return;
        }
        mDarkWaveAnimator = ValueAnimator.ofFloat(0, 2 * mRadius);
        mDarkWaveAnimator.setDuration(mDarkWaveAnimTime);
        mDarkWaveAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mDarkWaveAnimator.setInterpolator(new LinearInterpolator());
        mDarkWaveAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDarkWaveOffset = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        mDarkWaveAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mDarkWaveOffset = 0;
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mDarkWaveAnimator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopWaveAnimator();
        if (mProgressAnimator != null && mProgressAnimator.isRunning()) {
            mProgressAnimator.cancel();
            mProgressAnimator.removeAllUpdateListeners();
            mProgressAnimator = null;
        }
    }
}
