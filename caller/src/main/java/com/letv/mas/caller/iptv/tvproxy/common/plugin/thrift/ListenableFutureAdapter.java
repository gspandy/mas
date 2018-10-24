package com.letv.mas.caller.iptv.tvproxy.common.plugin.thrift;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.letv.mas.caller.iptv.tvproxy.common.util.HttpClientUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.thrift.async.AsyncMethodCallback;

import java.util.concurrent.*;

public class ListenableFutureAdapter<R> implements AsyncMethodCallback<Object>, ListenableFuture<R> {
    private static Log httpClientUtilLogger = LogFactory.getLog(HttpClientUtil.class);

    private ListenableFutureTask<R> listenableFutureTask = ListenableFutureTask.create(new Callable<R>() {
        @Override
        public R call() throws Exception {
            return result;
        }
    });

    private R result;

    private GenericObjectPool<Object> pool;

    private Object connection;

    private long startTime;

    private String url;

    public ListenableFutureAdapter() {
    }

    @Override
    public void onComplete(Object arg0) {
        try {
            long costTime = System.currentTimeMillis() - this.startTime;
            httpClientUtilLogger.info(url + "|200|0|" + costTime + "|0");
            this.result = (R) (arg0.getClass().getMethod("getResult", null).invoke(arg0, null));
            this.listenableFutureTask.run();
        } catch (Exception e) {
        } finally {
            try {
                this.pool.returnObject(connection);
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void onError(Exception arg0) {
        try {
            long costTime = System.currentTimeMillis() - this.startTime;
            httpClientUtilLogger.error(url + "|000|0|" + costTime + "|1");
            // 抛出异常的连接认为不可用，从池中remove掉
            this.pool.invalidateObject(connection);
            this.listenableFutureTask.cancel(false);
        } catch (Exception e) {
        }
    }

    public void setData(GenericObjectPool<Object> pool, Object connection, long startTime, String url) {
        this.pool = pool;
        this.connection = connection;
        this.startTime = startTime;
        this.url = url;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return this.listenableFutureTask.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return this.listenableFutureTask.isCancelled();
    }

    @Override
    public boolean isDone() {
        return this.listenableFutureTask.isDone();
    }

    @Override
    public R get() throws InterruptedException, ExecutionException {
        return this.listenableFutureTask.get();
    }

    @Override
    public R get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.listenableFutureTask.get(timeout, unit);
    }

    @Override
    public void addListener(Runnable listener, Executor executor) {
        this.listenableFutureTask.addListener(listener, executor);
    }

}
