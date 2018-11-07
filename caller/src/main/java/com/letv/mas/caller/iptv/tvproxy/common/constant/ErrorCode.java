/*
 *  Copyright (c) 2011 乐视网（letv.com）. All rights reserved
 * 
 *  LETV PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package com.letv.mas.caller.iptv.tvproxy.common.constant;

/**
 * Class contains the fields of Error.
 * @author <a href="mailto:pizhigang@letv.com">pizhigang</a>
 */
public class ErrorCode {
    private String code;
    private String message;

    public ErrorCode() {
    }

    public ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return this.code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * @param message
     *            the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
