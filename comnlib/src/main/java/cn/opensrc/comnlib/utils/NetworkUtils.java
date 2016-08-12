package cn.opensrc.comnlib.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Author:       sharp
 * Created on:   8/5/16 4:21 PM
 * Description:  网络环境判断
 * Revisions:
 */
public final class NetworkUtils {
    private NetworkUtils(){}

    /**
     * 判断手机当前网络是否可用
     * @param context Application Context
     * @return is network availabe
     */
    public static boolean isNetworkAvailable(Context context){
        if (context==null) return false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm==null) return false;
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni==null) return false;
        return ni.isAvailable();
    }

    /**
     * 判断手机是否是在wifi环境下
     * @param context Application Context
     * @return network is under wifi
     */
    public static boolean isNetworkUnderWifi(Context context){
        if (context==null) return false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm==null) return false;
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni==null) return false;
        return ConnectivityManager.TYPE_WIFI == ni.getType();
    }

}
