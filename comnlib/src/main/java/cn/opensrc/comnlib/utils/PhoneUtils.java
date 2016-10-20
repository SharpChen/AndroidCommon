package cn.opensrc.comnlib.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Author:       sharp
 * Created on:   8/7/16 4:09PM
 * Description:  手机操作
 * Revisions:
 */
public final class PhoneUtils {
    private PhoneUtils(){}

    /**
     * 获取手机系统API版本
     * @return the ApiVersion of current phone,eg:API 14
     */
    public static int getPhoneAPIVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取手机系统版本
     * @return the system version.eg:5.0.2
     */
    public static String getPhoneSystemVersion(){
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机厂商和型号
     * @return Manufacturer and model. eg: HUAWEI PLK-AL10
     */
    public static String getPhoneManufacturerAndModel(){
        return Build.MANUFACTURER + " " +Build.MODEL;
    }

    /**
     * 获取CPU型号
     *
     * @return the cpu model
     */
    @NonNull
    public static String getCpuName() {
        String name = getCpuName1();
        if (TextUtils.isEmpty(name)) {
            name = getCpuName2();
            if (TextUtils.isEmpty(name)) {
                name = "unknown";
            }
        }
        return name;
    }

    /**
     * 方式1 获取 CPU型号
     * @return cpu model
     */
    private static String getCpuName1() {
        String[] abiArr;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            abiArr = Build.SUPPORTED_ABIS;
        } else {
            abiArr = new String[]{Build.CPU_ABI, Build.CPU_ABI2};
        }

        StringBuilder abiStr = new StringBuilder();
        for (String abi : abiArr) {
            abiStr.append(abi);
            abiStr.append(',');
        }
        return abiStr.toString();
    }

    /**
     * 方式2获取 CPU型号
     * @return cpu model
     */
    private static String getCpuName2() {
        try {
            FileReader e = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(e);
            String text = br.readLine();
            String[] array = text.split(":\\s+", 2);
            e.close();
            br.close();
            return array[1];
        } catch (IOException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    /**
     * 键盘是否弹出
     * @param context Application Context
     * @return Keyboard is showing
     */
    public static boolean isKeyboardShowing(Context context){

        if (context==null)return false;
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm==null)return false;
        return imm.isActive();

    }
    /**
     * 弹出键盘
     * @param context Application Context
     * @param editText the current EditText
     */
    public static void showKeyboard(Context context,EditText editText){

        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);

    }
    /**
     * 隐藏键盘
     * @param context Application Context
     */
    public static void hideKeyboard(Context context){

        if (!(context instanceof Activity)) return;

        Activity activity = (Activity) context;

        View v = activity.getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }

        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    /**
     * 获取手机屏幕宽
     * @param context Application Context
     * @return the phone width in px
     */
    public static int getPhoneWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    /**
     * 获取手机屏幕高
     * @param context Application Context
     * @return the phone height in px
     */
    public static int getPhoneHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

}
