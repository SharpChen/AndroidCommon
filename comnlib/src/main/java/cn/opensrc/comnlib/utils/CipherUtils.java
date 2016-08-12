package cn.opensrc.comnlib.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

/**
 * Author:       sharp
 * Created on:   8/12/16 10:40 AM
 * Description:  数据加密
 * Revisions:
 */
public final class CipherUtils {
    private CipherUtils() {
    }

    private static final byte[] sKey = {(byte) 0xF4, (byte) 0x88, (byte) 0xFD, (byte) 0x58,
            (byte) 0x4E, (byte) 0x49, (byte) 0xDB, (byte) 0xCD,
            (byte) 0x20, (byte) 0xB4, (byte) 0x9D, (byte) 0xE4,
            (byte) 0x91, (byte) 0x07, (byte) 0x36, (byte) 0x6B,
            (byte) 0x33, (byte) 0x6C, (byte) 0x38, (byte) 0x0D,
            (byte) 0x45, (byte) 0x1D, (byte) 0x0F, (byte) 0x7C,
    };

    /**
     * MD5 32 lower case encrypt
     *
     * @param originStr     the original String
     * @param oriStrCharset charset of original String
     * @return md5 String
     */
    public static String encryptWithMD5(String originStr, String oriStrCharset) {
        String resultString = originStr;
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");

            if (oriStrCharset == null || "".equals(oriStrCharset))
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(oriStrCharset)));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return resultString;
    }

    /**
     * Base64 加密
     *
     * @param oriStr        原始字符串
     * @param oriStrCharset 原始字符串编码方式
     * @return 加密后的base64字符串
     */
    public static String encryptWithBase64(String oriStr, String oriStrCharset) {
        if (oriStr == null || "".equals(oriStr)) return "";
        if (oriStrCharset == null || "".equals(oriStrCharset)) oriStrCharset = "UTF-8";

        String base64Str = "";
        try {
            byte[] encode = oriStr.getBytes(oriStrCharset);
            base64Str = new String(Base64.encode(encode, 0, encode.length, Base64.DEFAULT), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return base64Str;
    }

    /**
     * Base64 解密
     *
     * @param base64Str base64 字符串
     * @return 原始字符串
     */
    public static String decryptBase64(String base64Str) {
        if (base64Str == null || "".equals(base64Str)) return "";
        String oriStr = "";
        try {
            byte[] encode = base64Str.getBytes("UTF-8");
            oriStr = new String(Base64.decode(encode, 0, encode.length, Base64.DEFAULT), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return oriStr;
    }

    /**
     * 3Des 加密
     *
     * @param oriStr 原始字符串
     * @return 加密后的字符串
     */
    public static String encryptWith3Des(String oriStr) {

        if (oriStr == null || "".equals(oriStr)) return "";


        String encryptStr = "";

        try {
            byte[] pass = oriStr.getBytes("UTF-8");
            int tail = pass.length % 8;
            int block = pass.length / 8 + 1;
            byte fill = (byte) (8 - tail);
            byte[] fillPass = new byte[block * 8];
            System.arraycopy(pass, 0, fillPass, 0, pass.length);
            for (int i = 0; i < 8 - tail; i++)
                fillPass[pass.length + i] = fill;
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            DESedeKeySpec desKeySpec = new DESedeKeySpec(sKey);
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] result = new byte[block * 8];
            for (int i = 0; i < block; i++) {
                byte[] tmp = cipher.doFinal(fillPass, i * 8, 8);
                System.arraycopy(tmp, 0, result, i * 8, 8);
            }
            encryptStr = byteArrayToHexString(result);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

        return encryptStr;

    }

    /**
     * 3Des 解密
     *
     * @param str3Des 3Des 字符串
     * @return 原始字符串
     */
    public static String decrypt3Des(String str3Des) {
        if (str3Des == null || "".equals(str3Des)) return "";

        String ori = "";
        try {
            byte[] pass = str2ByteArray(str3Des);
            int block = pass.length / 8;

            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            DESedeKeySpec desKeySpec = new DESedeKeySpec(sKey);
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] result = new byte[block * 8];
            for (int i = 0; i < block; i++) {
                byte[] tmp = cipher.doFinal(pass, i * 8, pass.length - i * 8);
                System.arraycopy(tmp, 0, result, i * 8, tmp.length);
            }
            int realLen = result.length;
            for (int i = 0; i < result.length; i++) {
                if (result[i] == 0) {
                    realLen = i;
                    break;
                }
            }
            ori = new String(result, 0, realLen, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

        return ori;
    }

    /**
     * 字节数组转换为十六进制字符串
     *
     * @param oriByteArray 原始字节数组
     * @return 十六进制字符串
     */
    private static String byteArrayToHexString(byte oriByteArray[]) {
        StringBuilder resultSb = new StringBuilder();
        for (byte aB : oriByteArray) resultSb.append(byteToHexString(aB));
        return resultSb.toString();
    }

    /**
     * 字节转换为十六进制字符串
     *
     * @param oriByte 原始字节
     * @return 十六进制字符串
     */
    private static String byteToHexString(byte oriByte) {
        String hexDigits[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
        int n = oriByte;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * 十六进制字符串转换为字节数组
     * @param hexStr 十六进制字符串
     * @return 字节数组
     */
    private static byte[] str2ByteArray(String hexStr) {
        int len = hexStr.length() / 2;
        byte[] arr = new byte[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (byte) Integer.parseInt(hexStr.substring(i * 2, i * 2 + 2), 16);
        }
        return arr;
    }
    
}
