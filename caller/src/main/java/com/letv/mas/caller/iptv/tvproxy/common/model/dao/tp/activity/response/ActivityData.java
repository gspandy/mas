package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.activity.response;

import java.util.Map;

public class ActivityData {
    private String errno;// 错误码，成功时为10000
    private String type;// 数据类型标识
    private PromotionData promotion;// 推广活动

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getErrno() {
        return errno;
    }

    public void setErrno(String errno) {
        this.errno = errno;
    }

    public PromotionData getPromotion() {
        return promotion;
    }

    public void setPromotion(PromotionData promotion) {
        this.promotion = promotion;
    }

    public static class PromotionData {
        private String reqid;// 本次请求唯一id，由观星系统分配，可用于调试跟踪
        private String sessionid;// 本次交互所属会话id
        private String posid;// 推广位标识
        private String promoid;// 推广活动id，由观星系统分配，可用于调试跟踪
        private Long timeout;// 推广活动自失效时间，单位秒
        private ActionInfo creative;// 创意实体

        public String getReqid() {
            return reqid;
        }

        public void setReqid(String reqid) {
            this.reqid = reqid;
        }

        public String getSessionid() {
            return sessionid;
        }

        public void setSessionid(String sessionid) {
            this.sessionid = sessionid;
        }

        public String getPosid() {
            return posid;
        }

        public void setPosid(String posid) {
            this.posid = posid;
        }

        public String getPromoid() {
            return promoid;
        }

        public void setPromoid(String promoid) {
            this.promoid = promoid;
        }

        public Long getTimeout() {
            return timeout;
        }

        public void setTimeout(Long timeout) {
            this.timeout = timeout;
        }

        public ActionInfo getCreative() {
            return creative;
        }

        public void setCreative(ActionInfo creative) {
            this.creative = creative;
        }

    }

    public static class ActionInfo {
        private String id;// 创意实体id，由观星系统分配
        private String template;// 所属创意模板类型，用于客户端进行渲染。 由观星系统统一定义
        private Map<String, Object> material;// 物料信息集合，由具体推广位的业务方来进一步定义
        private TrackInfo track;// 监测上报信息集合
        // key:{params,params_1,params_2},value:json encode后的打洞参数字符串
        // 如果只有一个按钮，key值为params，如果有多个按钮，key取值为params_{i}
        private Map<String, String> jump;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTemplate() {
            return template;
        }

        public void setTemplate(String template) {
            this.template = template;
        }

        public Map<String, Object> getMaterial() {
            return material;
        }

        public void setMaterial(Map<String, Object> material) {
            this.material = material;
        }

        public TrackInfo getTrack() {
            return track;
        }

        public void setTrack(TrackInfo track) {
            this.track = track;
        }

        public Map<String, String> getJump() {
            return jump;
        }

        public void setJump(Map<String, String> jump) {
            this.jump = jump;
        }

    }

    public static class TrackInfo {
        private AddressInfo miaozhen;// 秒针监测路径
        private AddressInfo admaster;// admaster监测路径
        private OpenInfo nielsen;

        public AddressInfo getMiaozhen() {
            return miaozhen;
        }

        public void setMiaozhen(AddressInfo miaozhen) {
            this.miaozhen = miaozhen;
        }

        public AddressInfo getAdmaster() {
            return admaster;
        }

        public void setAdmaster(AddressInfo admaster) {
            this.admaster = admaster;
        }

        public OpenInfo getNielsen() {
            return nielsen;
        }

        public void setNielsen(OpenInfo nielsen) {
            this.nielsen = nielsen;
        }
    }

    public static class AddressInfo {
        private String click;
        private String imp;

        public String getClick() {
            return click;
        }

        public void setClick(String click) {
            this.click = click;
        }

        public String getImp() {
            return imp;
        }

        public void setImp(String imp) {
            this.imp = imp;
        }
    }

    public static class OpenInfo {
        private String open;

        public String getOpen() {
            return open;
        }

        public void setOpen(String open) {
            this.open = open;
        }
    }

}
