package com.letv.mas.caller.iptv.tvproxy.channel.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.DataConstant;
import com.letv.mas.caller.iptv.tvproxy.channel.constants.ChannelConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.DomainMapping;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.ChannelMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.Vip;
import com.letv.mas.caller.iptv.tvproxy.common.util.CategoryIdCodeMapping;

/**
 * 频道
 */
public class Channel extends BaseChannel {

    /**
     * 
     */
    private static final long serialVersionUID = 9008575583831111538L;

    /**
     * 频道ID private int id; // 频道ID
     */
    private Integer channelId;

    /**
     * 频道名称 private String name;// 菜单名称
     */
    private String name;
    private String subName;
    private String channelName;

    /**
     * 父频道ID
     */
    private Integer parentChannelId;

    /**
     * 标题icon private String bigPic;// 焦点图
     */
    private String img;

    /**
     * 标题icon private String smallPic;// 小图
     */
    private String titleIcon;

    private String pic1;

    private String pic2;

    /**
     * 标题背景色 private String color;// 颜色值对应频道墙
     */
    private String titleBgColor;

    /**
     * 标题焦点1 private String focus;// 看点，二级标题
     */
    private String titleFocus1;

    /**
     * 标题焦点2 private String focus2;// 总集或部
     */
    private String titleFocus2;

    /**
     * 内容分类 private int cid;// 内容分类ID
     */
    private Integer categoryId;

    /**
     * 频道code
     */
    private String channelCode;//

    /**
     * 专辑id
     */
    private Integer albumId;//

    /**
     * 专题id
     */
    private Integer pid;//

    /**
     * 默认码流
     */
    private String defaultStream;
    private String defaultStreamName;
    /**
     * 兼容原先的老逻辑
     * @return
     */
    private int dataType;

    /**
     * 频道子类型 0 跳转频道首页 1 跳转cp列表详情页 levidi 1.0.*使用
     */
    private Integer subType;

    /**
     * 会员id
     */
    private Integer productId;

    /**
     * 会员名称
     */
    private String productName;

    /**
     * 是否订阅（可扩展）0、未订阅 1、订阅
     */
    private Integer isSelected;

    /**
     * 页面ID
     */
    private Integer pageId;

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public Integer getSubType() {
        return subType;
    }

    public void setSubType(Integer subType) {
        this.subType = subType;
    }

    public Integer getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Integer isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public int getDataType() {
        return DataConstant.DATA_TYPE_CHANNEL;
    }

