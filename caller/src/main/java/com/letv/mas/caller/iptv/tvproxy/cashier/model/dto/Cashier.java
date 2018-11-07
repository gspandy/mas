package com.letv.mas.caller.iptv.tvproxy.cashier.model.dto;

import com.letv.mas.caller.iptv.tvproxy.video.model.dto.AlbumDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by wangli on 4/24/17.
 * 收银台
 */
@ApiModel(value = "Cashier", description = "收银台")
public class Cashier {

    // 收银台的序号
    @ApiModelProperty(value = "收银台的序号")
    private String cashierId;
    // 收银台的图片信息
    @ApiModelProperty(value = "收银台的图片信息")
    private String pic;
    // 收银台的视频信息
    @ApiModelProperty(value = "收银台的视频信息")
    private Integer vid;
    // 收银台类型
    @ApiModelProperty(value = "收银台类型")
    private String type;
    // 收银台的套餐信息
    @ApiModelProperty(value = "收银台的套餐信息")
    private List<PackageInfo> packages;
    // 推荐信息
    @ApiModelProperty(value = "推荐信息")
    private List<Recommend> recommends;
    // 绑卡信息
    @ApiModelProperty(value = "绑卡信息")
    private List<BoundPayment> boundPayments;

    public String getCashierId() {
        return cashierId;
    }

    public void setCashierId(String cashierId) {
        this.cashierId = cashierId;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<PackageInfo> getPackages() {
        return packages;
    }

    public void setPackages(List<PackageInfo> packages) {
        this.packages = packages;
    }

    public List<Recommend> getRecommends() {
        return recommends;
    }

    public void setRecommends(List<Recommend> recommends) {
        this.recommends = recommends;
    }

    public List<BoundPayment> getBoundPayment() {
        return boundPayments;
    }

    public void setBoundPayment(List<BoundPayment> boundPayments) {
        this.boundPayments = boundPayments;
    }

    /**
     * 收银台的套餐信息：是商品、活动、订单、优惠券的集合
     */
    @ApiModel(value = "PackageInfo", description = "收银台的套餐信息：是商品、活动、订单、优惠券的集合")
    public static class PackageInfo {

        // 商品ID
        @ApiModelProperty(value = "商品ID")
        private String packageID;
        // 二维码地址
        @ApiModelProperty(value = "二维码地址")
        private String url;
        // 二维码短链地址
        @ApiModelProperty(value = "二维码短链地址")
        private String shortUrl;
        // 套餐图片信息
        @ApiModelProperty(value = "套餐图片信息")
        private String img;
        // 商品信息
        @ApiModelProperty(value = "商品信息")
        private Product product;
        // 活动信息
        @ApiModelProperty(value = "活动信息")
        private List<Activity> activitys;
        // 是否是默认套餐
        @ApiModelProperty(value = "是否是默认套餐")
        private boolean isDefault;
        // 自动续费标识（true:订阅自动续费，false:非自动续费）
        @ApiModelProperty(value = "连续包月标识（true:订阅自动续费，false:非自动续费）")
        private boolean renewal;
        // 套餐协议标题
        @ApiModelProperty(value = "套餐协议标题")
        private String agreementTitle;

        public String getAgreementTitle() {
            return agreementTitle;
        }

        public void setAgreementTitle(String agreementTitle) {
            this.agreementTitle = agreementTitle;
        }

        public String getPackageID() {
            return packageID;
        }

        public void setPackageID(String packageID) {
            this.packageID = packageID;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public List<Activity> getActivitys() {
            return activitys;
        }

        public void setActivitys(List<Activity> activitys) {
            this.activitys = activitys;
        }

        public boolean isDefault() {
            return isDefault;
        }

        public void setDefault(boolean aDefault) {
            isDefault = aDefault;
        }

        public String getShortUrl() {
            return shortUrl;
        }

        public void setShortUrl(String shortUrl) {
            this.shortUrl = shortUrl;
        }

        public boolean isRenewal() {
            return renewal;
        }

        public void setRenewal(boolean renewal) {
            this.renewal = renewal;
        }

    }

    /**
     * 收银台的推荐信息：可以连接到视频
     */
    @ApiModel(value = "Recommend", description = "收银台的推荐信息：可以连接到视频")
    public static class Recommend extends AlbumDto {

        // 视频ID
        @ApiModelProperty(value = "视频ID")
        private String vid;
        // 名称
        @ApiModelProperty(value = "名称")
        private String name;
        // 图片
        @ApiModelProperty(value = "图片")
        private String image;
        // 评分
        @ApiModelProperty(value = "评分")
        private float rating;
        // 是否付费
        @ApiModelProperty(value = "是否付费")
        private boolean isCharge;
        // 描述
        @ApiModelProperty(value = "描述")
        private String desc;

        public String getVid() {
            return vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }

        public float getRating() {
            return rating;
        }

        public void setRating(float rating) {
            this.rating = rating;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public boolean isCharge() {
            return isCharge;
        }

        public void setCharge(boolean charge) {
            isCharge = charge;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    /**
     * 商品信息
     */
    @ApiModel(value = "Product", description = "商品信息")
    public static class Product {

        // 商品ID
        @ApiModelProperty(value = "商品ID")
        private String productId;
        // 商品名称
        @ApiModelProperty(value = "商品名称")
        private String productName;
        // 当前价格
        @ApiModelProperty(value = "当前价格")
        private double price;
        // 活动名称
        @ApiModelProperty(value = "活动名称")
        private String activityName;

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getActivityName() {
            return activityName;
        }

        public void setActivityName(String activityName) {
            this.activityName = activityName;
        }
    }

    /**
     * 活动信息
     */
    @ApiModel(value = "Activity", description = "活动信息")
    public static class Activity {

        // 活动ID
        @ApiModelProperty(value = "活动ID")
        private String activityId;
        // 价值
        @ApiModelProperty(value = "价格")
        private String price;
        // 活动名称
        @ApiModelProperty(value = "活动名称")
        private String activityName;
        // 活动类型
        @ApiModelProperty(value = "活动类型")
        private Integer activityType;

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getActivityName() {
            return activityName;
        }

        public void setActivityName(String activityName) {
            this.activityName = activityName;
        }

        public Integer getActivityType() {
            return activityType;
        }

        public void setActivityType(Integer activityType) {
            this.activityType = activityType;
        }
    }
}
