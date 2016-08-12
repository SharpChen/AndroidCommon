package cn.opensrc.comnlib.utils;

import android.content.Context;

/**
 * Author:       sharp
 * Created on:   8/10/16 12:57 AM
 * Description:  单位转换
 * Revisions:
 */
public final class DensityUtils {
    private DensityUtils(){}

    /**
     * dp to px
     * @param context Application Context
     * @param dpValue dp
     * @return dp
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px to dp
     * @param context Application Context
     * @param pxValue px
     * @return px
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * px to sp
     * @param context Application Context
     * @param pxValue px
     * @return px
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources( ).getDisplayMetrics( ).scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * sp to px
     * @param context Application Context
     * @param spValue sp
     * @return px
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources( ).getDisplayMetrics( ).scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
