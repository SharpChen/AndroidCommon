package cn.opensrc.comnlib.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author:       sharp
 * Created on:   8/2/16 5:10 PM
 * Description:  缓程池
 * Revisions:
 */
public final class ThreadUtils {

    private ThreadUtils(){}

    private static ExecutorService cachedThreadPool;
    private static ExecutorService fixedThreadPool;
    private static ExecutorService scheduledThreadPool;
    private static ExecutorService singleThreadExecutor;

    /**
     * 获取 CachedThreadPool 线程池
     * @return CachedThreadPool instance
     */
    public static ExecutorService getCachedThreadPool() {
        if (cachedThreadPool == null) cachedThreadPool =Executors.newCachedThreadPool();
        return cachedThreadPool;
    }

    /**
     * 获取 FixedThreadPool 线程池
     * @return FixedThreadPool instance
     */
    public static ExecutorService getFixedThreadPool() {
        if (fixedThreadPool == null) fixedThreadPool =Executors.newFixedThreadPool(3);
        return fixedThreadPool;
    }

    /**
     * 获取 ScheduledThreadPool 线程池
     * @return ScheduledThreadPool instance
     */
    public static ExecutorService getScheduledThreadPool() {
        if (scheduledThreadPool == null) scheduledThreadPool =Executors.newScheduledThreadPool(3);
        return scheduledThreadPool;
    }

    /**
     * 获取 SingleThreadExecutor 线程池
     * @return SingleThreadExecutor instance
     */
    public static ExecutorService getSingleThreadExecutor() {
        if (singleThreadExecutor == null) singleThreadExecutor =Executors.newSingleThreadExecutor();
        return singleThreadExecutor;
    }

}
