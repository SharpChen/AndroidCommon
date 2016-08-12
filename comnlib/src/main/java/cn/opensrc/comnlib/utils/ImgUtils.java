package cn.opensrc.comnlib.utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Base64;

import net.bither.util.NativeUtil;
import net.qiujuer.genius.blur.StackBlur;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import cn.opensrc.comnlib.bean.ImgBean;
import cn.opensrc.comnlib.interfaces.OnBitmapBlurListener;
import cn.opensrc.comnlib.interfaces.OnBitmapDecodeListener;
import cn.opensrc.comnlib.interfaces.OnGetAllImgsListener;
import cn.opensrc.comnlib.interfaces.OnImgCompressListener;
import cn.opensrc.comnlib.interfaces.OnSaveBitmap2PhoneListener;

/**
 * Author:       sharp
 * Created on:   8/6/16 9:42 AM
 * Description:  图片处理
 * Revisions:
 */
public final class ImgUtils {
    private ImgUtils() {
    }

    /**
     * 图片文件转Base64字符串
     *
     * @param imgFile 图片文件
     * @return Base64字符串
     */
    public static String img2Base64(File imgFile) {
        if (imgFile == null) return "";
        try {
            FileInputStream imgFileInputStream = new FileInputStream(imgFile);
            byte[] buffer = new byte[(int) imgFile.length()];
            imgFileInputStream.read(buffer);
            imgFileInputStream.close();
            return Base64.encodeToString(buffer, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 根据图片在手机中的绝对路径获取 Bitmap
     *
     * @param absPath  图片绝对路径
     * @param listener 图片加载监听器
     */
    public static void getBitmapFromPhone(String absPath, OnBitmapDecodeListener listener) {

        if (absPath == null || "".equals(absPath)) return;

        new DecodeBitmapByPathAsyncTask(listener).execute(absPath);

    }

    /**
     * 根据图片id获取 Bitmap
     *
     * @param context Application Context
     * @param id      图片id
     * @return Bitmap Object of the image
     */
    public static Bitmap getBitmapFromRes(Context context, int id) {

        return BitmapFactory.decodeResource(context.getResources(), id);

    }

    /**
     * 将下载的图片暴露到媒体库
     *
     * @param context Application Context
     * @param imgPath the imagePath
     */
    public static void exposeInMedia(Context context, String imgPath) {

        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(new File(imgPath));
        intent.setData(uri);
        context.sendBroadcast(intent);

    }

    /**
     * 保存 Bitmap 到手机
     *
     * @param bmp      保存的图片
     * @param path     保存的路径
     * @param name     保存的文件名
     * @param listener 保存监听器
     */
    public static void saveBitmap2Phone(Bitmap bmp, String path, String name, OnSaveBitmap2PhoneListener listener) {

        if (bmp == null
                || path == null
                || "".equals(path)
                || name == null
                || "".equals(name))
            throw new RuntimeException("save bitmap error in saveBitmap2Phone method!");

        FileUtils.makeDir(path);
        new SaveBitmap2PhoneAsyncTask(bmp, listener).execute(path, name);

    }

    /**
     * 根据 BitmapFactory.Options 和要求的图片宽高计算出图片的缩放比例
     *
     * @param options   原始图片 BitmapFactory.Options
     * @param reqWidth  要求的宽
     * @param reqHeight 要求的高
     * @return 缩放比例
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        // Raw height and width of image
        final int width = options.outWidth;
        final int height = options.outHeight;

        return calculateInSampleSize(width, height, reqWidth, reqHeight);

    }

    /**
     * 根据原始图片宽高和要求的图片的宽高计算出缩放比例
     *
     * @param oriBitmapWidth  原始图片宽
     * @param oriBitmapHeight 原始图片高
     * @param reqWidth        要求的宽
     * @param reqHeight       要求的高
     * @return 缩放比例
     */
    public static int calculateInSampleSize(int oriBitmapWidth, int oriBitmapHeight, int reqWidth, int reqHeight) {

        int inSampleSize = 1;

        if (oriBitmapHeight > reqHeight || oriBitmapWidth > reqWidth) {

            final int halfHeight = oriBitmapHeight / 2;
            final int halfWidth = oriBitmapWidth / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth)
                inSampleSize *= 2;

        }

        return inSampleSize;

    }

    /**
     * 等比例缩放图片文件
     *
     * @param oriImgFilePath 原始图片路径
     * @param reqWidth       需要缩放到的最终宽
     * @param reqHeight      需要缩放到的最终高
     * @return 缩放后的 Bitmap 对象
     */
    public static Bitmap zoomImgFile(String oriImgFilePath, int reqWidth, int reqHeight) {

        if (oriImgFilePath == null || "".equals(oriImgFilePath)) return null;

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(oriImgFilePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(oriImgFilePath, options);

    }

    /**
     * 等比例缩放Bitmap
     *
     * @param oriBmp    原始图片
     * @param reqWidth  需要缩放到的最终宽
     * @param reqHeight 需要缩放到的最终高
     * @return 缩放后的 Bitmap 对象
     */
    public static Bitmap zoomBitmap(Bitmap oriBmp, int reqWidth, int reqHeight) {

        if (oriBmp == null) return Bitmap.createBitmap(0, 0, Bitmap.Config.RGB_565);

        int oriBitmapWidth = oriBmp.getWidth();
        int oriBitmapHeight = oriBmp.getHeight();
        int inSampleSize = calculateInSampleSize(oriBitmapWidth, oriBitmapHeight, reqWidth, reqHeight);
        Matrix bmpScaleMatrix = new Matrix();
        bmpScaleMatrix.postScale(inSampleSize, inSampleSize);

        return Bitmap.createBitmap(oriBmp, 0, 0, oriBitmapWidth, oriBitmapHeight, bmpScaleMatrix, true);

    }

    /**
     * 压缩 Bitmap 图片（重载方法）.
     * 默认压缩比例50%,optimize为true,默认图片压缩后保存路径.
     * 图片大小 7.5M 压缩至 427KB,用时1s
     *
     * @param bitmap   需要压缩的 Bitmap 图片
     * @param listener 压缩状态监听器
     */
    public static void compressBitmapWithJni(Bitmap bitmap, OnImgCompressListener listener) {
        NativeUtil.compressBitmap(bitmap, listener);
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
    public static void compressBitmapWithJni(Bitmap bitmap,
                                             final int compressQuality,
                                             final String compressToPath,
                                             final boolean optimize,
                                             final OnImgCompressListener listener) {

        NativeUtil.compressBitmap(bitmap, compressQuality, compressToPath, optimize, listener);
    }

    /**
     * 调用 JNI 方法直接模糊 Bitmap 图片（图片大小7.5M,用时10s,占用内存20M左右）
     * 直接在原图上进行模糊,不保留原图.
     *
     * @param oriBitmap Original Image
     * @param radius    Blur radius
     * @param listener  blur process listener
     */
    public static void blurBitmapWithJni(Bitmap oriBitmap,
                                         int radius,
                                         OnBitmapBlurListener listener) {
        StackBlur.blurNatively(oriBitmap, radius, listener);
    }

    /**
     * 调用 JNI 方法模糊 Bitmap 图片像素（图片大小7.5M,用时10s,占用内存57M左右）
     * 保留原图
     *
     * @param oriBitmap Original Image
     * @param radius    Blur radius
     * @param listener  blur process listener
     */
    public static void blurBitmapPixelWithJni(Bitmap oriBitmap,
                                              int radius,
                                              OnBitmapBlurListener listener) {
        StackBlur.blurNativelyPixels(oriBitmap, radius, listener);
    }

    /**
     * 调用 Java 方法模糊 Bitmap 图片（图片大小7.5M,用时12s,占用内存117M左右）
     * 保留原图
     *
     * @param oriBitmap Original Image
     * @param radius    Blur radius
     * @param listener  blur process listener
     */
    public static void blurBitmapWithJava(Bitmap oriBitmap,
                                          int radius,
                                          OnBitmapBlurListener listener) {
        StackBlur.blurJava(oriBitmap, radius, listener);
    }

    /**
     * 获取手机中的全部图片（以每张图片所在目录进行分类）
     *
     * @param context  Application Context
     * @param listener 获取完后回调
     */
    public static void getAllImgsFromPhone(Context context, OnGetAllImgsListener listener) {
        new GetAllImgsAsyncTask(context, listener).execute();
    }

    /**
     * 异步解析手机中的单张Bitmap图片
     */
    private static class DecodeBitmapByPathAsyncTask extends AsyncTask<String, Integer, Bitmap> {

        private OnBitmapDecodeListener onBitmapDecodeListener;

        public DecodeBitmapByPathAsyncTask(OnBitmapDecodeListener onBitmapDecodeListener) {
            this.onBitmapDecodeListener = onBitmapDecodeListener;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String filePath = params[0];
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            return BitmapFactory.decodeFile(filePath, options);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            onBitmapDecodeListener.onComplete(bitmap);
        }
    }

    /**
     * 异步获取手机中的所有图片
     */
    private static class GetAllImgsAsyncTask extends AsyncTask<String, Integer, Cursor> {

        private Context context;
        private OnGetAllImgsListener listener;
        private LinkedHashMap<String, LinkedList<ImgBean>> dir2Imgs;

        public GetAllImgsAsyncTask(Context context, OnGetAllImgsListener listener) {
            this.context = context;
            this.listener = listener;
            this.dir2Imgs = new LinkedHashMap<>();
        }

        @Override
        protected Cursor doInBackground(String... params) {

            Cursor imgCursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    null,
                    MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",
                    new String[]{"image/jpeg", "image/png"},
                    MediaStore.Images.Media.DATE_ADDED + " DESC");

            if (imgCursor == null)
                throw new RuntimeException("get phone all pictures Cursor is null in GetAllImgsAsyncTask class");

            while (imgCursor.moveToNext()) {

                // 图片路径
                String sImgPath = imgCursor.getString(imgCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                File imgFile = new File(sImgPath);
                if (imgFile.isDirectory() || (!imgFile.exists())) continue;

                // 图片所在目录名
                String sImgDirName = imgCursor.getString(imgCursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));

                ImgBean imgBean = new ImgBean();
                imgBean.setImgPath(sImgPath);
                imgBean.setImgParentDirName(sImgDirName);
                imgBean.setSelected(false);

                if (dir2Imgs.containsKey(sImgDirName)) {
                    dir2Imgs.get(sImgDirName).add(imgBean);
                } else {
                    LinkedList<ImgBean> newImgsList = new LinkedList<>();
                    newImgsList.add(imgBean);
                    dir2Imgs.put(sImgDirName, newImgsList);
                }

            }

            return imgCursor;
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null) cursor.close();
            listener.onComplete(dir2Imgs);
        }

    }

    /**
     * 异步保存 Bitmap 图片到本地
     */
    private static class SaveBitmap2PhoneAsyncTask extends AsyncTask<String, Integer, String> {

        private Bitmap bitmap;
        private OnSaveBitmap2PhoneListener listener;

        public SaveBitmap2PhoneAsyncTask(Bitmap bitmap, OnSaveBitmap2PhoneListener listener) {
            this.bitmap = bitmap;
            this.listener = listener;
        }

        @Override
        protected String doInBackground(String... params) {
            String savePath = params[0];
            String saveName = params[1];
            File imgFile = new File(savePath);
            FileUtils.makeDir(imgFile);
            File savedImgFile = new File(savePath, saveName);
            FileOutputStream bmpOutputStream = null;
            try {
                bmpOutputStream = new FileOutputStream(savedImgFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bmpOutputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (bmpOutputStream != null) {
                    try {
                        bmpOutputStream.flush();
                        bmpOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return savedImgFile.getAbsolutePath();
        }

        @Override
        protected void onPostExecute(String s) {
            listener.onSuccess(s);
        }

    }

}
