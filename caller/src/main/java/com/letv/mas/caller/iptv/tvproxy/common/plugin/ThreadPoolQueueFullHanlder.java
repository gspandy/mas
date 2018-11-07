package com.letv.mas.caller.iptv.tvproxy.common.plugin;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolQueueFullHanlder implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        throw new ThreadPoolRejectedExecutionException(r);
    }

}
