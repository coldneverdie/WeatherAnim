package com.mlog.weather.anim.weatherItem;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;

/**
 * 云
 *
 * @author CJL
 * @since 2015-09-12
 */
public class Cloud extends SimpleWeatherItem {

    // 移动最大距离
    float moveDistance;

    // 向上或者向下移动时间
    static final int MOVE_DURATION = 2000;
    // 动画单周期总时间
    static final int ANIM_DURATION = 5000;

    // 各个圆圈信息
    ArrayList<CircleMsg> mCircleMsg = new ArrayList<>(7);

    public Cloud() {
        mInterpolator = new DecelerateInterpolator(3);
    }

    @Override
    public void setBounds(Rect rect) {
        super.setBounds(rect);

        mCircleMsg.clear();

        // 设置各个圆圈半径与位置  按宽度适应
        int w = rect.width();
        moveDistance = 9f / 250 * w;

        //1
        CircleMsg cm = new CircleMsg();
        cm.x = 54f / 250 * w;
        cm.y = 105f / 250 * w;
        cm.r = 25f / 250 * w;
        cm.alpha = 255;
        mCircleMsg.add(cm);

        //2
        cm = new CircleMsg();
        cm.x = 204f / 250 * w;
        cm.y = 115f / 250 * w;
        cm.r = 17.5f / 250 * w;
        cm.alpha = 255;
        mCircleMsg.add(cm);

        //3
        cm = new CircleMsg();
        cm.x = 178f / 250 * w;
        cm.y = 98f / 250 * w;
        cm.r = 35f / 250 * w;
        cm.alpha = 204;
        mCircleMsg.add(cm);

        //4
        cm = new CircleMsg();
        cm.x = 92f / 250 * w;
        cm.y = 90f / 250 * w;
        cm.r = 42.5f / 250 * w;
        cm.alpha = 204;
        mCircleMsg.add(cm);

        //5
        cm = new CircleMsg();
        cm.x = 142f / 250 * w;
        cm.y = 104f / 250 * w;
        cm.r = 28.5f / 250 * w;
        cm.alpha = 255;
        mCircleMsg.add(cm);

        //6
        cm = new CircleMsg();
        cm.x = 152f / 250 * w;
        cm.y = 76f / 250 * w;
        cm.r = 38f / 250 * w;
        cm.alpha = 204;
        mCircleMsg.add(cm);

        //7
        cm = new CircleMsg();
        cm.x = 138f / 250 * w;
        cm.y = 62f / 250 * w;
        cm.r = 45f / 250 * w;
        cm.alpha = 153;
        mCircleMsg.add(cm);
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint, long time) {
        if (mStartTime == -1) {
            return;
        }
        int t = ((int) (time - mStartTime)) % ANIM_DURATION;

        for (int i = 0; i < 7; i++) {
            CircleMsg cm = mCircleMsg.get(i);
            float delayTime = 100 * i;

            if (t < delayTime) {
                paint.setColor(Color.argb(cm.alpha, 255, 255, 255));
                canvas.drawCircle(cm.x, cm.y, cm.r, paint);
            } else if (t < MOVE_DURATION + delayTime) {
                float deltaY = moveDistance * mInterpolator.getInterpolation((t - delayTime) / MOVE_DURATION);
                float y = cm.y - deltaY;

                paint.setColor(Color.argb(cm.alpha, 255, 255, 255));
                canvas.drawCircle(cm.x, y, cm.r, paint);
            } else if (t < MOVE_DURATION * 2 + delayTime) {
//                float deltaY = moveDistance * mInterpolator.getInterpolation((MOVE_DURATION * 2 + delayTime - t) / MOVE_DURATION);
//                float y = cm.y - deltaY;
                float deltaY = moveDistance * mInterpolator.getInterpolation((t - MOVE_DURATION - delayTime) / MOVE_DURATION);
                float y = cm.y - moveDistance + deltaY;

                paint.setColor(Color.argb(cm.alpha, 255, 255, 255));
                canvas.drawCircle(cm.x, y, cm.r, paint);
            } else {
                paint.setColor(Color.argb(cm.alpha, 255, 255, 255));
                canvas.drawCircle(cm.x, cm.y, cm.r, paint);
            }
        }

    }


    private class CircleMsg {
        float x;
        float y;
        float r;
        int alpha;

        @Override
        public String toString() {
            return "CircleMsg{" +
                    "x=" + x +
                    ", y=" + y +
                    ", r=" + r +
                    ", alpha=" + alpha +
                    '}';
        }
    }
}
