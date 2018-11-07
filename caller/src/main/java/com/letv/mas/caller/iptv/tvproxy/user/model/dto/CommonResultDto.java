package com.letv.mas.caller.iptv.tvproxy.user.model.dto;

import java.io.Serializable;

/**
 * 公共范型返回dto
 * @author Jalon
 * @param <T>
 */
public class CommonResultDto<T> implements Serializable {

    private static final long serialVersionUID = 8668889519830325316L;

    private T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

}
