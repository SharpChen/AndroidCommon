package cn.opensrc.comnlib.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;

/**
 * Author:       sharp
 * Created on:   8/8/16 1:39 PM
 * Description:  SharedPreferences 缓存操作
 * Revisions:
 */
public final class PreferencesUtils {
    private PreferencesUtils() {
    }

    private static final String SPNAME = "XxxXx";

    /**
     * 获取键对应的值
     *
     * @param context Application Context
     * @param key the key to put
     * @return the value of the key
     */
    public static String getValueFromPreferences(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(SPNAME, Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    /**
     * 单个字段加入缓存
     *
     * @param context Application Context
     * @param key     the key to put
     * @param value   the value to put
     */
    public static void putValue2Preferences(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(SPNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * 多个字段加入缓存
     *
     * @param context Application Context
     * @param data    the data to put
     */
    public static void putMultiValue2Preferences(Context context, Map<String, String> data) {
        if (data == null || data.size() == 0) return;
        SharedPreferences sp = context.getSharedPreferences(SPNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Set<Map.Entry<String, String>> entSet = data.entrySet();
        for (Map.Entry<String, String> en : entSet) {
            editor.putString(en.getKey(), en.getValue());
        }
        editor.apply();
    }

    /**
     * 清空指定键值
     *
     * @param context Application Context
     * @param key     the SharedPreferences key
     */
    public static void clearSpecifyKeyValueOfPreferences(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(SPNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit().remove(key);
        editor.apply();
    }

    /**
     * 批量清空一组键值
     *
     * @param context Application Context
     * @param keys    the SharedPreferences keys
     */
    public static void clearKeyValueBatchOfPreferences(Context context, Set<String> keys) {
        if (keys == null || keys.size() < 1) return;
        for (String key : keys) {
            clearSpecifyKeyValueOfPreferences(context, key);
        }
    }

}
