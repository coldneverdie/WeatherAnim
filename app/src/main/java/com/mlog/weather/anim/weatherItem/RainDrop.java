package com.mlog.weather.anim.weatherItem;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * 圆形的雨水
 *
 * @author CJL
 * @since 2015-09-12
 */
public class RainDrop extends SimpleWeatherItem {

    private int mXShift = 0;

    //动画持续时间 ms
    static final int ANIM_DURATION = 800;
    // 透明度100中间点
    static final int ALPHA_CENTER = 200;

    public RainDrop() {
        mInterpolator = new AccelerateDecelerateInterpolator();
    }

    /**
     * 设置X轴位置偏移量
     *
     * @param xShift 偏移量,左侧为正，右侧为负
     */
    public void setXShift(int xShift) {
        this.mXShift = xShift;
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint, long time) {
        if (mStartTime == -1) {
            return;
        }

        int t = (int) (time - mStartTime);
        if (t <= mDelayTime) {
            return;
        }
        t -= mDelayTime;

        if (t < ANIM_DURATION) {
            int alpha;
            if (t < ALPHA_CENTER) {
                alpha = 255 * t / ALPHA_CENTER;
            } else {
                alpha = 255 - 255 * (t - ALPHA_CENTER) / (ANIM_DURATION - ALPHA_CENTER);
            }
            paint.setColor(Color.argb(alpha, 255, 255, 255));

            float progress = mInterpolator.getInterpolation((float) t / ANIM_DURATION);
            float x = mBounds.centerX() - mXShift * progress;
            float y = mBounds.top + mBounds.height() * progress;

            canvas.drawCircle(x, y, mBounds.width() / 2, paint);
        } else {
            stop();
        }
    }
}
