package cn.opensrc.comnlib.utils;

import java.text.DecimalFormat;

/**
 * Author:       sharp
 * Created on:   8/2/16 3:33 PM
 * Description:  字符串操作
 * Revisions:
 */
public final class StringUtils {
    private StringUtils() {
    }

    /**
     * 判断字符串是否为空
     *
     * @param str String
     * @return is null of emplty of the str
     */
    public static boolean isNullOrEmpty(String str) {

        return str == null || "".equals(str);

    }

    /**
     * 去除字符串中的换行、空格等特殊字符
     *
     * @param str String
     * @return the String that not include special char
     */
    public static String removeSpecialChar(String str) {
        if (str == null) return "";
        String backStr;
        backStr = str.replaceAll("\r", "");
        backStr = backStr.replaceAll("\n", "");
        backStr = backStr.replaceAll(" ", "");
        backStr = backStr.trim();
        return backStr;
    }

    /**
     * 将 null 转换为空字符串
     *
     * @param str String
     * @return not null String
     */
    public static String getNonNullString(String str) {

        if (str == null)
            str = "";
        return str;

    }

    /**
     * Double 类型保留两位小数
     *
     * @param oriDecimal the original decimal
     * @return two places decimal
     */
    public static String getDecimalWithTwo(double oriDecimal) {

        if (oriDecimal == 0) return "0";

        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(oriDecimal);

    }

    /**
     * 遮盖手机号码中间位数
     *
     * @param oriPhoneNumber the original phonenumber
     * @return the phone that masked
     */
    public static String getMaskPhoneNumber(String oriPhoneNumber) {

        if (oriPhoneNumber == null || "".equals(oriPhoneNumber) || oriPhoneNumber.length() < 11)
            return "";
        return new StringBuilder(oriPhoneNumber.substring(0, 4)).append("****").append(oriPhoneNumber.substring(7, 11)).toString();

    }

}
