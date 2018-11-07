package com.letv.mas.caller.iptv.tvproxy.common.model.bean;

/**
 * 下单接口返回的参与签名参数
 * Created by liudaojie on 16/8/18.
 */
public class ExtendParam {
    private String version = "2.0";// 版本号
    private String service;// 服务类型
    private String merchant_business_id;// 业务线id
    private String notify_url;// 支付成功后通知商户的地址
    private String merchant_no;// 传订单号
    private String out_trade_no;// 传订单号
    private String currency;// 币种
    private String pay_expire = "21600";// 过期时间15天，单位分钟
    private String product_id;// 产品id
    private String product_urls;// 产品url
    private Boolean is_auth;// hk purchase parameter
    private Boolean is_create_token;// hk purchase parameter
    private String locale;// hk purchase parameter
    private String language;// 0--Traditional,1--English,2--Simplified
    private String index = "1";// 索引
    private String input_charset = "UTF-8";// 编码格式
    private String sign_type = "MD5";// 签名类型
    private String price;// 价格
    private String token;// pay token
    private String lepayOrderNo;// lepay order number

    private String timestamp; // 时间戳
    private String user_id; // 用户id
    private String displayName; // 用户显示名
    private String product_name; // 产品名
    private String product_desc; // 产品描述
    private String ip; // 客户端ip
    private String sign; // 签名
    private String call_back_url; // 用于支付sdk签名的字段

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getService() {
        return this.service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getMerchant_business_id() {
        return this.merchant_business_id;
    }

    public void setMerchant_business_id(String merchant_business_id) {
        this.merchant_business_id = merchant_business_id;
    }

    public String getNotify_url() {
        return this.notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getMerchant_no() {
        return this.merchant_no;
    }

    public void setMerchant_no(String merchant_no) {
        this.merchant_no = merchant_no;
    }

    public String getOut_trade_no() {
        return this.out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPay_expire() {
        return this.pay_expire;
    }

    public void setPay_expire(String pay_expire) {
        this.pay_expire = pay_expire;
    }

    public String getProduct_id() {
        return this.product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_urls() {
        return this.product_urls;
    }

    public void setProduct_urls(String product_urls) {
        this.product_urls = product_urls;
    }

    public Boolean getIs_auth() {
        return this.is_auth;
    }

    public void setIs_auth(Boolean is_auth) {
        this.is_auth = is_auth;
    }

    public Boolean getIs_create_token() {
        return this.is_create_token;
    }

    public void setIs_create_token(Boolean is_create_token) {
        this.is_create_token = is_create_token;
    }

    public String getLocale() {
        return this.locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getIndex() {
        return this.index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getInput_charset() {
        return this.input_charset;
    }

    public void setInput_charset(String input_charset) {
        this.input_charset = input_charset;
    }

    public String getSign_type() {
        return this.sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLepayOrderNo() {
        return this.lepayOrderNo;
    }

    public void setLepayOrderNo(String lepayOrderNo) {
        this.lepayOrderNo = lepayOrderNo;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_desc() {
        return product_desc;
    }

    public void setProduct_desc(String product_desc) {
        this.product_desc = product_desc;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getCall_back_url() {
        return call_back_url;
    }

    public void setCall_back_url(String call_back_url) {
        this.call_back_url = call_back_url;
    }

}
