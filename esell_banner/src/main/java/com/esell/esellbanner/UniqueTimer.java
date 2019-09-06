package com.esell.esellbanner;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * author : soyao
 * 唯一定时器
 */
public class UniqueTimer {

    /**
     * 计划任务线程池
     */
    private ScheduledExecutorService mPool;
    private Runnable mTask;

    public void startTimer() {
        if (mPool == null) {
            mPool = Executors.newScheduledThreadPool(1);
        }

        if (mTask == null) {
            mTask = new Runnable() {
                @Override
                public void run() {
                    if (onTimingExecute != null) onTimingExecute.run();
                }
            };

            mPool.scheduleAtFixedRate(mTask, 0, 1, TimeUnit.SECONDS);
        }

    }

    private OnTimingExecute onTimingExecute;

    public void setOnTimingExecute(OnTimingExecute onTimingExecute) {
        this.onTimingExecute = onTimingExecute;
    }

    public interface OnTimingExecute {
        void run();
    }

    public void stopTimer() {
        if (mPool != null) {
            mPool.shutdown();
            mPool = null;
            if (mTask != null) {
                mTask = null;
            }
        }
    }

}
