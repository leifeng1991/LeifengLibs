package com.leifeng.lib.utils;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 描述:
 *
 * @author leifeng
 *         2018/3/28 11:50
 */


public class ThreadPoolManager {
    private ThreadPoolExecutor threadPoolExecutor;
    private LinkedBlockingQueue<Future<?>> service = new LinkedBlockingQueue<>();
    private static ThreadPoolManager instance = new ThreadPoolManager();

    public static ThreadPoolManager getInstance() {
        return instance;
    }

    private ThreadPoolManager() {
        RejectedExecutionHandler handler = new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
                try {
                    service.put(new FutureTask<>(runnable, null));//
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        threadPoolExecutor = new ThreadPoolExecutor(4, 10, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(4), handler);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {

                    FutureTask futureTask = null;

                    try {
                        LogUtil.e("myThreadPook", "service size " + service.size());
                        futureTask = (FutureTask) service.take();
                        LogUtil.e("myThreadPook", "池  " + threadPoolExecutor.getPoolSize());

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (futureTask != null) {
                        threadPoolExecutor.execute(futureTask);
                    }
                }
            }
        };
        threadPoolExecutor.execute(runnable);
    }

    public <T> void execute(final FutureTask<T> futureTask, Object delayed) {
        if (futureTask != null) {
            try {

                if (delayed != null) {
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        public void run() {
                            try {
                                service.put(futureTask);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }, (long) delayed);
                } else {
                    service.put(futureTask);

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

}
