package com.letv.mas.caller.iptv.tvproxy.common.plugin;

import java.util.concurrent.RejectedExecutionException;

public class ThreadPoolRejectedExecutionException extends RejectedExecutionException {

    /**
     * 
     */
    private static final long serialVersionUID = -3006034621717118815L;
    private Runnable throwTask;

    public ThreadPoolRejectedExecutionException(Runnable throwTask) {
        super();
        this.throwTask = throwTask;
    }

    public Runnable getThrowTask() {
        return this.throwTask;
    }

    public void setThrowTask(Runnable throwTask) {
        this.throwTask = throwTask;
    }

}
