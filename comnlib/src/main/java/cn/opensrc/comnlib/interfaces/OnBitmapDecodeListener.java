package cn.opensrc.comnlib.interfaces;

import android.graphics.Bitmap;

/**
 * Author:       sharp
 * Created on:   8/11/16 10:52 AM
 * Description:  获取手机中的 Bitmap 图片回调
 * Revisions:
 */
public interface OnBitmapDecodeListener {
    void onComplete(Bitmap bitmap);
}
