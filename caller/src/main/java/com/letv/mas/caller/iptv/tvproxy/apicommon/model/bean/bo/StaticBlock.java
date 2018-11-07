package com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo;

/**
 * 专题
 */
public class StaticBlock extends BaseData {

    /**
     *
     */
    private static final long serialVersionUID = -8244945341900102355L;

    /**
     * 板块id
     */
    private String blockId;

    /**
     * 版块名称
     */
    private String name;

    /**
     * 副标题
     */
    private String subName;

    /**
     * 图片
     */
    private String img;

    private String shortDesc;

    /**
     * 这几个字段会删除掉
     */
    private String adviceContent;
    private String adviceImg;
    private String adviceHourLong;
    private String adviceDayLong;

    /**
     * 子标签id
     */
    private String labelIdToPlay;

    private int dataType;
    /**
     * 我的页面会员相关板块的位置，
     * 1、激活会员服务，2--开通会员，3(目前没有我要续费)--我要续费，4--会员兑换，5--播放记录，6--收藏与追剧，
     * focus--旧的"我的"页面焦点图，title--标题数据类型
     */
    private String pos;

    private String pic1;

    private String content;

    public String getPic1() {
        return this.pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    @Override
    public int getDataType() {
        return this.dataType;
    }

    @Override
    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubName() {
        return this.subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLabelIdToPlay() {
        return this.labelIdToPlay;
    }

    public void setLabelIdToPlay(String labelIdToPlay) {
        this.labelIdToPlay = labelIdToPlay;
    }

    public String getBlockId() {
        return this.blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public String getAdviceContent() {
        return this.adviceContent;
    }

    public void setAdviceContent(String adviceContent) {
        this.adviceContent = adviceContent;
    }

    public String getAdviceImg() {
        return this.adviceImg;
    }

    public void setAdviceImg(String adviceImg) {
        this.adviceImg = adviceImg;
    }

    public String getAdviceHourLong() {
        return this.adviceHourLong;
    }

    public void setAdviceHourLong(String adviceHourLong) {
        this.adviceHourLong = adviceHourLong;
    }

    public String getAdviceDayLong() {
        return this.adviceDayLong;
    }

    public void setAdviceDayLong(String adviceDayLong) {
        this.adviceDayLong = adviceDayLong;
    }

    public String getPos() {
        return this.pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
