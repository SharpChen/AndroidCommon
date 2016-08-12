package net.qiujuer.genius.blur;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import cn.opensrc.comnlib.interfaces.OnBitmapBlurListener;


public class StackBlur extends StackNative {

    /**
     * StackBlur By Jni Bitmap
     *
     * @param oriBitmap Original Image
     * @param radius    Blur radius
     * @param listener  blur process listener
     */
    public static void blurNatively(Bitmap oriBitmap, int radius, OnBitmapBlurListener listener) {
        new BlurAsyncTask(radius, BlurAsyncTask.BLUR_NATIVE, listener).execute(oriBitmap);
    }

    /**
     * StackBlur By Jni Pixels
     *
     * @param oriBitmap Original Image
     * @param radius    Blur radius
     * @param listener  blur process listener
     */
    public static void blurNativelyPixels(Bitmap oriBitmap, int radius, OnBitmapBlurListener listener) {

        new BlurAsyncTask(radius, BlurAsyncTask.BLUR_NATIVE_PIXEl, listener).execute(oriBitmap);

    }

    /**
     * StackBlur By Java Bitmap
     *
     * @param oriBitmap Original Image
     * @param radius    Blur radius
     * @param listener  blur process listener
     */
    public static void blurJava(Bitmap oriBitmap, int radius, OnBitmapBlurListener listener) {
        new BlurAsyncTask(radius, BlurAsyncTask.BLUR_JAVA, listener).execute(oriBitmap);
    }

    /**
     * StackBlur By Java Bitmap（内部使用）
     *
     * @param oriBitmap Original Image
     * @param radius Blur radius
     * @return blured bitmap
     */
    private static Bitmap blurWithJavaMethod(Bitmap oriBitmap, int radius) {

        int w = oriBitmap.getWidth();
        int h = oriBitmap.getHeight();

        int[] pix = new int[w * h];
        // get array
        oriBitmap.getPixels(pix, 0, w, 0, 0, w, h);

        // run Blur
        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rSum, gSum, bSum, x, y, i, p, yp, yi, yw;
        int vMin[] = new int[Math.max(w, h)];

        int divSum = (div + 1) >> 1;
        divSum *= divSum;
        int dv[] = new int[256 * divSum];
        for (i = 0; i < 256 * divSum; i++) {
            dv[i] = (i / divSum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackPointer;
        int stackStart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routSum, goutSum, boutSum;
        int rinSum, ginSum, binSum;

        for (y = 0; y < h; y++) {
            rinSum = ginSum = binSum = routSum = goutSum = boutSum = rSum = gSum = bSum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rSum += sir[0] * rbs;
                gSum += sir[1] * rbs;
                bSum += sir[2] * rbs;
                if (i > 0) {
                    rinSum += sir[0];
                    ginSum += sir[1];
                    binSum += sir[2];
                } else {
                    routSum += sir[0];
                    goutSum += sir[1];
                    boutSum += sir[2];
                }
            }
            stackPointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rSum];
                g[yi] = dv[gSum];
                b[yi] = dv[bSum];

                rSum -= routSum;
                gSum -= goutSum;
                bSum -= boutSum;

                stackStart = stackPointer - radius + div;
                sir = stack[stackStart % div];

                routSum -= sir[0];
                goutSum -= sir[1];
                boutSum -= sir[2];

                if (y == 0) {
                    vMin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vMin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinSum += sir[0];
                ginSum += sir[1];
                binSum += sir[2];

                rSum += rinSum;
                gSum += ginSum;
                bSum += binSum;

                stackPointer = (stackPointer + 1) % div;
                sir = stack[(stackPointer) % div];

                routSum += sir[0];
                goutSum += sir[1];
                boutSum += sir[2];

                rinSum -= sir[0];
                ginSum -= sir[1];
                binSum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinSum = ginSum = binSum = routSum = goutSum = boutSum = rSum = gSum = bSum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rSum += r[yi] * rbs;
                gSum += g[yi] * rbs;
                bSum += b[yi] * rbs;

                if (i > 0) {
                    rinSum += sir[0];
                    ginSum += sir[1];
                    binSum += sir[2];
                } else {
                    routSum += sir[0];
                    goutSum += sir[1];
                    boutSum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackPointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rSum] << 16) | (dv[gSum] << 8) | dv[bSum];

                rSum -= routSum;
                gSum -= goutSum;
                bSum -= boutSum;

                stackStart = stackPointer - radius + div;
                sir = stack[stackStart % div];

                routSum -= sir[0];
                goutSum -= sir[1];
                boutSum -= sir[2];

                if (x == 0) {
                    vMin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vMin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinSum += sir[0];
                ginSum += sir[1];
                binSum += sir[2];

                rSum += rinSum;
                gSum += ginSum;
                bSum += binSum;

                stackPointer = (stackPointer + 1) % div;
                sir = stack[stackPointer];

                routSum += sir[0];
                goutSum += sir[1];
                boutSum += sir[2];

                rinSum -= sir[0];
                ginSum -= sir[1];
                binSum -= sir[2];

                yi += w;
            }
        }

        // set Bitmap
        oriBitmap.setPixels(pix, 0, w, 0, 0, w, h);

        return oriBitmap;
    }

    /**
     * 异步模糊
     */
    private static class BlurAsyncTask extends AsyncTask<Bitmap, Integer, Bitmap> {

        public static final int BLUR_NATIVE = 1;
        public static final int BLUR_NATIVE_PIXEl = 2;
        public static final int BLUR_JAVA = 3;
        private int radius;
        private int blurType;
        private OnBitmapBlurListener listener;

        public BlurAsyncTask(int radius, int blurType, OnBitmapBlurListener listener) {
            this.radius = radius;
            this.blurType = blurType;
            this.listener = listener;
        }

        @Override
        protected Bitmap doInBackground(Bitmap... params) {

            Bitmap oriBitmap = params[0];

            if (radius <= 1) {
                return oriBitmap;
            }

            switch (blurType) {

                case BLUR_NATIVE_PIXEl:
                    oriBitmap = oriBitmap.copy(oriBitmap.getConfig(), true);
                    int w = oriBitmap.getWidth();
                    int h = oriBitmap.getHeight();

                    int[] pix = new int[w * h];
                    oriBitmap.getPixels(pix, 0, w, 0, 0, w, h);

                    // Jni Pixels Blur
                    blurPixels(pix, w, h, radius);

                    oriBitmap.setPixels(pix, 0, w, 0, 0, w, h);
                    break;
                case BLUR_NATIVE:
                    // Jni Blur
                    blurBitmap(oriBitmap, radius);
                    break;
                case BLUR_JAVA:
                    // Java Blur
                    oriBitmap = oriBitmap.copy(oriBitmap.getConfig(), true);
                    blurWithJavaMethod(oriBitmap, radius);
                    break;
                default:
                    break;

            }

            return oriBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            listener.onComplete(bitmap);
        }
    }

}
