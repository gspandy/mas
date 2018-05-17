package com.letv.mas.caller.interceptor;

import io.grpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by leeco on 18/5/17.
 */
public class GrpcInterceptor implements ClientInterceptor {
    private static final Logger log = LoggerFactory.getLogger(GrpcInterceptor.class);

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
        log.info(method.getFullMethodName());
        return next.newCall(method, callOptions);
    }
}
