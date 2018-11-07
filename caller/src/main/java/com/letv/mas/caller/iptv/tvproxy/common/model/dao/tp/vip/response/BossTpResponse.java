package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

/**
 * boss V2接口响应基类
 * @param <T>
 *            用来接收boss V2接口返回的值得java bean
 */
public class BossTpResponse<T> {

    /**
     * 请求结果状态，0--正常，其他值代表错误(鉴权时9999代表用户被封禁)
     */
    protected Integer code;
    protected String msg;
    protected T data;

    /**
     * 判断响应是否成功
     */
    public boolean isSucceed() {
        if (code != null && code == 0 && data != null) {
            return true;
        }
        return false;
    }

    /**
     * 判断用户是否被封禁(仅在鉴权时才会有用户被封禁情况,所以此方法在鉴权时才有效果,其他情况无效)
     * @return
     *         {@code true} 用户被封禁, {@code false} 用户未被封禁
     */
    public boolean isForbiddin() {
        return code == 9999 ? true : false;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
