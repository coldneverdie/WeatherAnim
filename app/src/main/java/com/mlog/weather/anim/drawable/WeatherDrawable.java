package com.mlog.weather.anim.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import com.mlog.weather.anim.weatherItem.IWeatherItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 天气动画基类
 *
 * @author CJL
 * @since 2015-09-11
 */
public abstract class WeatherDrawable extends Drawable {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private ArrayList<IWeatherItem> mWeatherItems = new ArrayList<>();

    protected boolean mIsRunning = false;

    /**
     * 启动动画
     * <p/>
     * 除非调用stopAnimation，否则不会停止
     */
    public void startAnimation() {
        if (mIsRunning || mWeatherItems.size() == 0) {
            return;
        }

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                invalidateSelf();
                mHandler.post(this);
            }
        });

        long time = SystemClock.elapsedRealtime();
        for (IWeatherItem wi : mWeatherItems) {
            wi.start(time);
        }
        mIsRunning = true;
    }


    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);

        mWeatherItems.clear();
        addWeatherItem(mWeatherItems, getBounds());
        if (!mIsRunning) {
            startAnimation();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        long time = SystemClock.elapsedRealtime();
        for (IWeatherItem wi : mWeatherItems) {
            wi.onDraw(canvas, mPaint, time);
        }
    }

    public void stopAnimation() {
        mHandler.removeCallbacksAndMessages(null);
        for (IWeatherItem wi : mWeatherItems) {
            wi.stop();
        }
        mIsRunning = false;
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    protected Handler getHandler() {
        return mHandler;
    }

    protected List<IWeatherItem> getWeatherItems() {
        return mWeatherItems;
    }

    /**
     * 添加天气动态组件
     *
     * @param weatherItems 组件集合
     * @param rect         Drawable Bounds
     */
    abstract void addWeatherItem(List<IWeatherItem> weatherItems, Rect rect);
}
