package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

public class PromotionsInfo {
    /**
	 * 
	 */
    public static final int NO_BUY = 0;
    public static final int UN_PAY = 1;
    public static final int PAYED = 2;
    public static final int EXP_PAY = 3;
    public static final int FORBID_PAY = 4;
    private static final long serialVersionUID = 5201848680600756900L;
    private Integer id;// 活动id
    private String name;// 活动名称
    private Float price;// 活动价格
    private Integer hasMacBuy;// 是否已买，0未买，1已买,未付款，2已付款，3、交易超时,4不符合购买条件
    private Integer hasUserBuy;// 是否已买，0未买，1已买,未付款，2已付款，3、交易超时,4不符合购买条件
    private Integer hasBuy;// 是否已买，0未买，1已买,未付款，2已付款，3、交易超时,4不符合购买条件
    private String orderId;

    public PromotionsInfo() {
    }

    public PromotionsInfo(Integer id, String name, Float price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return this.price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getHasMacBuy() {
        return this.hasMacBuy;
    }

    public void setHasMacBuy(Integer hasMacBuy) {
        this.hasMacBuy = hasMacBuy;
    }

    public Integer getHasUserBuy() {
        return this.hasUserBuy;
    }

    public void setHasUserBuy(Integer hasUserBuy) {
        this.hasUserBuy = hasUserBuy;
    }

    public Integer getHasBuy() {
        return this.hasBuy;
    }

    public void setHasBuy(Integer hasBuy) {
        this.hasBuy = hasBuy;
    }

    @Override
    public String toString() {
        return "PromotionsInfo [id=" + this.id + ", name=" + this.name + ", price=" + this.price + ", hasMacBuy="
                + this.hasMacBuy + ", hasUserBuy=" + this.hasUserBuy + ", hasBuy=" + this.hasBuy + ", orderId="
                + this.orderId + "]";
    }

}
