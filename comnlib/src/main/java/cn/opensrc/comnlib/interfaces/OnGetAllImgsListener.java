package cn.opensrc.comnlib.interfaces;


import java.util.LinkedHashMap;
import java.util.LinkedList;

import cn.opensrc.comnlib.bean.ImgBean;

/**
 * Author:       sharp
 * Created on:   8/11/16 4:38 PM
 * Description:  获取手机中的所有图片回调
 * Revisions:
 */
public interface OnGetAllImgsListener {
    // key:目录 value:目录下所有图片
    void onComplete(LinkedHashMap<String, LinkedList<ImgBean>> dir2Imgs);
}
