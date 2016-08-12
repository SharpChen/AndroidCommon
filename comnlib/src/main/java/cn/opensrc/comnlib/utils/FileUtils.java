package cn.opensrc.comnlib.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Author:       sharp
 * Created on:   8/11/16 2:24 PM
 * Description:  手机中文件操作
 * Revisions:
 */
public final class FileUtils {
    private FileUtils(){}

    /**
     * 创建目录
     *
     * @param dirPath the directory path
     */
    public static void makeDir(String dirPath) {

        if (dirPath == null || "".equals(dirPath)) return;

        makeDir(new File(dirPath));
    }

    /**
     * 创建目录
     *
     * @param dirFile the directory file
     */
    public static void makeDir(File dirFile) {

        if (dirFile.exists()) return;

        dirFile.mkdirs();

    }

    /**
     * 获取指定文件夹下所有文件大小
     *
     * @return the directory size in KB
     */
    public static double getSpecifyDirSize(String dirPath) {

        double dirSize = 0;

        if (dirPath == null || "".equals(dirPath)) return dirSize;

        File file = new File(dirPath);

        if (!file.exists()) {
            boolean mkRst = file.mkdirs();
            if (mkRst)
                return dirSize;
        }

        if (file.isDirectory()) {

            File[] subFile = file.listFiles();
            for (File f : subFile) {

                if (!f.isDirectory()) {

                    dirSize += getFileSize(f.getAbsolutePath());

                } else {

                    dirSize += getSpecifyDirSize(f.getAbsolutePath());

                }

            }

        }

        return dirSize;

    }

    /**
     * 获取单个文件的大小
     * @param filePath the file path
     * @return the file size in kb
     */
    public static long getFileSize(String filePath) {

        long size = 0;
        if (filePath == null || "".equals(filePath)) return size;

        File oriFile = new File(filePath);

        if (!oriFile.exists()) return size;

        return oriFile.length() / 1024;

    }

    /**
     * 删除指定文件
     * @param filePath the delete filepath
     */
    public static void delSpecifyFile(String filePath) {

        if (filePath == null || "".equals(filePath)) return;

        File file = new File(filePath);

        if (file.exists() && file.isFile()) {
            file.delete();

        }

    }

    /**
     * 删除指定文件夹下所有文件
     *
     * @param dirPath the directory path
     * @return delete result
     */
    public static boolean delSpecifyDirFiles(String dirPath) {

        boolean delRst = false;

        if (dirPath == null || "".equals(dirPath)) return false;

        File file = new File(dirPath);

        if (!file.exists()) {
            return false;
        }

        if (file.isDirectory()) {

            File[] subFile = file.listFiles();
            if (subFile.length < 1) return true;

            for (int i = 0; i < subFile.length; i++) {

                File f = subFile[i];
                if (!f.isDirectory()) {

                    delSpecifyFile(f.getAbsolutePath());

                } else {

                    delSpecifyDirFiles(f.getAbsolutePath());

                }
                if (i == subFile.length - 1) {

                    delRst = true;

                }

            }

        }

        return delRst;

    }

    /**
     * 复制文件
     * @param oriFile original file
     * @param copiedFile the original file copy
     */
    public static void copyFile(File oriFile, File copiedFile) {

        if (!oriFile.exists()) return;

        InputStream inputStream = null;
        FileOutputStream outputStream = null;

        try {

            inputStream = new FileInputStream(oriFile);
            outputStream = new FileOutputStream(copiedFile);
            int length;
            byte[] bytes = new byte[1024];
            while ((length = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, length);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {

                if (inputStream != null)
                    inputStream.close();
                if (outputStream != null)
                    outputStream.close();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

    }



}