    @Override
    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public Integer getChannelId() {
        return this.channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentChannelId() {
        return this.parentChannelId;
    }

    public void setParentChannelId(Integer parentChannelId) {
        this.parentChannelId = parentChannelId;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitleBgColor() {
        return this.titleBgColor;
    }

    public void setTitleBgColor(String titleBgColor) {
        this.titleBgColor = titleBgColor;
    }

    public String getTitleFocus1() {
        return this.titleFocus1;
    }

    public void setTitleFocus1(String titleFocus1) {
        this.titleFocus1 = titleFocus1;
    }

    public String getTitleFocus2() {
        return this.titleFocus2;
    }

    public void setTitleFocus2(String titleFocus2) {
        this.titleFocus2 = titleFocus2;
    }

    public Integer getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getChannelName() {
        return this.channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getTitleIcon() {
        return this.titleIcon;
    }

    public void setTitleIcon(String titleIcon) {
        this.titleIcon = titleIcon;
    }

    public String getChannelCode() {
        return this.channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public Integer getAlbumId() {
        return this.albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getDefaultStream() {
        return this.defaultStream;
    }

    public void setDefaultStream(String defaultStream) {
        this.defaultStream = defaultStream;
    }

    public String getDefaultStreamName() {
        return this.defaultStreamName;
    }

    public void setDefaultStreamName(String defaultStreamName) {
        this.defaultStreamName = defaultStreamName;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public String getPic1() {
        return pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public Channel() {

    }

    public Channel(ChannelMysqlTable channelMysqlTable, Integer broadcastId, String terminalApplication, String vnum,
                   Integer parentChannelId) {
        Integer id = channelMysqlTable.getId();

        this.channelId = id;
        this.name = channelMysqlTable.getName();
        this.parentChannelId = channelMysqlTable.getParentid();
        this.titleBgColor = channelMysqlTable.getColor();
        this.titleFocus1 = channelMysqlTable.getFocus();
        this.titleFocus2 = channelMysqlTable.getFocus2();
        this.channelName = channelMysqlTable.getName();
        this.albumId = channelMysqlTable.getAlbumId();
        this.pid = channelMysqlTable.getPid();
        this.img = DomainMapping.changeDomainByBraodcastId(channelMysqlTable.getBig_pic(), broadcastId,
                terminalApplication);
        this.setDataUrl(DomainMapping.changeDomainByBraodcastId(channelMysqlTable.getUrl(), broadcastId,
                terminalApplication));
        this.setConfigInfo(channelMysqlTable.getConfig_info());
        this.titleIcon = DomainMapping.changeDomainByBraodcastId(channelMysqlTable.getSmall_pic(), broadcastId,
                terminalApplication);
        this.channelCode = channelMysqlTable.getChannelcode();
        this.defaultStream = channelMysqlTable.getDefault_stream();

        this.categoryId = channelMysqlTable.getCategoryid() == null ? CategoryIdCodeMapping.getCidByMid(id)
                : channelMysqlTable.getCategoryid();

        /*
         * 标题数据类型
         */
        // if (channelMysqlTable.getRequest_type() == null) {
        // // 不处理,继续执行
        // } else if (channelMysqlTable.getRequest_type() == 1) {// 频道首页
        // this.setTitleDataType(DataConstant.DATA_TYPE_MULTILIST);
        // } else if (channelMysqlTable.getRequest_type() == 12) {// 普通列表页
        // this.setTitleDataType(DataConstant.DATA_TYPE_LIST);
        // } else if (channelMysqlTable.getRequest_type() == 13) {// 猜你喜欢
        // this.setTitleDataType(DataConstant.DATA_TYPE_LIST_RECOMMENDATION);
        // } else if (channelMysqlTable.getRequest_type() == 14) {// 儿童模式
        // this.setTitleDataType(DataConstant.DATA_TYPE_CHILDREN);
        // } else if (channelMysqlTable.getRequest_type() == 0) {
        // this.setTitleDataType(DataConstant.DATA_TYPE_CHANNEL);
        // }
        // //以上逻辑不要，数据库中数据要统一

        // if (this.getTitleDataType() == null &&
        // channelMysqlTable.getParentid() != null
        // && channelMysqlTable.getParentid() == ChannelConstants.CHANNEL) {
        // if (channelMysqlTable.getRequest_type() != null &&
        // channelMysqlTable.getRequest_type() != 0) {
        // this.setTitleDataType(channelMysqlTable.getRequest_type());
        // } else {
        // this.setTitleDataType(DataConstant.DATA_TYPE_CHANNEL);
        // }
        //
        // }
        /**
         * 新逻辑处理，如果channelMysqlTable中dataType不为空则按照新逻辑走
         */

        if (channelMysqlTable.getData_type() != null) {
            this.setTitleDataType(channelMysqlTable.getData_type());
        }

        /*
         * 国广，自有2.6之后的版本会把亲子换成小色块，放到频道墙的第14个位置，并且设置小标题 vnum传入7或者7.0，就是2.6之后的频道墙
         */
        if (vnum != null && Float.parseFloat(vnum) == 7f && parentChannelId == ChannelConstants.CHANNEL && id != null
                && id == ChannelConstants.PARENTING) {
            this.titleFocus1 = "陪伴宝贝成长";
            // this.titleFocus2 = "陪伴宝贝成长";
        }
    }

    public Channel(Vip vip, ChannelMysqlTable channelMysqlTable) {
        if (channelMysqlTable != null) {
            this.parentChannelId = channelMysqlTable.getId();
        }
        if (vip != null) {
            this.productId = vip.getProductId();
            this.productName = vip.getName();
            this.name = vip.getName();
            this.img = vip.getPic();
        }
        this.dataType = DataConstant.DATA_TYPE_CHANNEL; // 设置跳转类型为频道
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
