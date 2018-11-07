package com.letv.mas.caller.iptv.tvproxy.channel.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.ButtonDto;

import java.util.List;
import java.util.Map;

public class VideoSaleDto extends BaseData {

    /**
     *
     */
    private static final long serialVersionUID = -4411962995395328905L;

    private List<BaseData> dataList;// 数据列表（目前是视频，但是支持专辑等其他数据类型）

    private ButtonDto buttonBuy;// 购买按钮
    private ButtonDto buttonGift;// 领取按钮
    private Map<String, String> titleMap;// 标题集合,key{title1,title2,title3,title4}
    private String background;// 大背景图
    private String img;// 二维码图片，直接展示，不需要生成二维码
    private String url;// 二维码地址，需要生成二维码图片
    private String type;// 数据类型，

    public List<BaseData> getDataList() {
        return this.dataList;
    }

    public void setDataList(List<BaseData> dataList) {
        this.dataList = dataList;
    }

    public ButtonDto getButtonBuy() {
        return this.buttonBuy;
    }

    public void setButtonBuy(ButtonDto buttonBuy) {
        this.buttonBuy = buttonBuy;
    }

    public ButtonDto getButtonGift() {
        return this.buttonGift;
    }

    public void setButtonGift(ButtonDto buttonGift) {
        this.buttonGift = buttonGift;
    }

    public Map<String, String> getTitleMap() {
        return this.titleMap;
    }

    public void setTitleMap(Map<String, String> titleMap) {
        this.titleMap = titleMap;
    }

    public String getBackground() {
        return this.background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
