package net.bither.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.AsyncTask;

import cn.opensrc.comnlib.constants.DirConstants;
import cn.opensrc.comnlib.interfaces.OnImgCompressListener;
import cn.opensrc.comnlib.utils.FileUtils;


public class NativeUtil {

    /**
     * load so
     */
    static {

        System.loadLibrary("jpegbither");
        System.loadLibrary("bitherjni");

    }

    /**
     * 压缩 Bitmap 图片（重载方法）.默认压缩比例50%,optimize为true,默认图片压缩后保存路径
     *
     * @param bitmap   需要压缩的 Bitmap 图片
     * @param listener 压缩状态监听器
     */
    public static void compressBitmap(Bitmap bitmap, OnImgCompressListener listener) {

        if (bitmap == null)
            throw new RuntimeException("The bitmap object is null in NativeUtil");

        // 创建本地压缩图片保存目录
        FileUtils.makeDir(DirConstants.DIR_COMPRESS);

        String compressedImgSavedPath = DirConstants.DIR_COMPRESS + "compressed_" + System.currentTimeMillis();
        compressBitmap(bitmap, 50, compressedImgSavedPath, true, listener);

    }

    /**
     * 压缩 Bitmap 图片
     *
     * @param bitmap          需要压缩的 Bitmap 图片
     * @param compressQuality 压缩百分比
     * @param compressToPath  压缩后的图片保存路径
     * @param optimize        miss
     * @param listener        压缩状态监听器
     */
    public static void compressBitmap(Bitmap bitmap,
                                      final int compressQuality,
                                      final String compressToPath,
                                      final boolean optimize,
                                      final OnImgCompressListener listener) {

        final Bitmap bitmapCopy;
        final int w = bitmap.getWidth();
        final int h = bitmap.getHeight();
        bitmapCopy = Bitmap.createBitmap(w, h, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCopy);
        Rect rect = new Rect(0, 0, w, h);
        canvas.drawBitmap(bitmap, null, rect, null);

        new CompressAsyncTask(bitmapCopy,w,h,compressQuality,compressToPath,optimize,listener).execute();

    }

    /**
     * 压缩图片异步AsyncTask
     */
     private static class CompressAsyncTask extends AsyncTask<String,Integer,String>{

        private Bitmap bitmap;
        private int bitmapWidth;
        private int bitmapHeight;
        private int compressQuality;
        private String compressToPath;
        private boolean optimize;
        private OnImgCompressListener onImgCompressListener;

        public CompressAsyncTask(Bitmap bitmap,
                                 int bitmapWidth,
                                 int bitmapHeight,
                                 int compressQuality,
                                 String compressToPath,
                                 boolean optimize,
                                 OnImgCompressListener onImgCompressListener) {
            this.bitmap = bitmap;
            this.bitmapWidth = bitmapWidth;
            this.bitmapHeight = bitmapHeight;
            this.compressQuality = compressQuality;
            this.compressToPath = compressToPath;
            this.optimize = optimize;
            this.onImgCompressListener = onImgCompressListener;
        }

        @Override
        protected String doInBackground(String... params) {
            return compressBitmap(bitmap, bitmapWidth, bitmapHeight, compressQuality, compressToPath.getBytes(), optimize);
        }

        @Override
        protected void onPostExecute(String s) {
            if ("1".equals(s))
                onImgCompressListener.onComplete(compressToPath);
            else
                onImgCompressListener.onComplete("");

            if (!bitmap.isRecycled())
                bitmap.recycle();

        }

    }

    /**
     * JNI 压缩图片方法
     *
     * @return 压缩状态,1 成功
     */
    public static native String compressBitmap(Bitmap bit,
                                                int w,
                                                int h,
                                                int quality,
                                                byte[] fileNameBytes,
                                                boolean optimize);


}
