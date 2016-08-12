package cn.opensrc.comnlib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.webkit.WebView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Author:       sharp
 * Created on:   8/3/16 2:39 PM
 * Description:  项目资源操作
 * Revisions:
 */
public final class ResUtils {
    private ResUtils() {
    }

    /**
     * 获取项目中的图片
     *
     * @param context Application Context
     * @param id      the image id
     * @return the Drawable object of image
     */
    public static Drawable getDrawableById(Context context, int id) {

        return ContextCompat.getDrawable(context, id);

    }

    /**
     * 获取项目中的图片
     *
     * @param context Application Context
     * @param id      the image id
     * @return the Bitmap object of image
     */
    public static Bitmap getBitmapById(Context context, int id) {
        return BitmapFactory.decodeResource(context.getResources(), id);
    }

    /**
     * 获取字符串
     *
     * @param context Application Context
     * @param id      the text id
     * @return text
     */
    public static String getStringById(Context context, int id) {

        return context.getResources().getString(id);

    }

    /**
     * 获取颜色
     *
     * @param context Application Context
     * @param id      the color id
     * @return the color value
     */
    public static int getColorById(Context context, int id) {
        return ContextCompat.getColor(context, id);
    }

    /**
     * 从 res/raw 目录下读取文本
     * Arbitrary files to save in their raw form.
     * To open these resources with a raw InputStream, call Resources.openRawResource() with the resource ID,
     * which is R.raw.filename.
     * <p/>
     * However, if you need access to original file names and file hierarchy,
     * you might consider saving some resources in the assets/ directory (instead of res/raw/).
     * Files in assets/ are not given a resource ID, so you can read them only using AssetManager.
     *
     * @param context Application Context
     * @param id      the raw file id
     * @return read result
     */
    public static String readTxtFromRaw(Context context, int id) {

        return readTxtFromInputStream(context.getResources().openRawResource(id));

    }

    /**
     * 从 assets 目录下读取文本
     *
     * @param context  Application Context
     * @param fileName only the file name
     * @return file text content
     */
    public static String readTxtFromAssets(Context context, String fileName) {

        if (fileName == null || "".equals(fileName)) return "";

        String rst = "";

        try {
            rst = readTxtFromInputStream(context.getAssets().open(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rst;

    }

    /**
     * 从输入流中读取文本
     *
     * @param in InputStream
     * @return the text of inputstream
     */
    public static String readTxtFromInputStream(InputStream in) {

        if (in == null) return "";

        InputStreamReader reader = null;
        BufferedReader bufferedReader = null;

        StringBuilder sb = new StringBuilder("");
        String str;
        try {
            reader = new InputStreamReader(in);
            bufferedReader = new BufferedReader(reader);
            while ((str = bufferedReader.readLine()) != null) {
                sb.append(str);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                if (reader != null)
                    reader.close();
                if (bufferedReader != null)
                    bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();

    }

    /**
     * 获取文本文件编码
     *
     * @param is the text file InputStream
     * @return the text file code
     */
    public static String getTxtFileCode(InputStream is) {

        String code = "UTF-8";
        try {

            BufferedInputStream bin = new BufferedInputStream(is);
            int p = (bin.read() << 8) + bin.read();

            switch (p) {
                case 0xefbb:
                    code = "UTF-8";
                    break;
                case 0xfffe:
                    code = "Unicode";
                    break;
                case 0xfeff:
                    code = "UTF-16BE";
                    break;
                default:
                    code = "GBK";
                    break;
            }

            return code;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return code;

    }

    /**
     * 加载 assets 目录下的 html
     *
     * @param wv   WebView
     * @param relativePath html file path
     */
    public static void loadHtmlFromAssets(WebView wv, String relativePath) {
        if (wv == null || relativePath == null || "".equals(relativePath)) return;
        wv.loadUrl("file:///android_asset/" + relativePath);
    }

}
