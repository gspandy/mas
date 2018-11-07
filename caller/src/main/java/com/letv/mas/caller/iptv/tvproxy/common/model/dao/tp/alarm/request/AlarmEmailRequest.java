package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.alarm.request;

import java.io.Serializable;

/**
 * 邮件报警Request 对象类
 */
public class AlarmEmailRequest implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3847247564587261397L;

    private String token;// 决定以谁的邮箱进行发邮件，token由第三方部门根据发件人邮箱生成
    private String email;// 收件人邮箱，多个人以英文逗号分隔
    private String email_cc;// 抄送人邮箱，多个用英文逗号分隔
    private String title;// 邮件标题
    private String msg;// 邮件内容，支持html，UTF-8编码

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail_cc() {
        return email_cc;
    }

    public void setEmail_cc(String email_cc) {
        this.email_cc = email_cc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "AlarmEmailRequest [token=" + token + ", email=" + email + ", email_cc=" + email_cc + ", title=" + title
                + ", msg=" + msg + "]";
    }

}
