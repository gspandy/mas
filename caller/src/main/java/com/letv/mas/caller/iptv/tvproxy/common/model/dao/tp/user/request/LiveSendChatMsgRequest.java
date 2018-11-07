package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.request;

/**
 * 发送直播弹幕请求封装类
 * @author KevinYi
 */
public class LiveSendChatMsgRequest {

    /**
     * 聊天室id
     */
    private String roomId;

    /**
     * 客户端ip
     */
    private String clientIp;

    /**
     * 是否向嘉宾提问
     */
    private Boolean forhost;

    /**
     * 消息内容
     */
    private String message;

    /**
     * 用户token
     */
    private String sso_tk;

    /**
     * 内容颜色值，例如：00FCFF
     */
    private String color;

    /**
     * 字体大小［s:小号 m:中号 l:大号］默认值：m
     */
    private String font;

    /**
     * 显示的位置 ［1:顶部 2:中间 3:底部 4:随机］默认值：4
     */
    private Integer position;

    /**
     * 来源［1:Web 2:iPhone 3:iPad4:TV 5:PC桌面 6:Android Phone7:LePhone］，默认值：1
     */
    private Integer from;

    /**
     * 跨域
     */
    private String callback;

    /**
     * 类型 [1:普通消息 2:pc端带表情的消息 9:聊天室公告] 默认值1
     */
    private Integer type;

    /**
     * 实名制认证 1需要认证 其他值，不进行实名验证
     */
    private Integer realname;

    /**
     * pc端表情x坐标
     */
    private String x;

    /**
     * pc端表情y坐标
     */
    private String y;

    /**
     * join 聊天室时，socket服务器返回的vtkey
     */
    private String vtkey;

    public String getRoomId() {
        return this.roomId;
    }

    public LiveSendChatMsgRequest(String roomId, String clientIp, Boolean forhost, String message, String sso_tk,
            String color, String font, Integer position, Integer from, String callback, Integer type, Integer realname,
            String x, String y, String vtkey) {
        super();
        this.roomId = roomId;
        this.clientIp = clientIp;
        this.forhost = forhost;
        this.message = message;
        this.sso_tk = sso_tk;
        this.color = color;
        this.font = font;
        this.position = position;
        this.from = from;
        this.callback = callback;
        this.type = type;
        this.realname = realname;
        this.x = x;
        this.y = y;
        this.vtkey = vtkey;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getClientIp() {
        return this.clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public Boolean getForhost() {
        return this.forhost;
    }

    public void setForhost(Boolean forhost) {
        this.forhost = forhost;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSso_tk() {
        return this.sso_tk;
    }

    public void setSso_tk(String sso_tk) {
        this.sso_tk = sso_tk;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFont() {
        return this.font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public Integer getPosition() {
        return this.position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getFrom() {
        return this.from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public String getCallback() {
        return this.callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getRealname() {
        return this.realname;
    }

    public void setRealname(Integer realname) {
        this.realname = realname;
    }

    public String getX() {
        return this.x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return this.y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getVtkey() {
        return this.vtkey;
    }

    public void setVtkey(String vtkey) {
        this.vtkey = vtkey;
    }

}
