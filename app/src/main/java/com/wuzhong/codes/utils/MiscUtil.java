package com.wuzhong.codes.utils;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;

/**
 *
 * Created by Wuzhong on 2017-11-14.
 */

public class MiscUtil {


    //此方法限制了传入参数的最大值,如果传入值比较大,就会选用默认值.
    public static int measure(int measureSpec,int defaultSize){
        int result = defaultSize;
        //获取模式和尺寸
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);
        // 精确尺寸/最大尺寸
        /* 精确尺寸的时候返回尺寸值
        * 最大尺寸的时候返回尺寸值和默认值中较小的那个*/
        if (specMode == View.MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == View.MeasureSpec.AT_MOST) {
            result = Math.min(result, specSize);
        }

        return result;
    }

    /*将dip(独立像素)转化为px(固定像素)*/
    public static int dipToPx(Context context, float dip) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f * (dip >= 0 ? 1 : -1));
    }

    /*格式化*/
    public static String getPrecisionFormat(int precision) {
        return "%." + precision + "f";
    }

    /*翻转数组*/
    public static <T> T[] reverse(T[] arrays) {
        if (arrays == null) {
            return null;
        }
        int length = arrays.length;
        for (int i = 0; i < length / 2; i++) {
            T t = arrays[i];
            arrays[i] = arrays[length - i - 1];
            arrays[length - i - 1] = t;
        }
        return arrays;
    }

    /**
     * 测量文字高度
     * @param paint
     * @return
     */
    public static float measureTextHeight(Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return (Math.abs(fontMetrics.ascent) - fontMetrics.descent);
    }
}
