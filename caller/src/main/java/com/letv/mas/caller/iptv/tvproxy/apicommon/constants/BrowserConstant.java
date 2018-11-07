package com.letv.mas.caller.iptv.tvproxy.apicommon.constants;

/**
 * 浏览器
 */
public class BrowserConstant {

    /**
     * 打开外置浏览器
     */
    public final static int BROWSER_TYPE = 1;

    /**
     * 打开内置浏览器
     */
    public final static int BROWSER_TYPE_BUILTIN = 2;

    /**
     * 打开浏览器后的交互，0--不做响应，无交互
     */
    public final static int BROWSER_OPENTYPE_NONE = 0;

    /**
     * 打开浏览器后的交互，1--跳TV版收银台
     */
    public final static int BROWSER_OPENTYPE_CHECKOUT = 1;

    /**
     * 打开浏览器后的交互，2--跳频道页
     */
    public final static int BROWSER_OPENTYPE_CHANNEL = 2;

}
