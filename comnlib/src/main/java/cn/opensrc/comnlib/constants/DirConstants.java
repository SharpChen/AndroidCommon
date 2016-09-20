package cn.opensrc.comnlib.constants;

import android.os.Environment;

/**
 * Author:       sharp
 * Created on:   8/10/16 2:46 PM
 * Description:  手机端缓存目录结构
 * Revisions:
 */
public class DirConstants {

    // 根目录
    public static final String DIR_ROOT      = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CacheRoot/";

    // 二级目录
    public static final String DIR_AUDIO     = DIR_ROOT + "Audio/";
    public static final String DIR_DOWNLOAD  = DIR_ROOT + "Download/";
    public static final String DIR_IMAGE     = DIR_ROOT + "TempImg/";
    public static final String DIR_TEXT      = DIR_ROOT + "Text/";
    public static final String DIR_VIDEO     = DIR_ROOT + "Video/";

    // 三级目录
    public static final String DIR_CAPTURE   = DIR_IMAGE + "Capture/";
    public static final String DIR_COMPRESS  = DIR_IMAGE + "Compress/";
    public static final String DIR_CROP      = DIR_IMAGE + "Crop/";
    public static final String DIR_ZOOM      = DIR_IMAGE + "Zoom/";
    public static final String DIR_DLD_AUDIO = DIR_DOWNLOAD + "Audio/";
    public static final String DIR_DLD_IMAGE = DIR_DOWNLOAD + "Image/";
    public static final String DIR_DLD_VIDEO = DIR_DOWNLOAD + "Video/";

}
