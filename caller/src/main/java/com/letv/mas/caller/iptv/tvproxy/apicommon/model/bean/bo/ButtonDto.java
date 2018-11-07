package com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo;

/**
 * 按钮模型封装类
 * @author liyunlong
 */
public class ButtonDto extends BaseData {

    public ButtonDto() {

    }

    /**
     * 按钮标题
     */
    private String title;

    /**
     * 按钮副标题
     */
    private String subTitle;

    /**
     * 按钮类型，1—预约按钮(跳h5)，2—购买按钮(跳收银台)，3--频道按钮（跳3D频道页）
     */
    private Integer type;

    /**
     * 按钮是否可用，0-不可用，1-可用，默认可用
     */
    private Integer enable = 1;

    /**
     * 打开浏览器类型，1--打开外置浏览器，2--打开内置浏览器
     */
    private Integer browserType;

    /**
     * 超前点映：0--跳h5，1--跳收银台,2--跳频道页(后期需要跟客户端统一)
     * 其他地方：0--无响应，1--直接跳收银台，2--先跳H5再跳收银台，3--只跳H5，4--跳超前院线，定制业务，5--跳919活动页，
     */
    private Integer openType;

    /**
     * 可见性，0/null--不可见，1--可见
     */
    private Integer visible = 1;

    public ButtonDto(String title, Integer type, Integer browserType, Integer openType) {
        this.title = title;
        this.type = type;
        this.browserType = browserType;
        this.openType = openType;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return this.subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getEnable() {
        return this.enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Integer getBrowserType() {
        return this.browserType;
    }

    public void setBrowserType(Integer browserType) {
        this.browserType = browserType;
    }

    public Integer getOpenType() {
        return this.openType;
    }

    public void setOpenType(Integer openType) {
        this.openType = openType;
    }

    public Integer getVisible() {
        return this.visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

}
