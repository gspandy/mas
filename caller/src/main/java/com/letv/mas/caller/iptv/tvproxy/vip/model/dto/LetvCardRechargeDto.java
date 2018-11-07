package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageUtils;
import com.letv.mas.caller.iptv.tvproxy.vip.VipConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author <a href="mailto:lihl@letv.com">Lihl</a>
 */
@XmlRootElement
public class LetvCardRechargeDto extends BaseDto {
    private static Logger log = LoggerFactory.getLogger(LetvCardRechargeDto.class);
    /**
     * 充值结果状态码，0--失败，1--成功
     */
    private Integer status;
    private String description;
    private int oldBalance;
    private int newBalance;
    private int rechargeLetvPoint;
    private String orderCode;

    /**
     * 乐卡类型，1--充值码，2--兑换码，目前以充值额为判断，大于0为充值码，否则为兑换码
     */
    private Integer cardType;

    /**
     * 乐卡名称，充值卡类型统称：全屏影视会员乐卡，兑换卡统称：超级电视服务兑换卡
     */
    private String cardName;

    /**
     * 乐卡校验错误码
     */
    private String errorcode;

    /**
     * 返回的消息
     */
    private String msg;

    /**
     * 会员有效时间，格式为"yyyy-MM-dd"
     */
    private String validDate;

    /**
     * 当前乐点余额
     */
    private Integer letvPoint;

    /**
     * 以当前乐点余额可购买的最贵服务包套餐中文名称
     */
    private String packageName;

    public LetvCardRechargeDto() {
    }

    public LetvCardRechargeDto(Integer status, String description) {
        this.status = status;
        this.description = description;
    }

    public LetvCardRechargeDto(Integer status, int oldBalance, int newBalance) {
        this.status = status;
        this.oldBalance = oldBalance;
        this.newBalance = newBalance;
    }

    public LetvCardRechargeDto(String[] fields, String orderCode) {
        this.status = 1;
        this.oldBalance = Integer.valueOf(fields[1]);
        this.newBalance = Integer.valueOf(fields[2]);
        this.rechargeLetvPoint = this.newBalance - this.oldBalance;
        this.orderCode = orderCode;
    }

    public static LetvCardRechargeDto asLetvCardRechargeResponse(String respString, String orderCode) {
        LetvCardRechargeDto response = null;

        // remove all end of line characters
        respString = respString.replaceAll("\\r|\\n", "");

        String[] fields = respString.split("\\|");

        if (fields.length == 2 && VipTpConstant.PAYMENT_STATUS_FAILURE.equals(Boolean.valueOf(fields[0]))) {// 失败
            // failure
            response = new LetvCardRechargeDto(0, fields[1]);
        } else {
            // success
            response = new LetvCardRechargeDto(fields, orderCode);
        }

        log.debug("returned LetvCardRechargeResponse = {}", response);

        return response;
    }

    public static LetvCardRechargeDto asLetvCardRechargeWidthCardSecretResponse(String respString, String orderCode) {
        LetvCardRechargeDto response = null;

        // remove all end of line characters
        respString = respString.replaceAll("\\r|\\n", "");

        String[] fields = respString.split("\\|");

        if (fields.length == 2 && VipTpConstant.PAYMENT_STATUS_FAILURE.equals(Boolean.valueOf(fields[0]))) {// 失败
            // failure
            response = new LetvCardRechargeDto(0, fields[1]);
        } else {
            // success
            response = new LetvCardRechargeDto(fields, orderCode);
        }

        log.debug("returned LetvCardRechargeResponse = {}", response);

        return response;
    }

    /**
     * @return the status
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return the oldBalance
     */
    public int getOldBalance() {
        return this.oldBalance;
    }

    /**
     * @param oldBalance
     *            the oldBalance to set
     */
    public void setOldBalance(int oldBalance) {
        this.oldBalance = oldBalance;
    }

    /**
     * @return the newBalance
     */
    public int getNewBalance() {
        return this.newBalance;
    }

    /**
     * @param newBalance
     *            the newBalance to set
     */
    public void setNewBalance(int newBalance) {
        this.newBalance = newBalance;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the rechargeLetvPoint
     */
    public int getRechargeLetvPoint() {
        return this.rechargeLetvPoint;
    }

    /**
     * @param rechargeLetvPoint
     *            the rechargeLetvPoint to set
     */
    public void setRechargeLetvPoint(int rechargeLetvPoint) {
        this.rechargeLetvPoint = rechargeLetvPoint;
    }

    /**
     * @return the orderCode
     */
    public String getOrderCode() {
        return this.orderCode;
    }

    /**
     * @param orderCode
     *            the orderCode to set
     */
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getCardType() {
        return this.cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
        if (cardType != null && cardType != 1) {
            this.cardName = MessageUtils.getMessage(VipConstants.LETV_CARD_NAME_2);
        } else {
            this.cardName = MessageUtils.getMessage(VipConstants.LETV_CARD_NAME_1);
        }
    }

    public String getCardName() {
        return this.cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public static Logger getLog() {
        return log;
    }

    public static void setLog(Logger log) {
        LetvCardRechargeDto.log = log;
    }

    public String getErrorcode() {
        return this.errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getValidDate() {
        return this.validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public Integer getLetvPoint() {
        return this.letvPoint;
    }

    public void setLetvPoint(Integer letvPoint) {
        this.letvPoint = letvPoint;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    /*
     * public static LetvCardRechargeResponse
     * asLetvCardRechargeResponse(RechargeWidthCardSecretResponse
     * responseTmpCardSecretResponse,
     * String orderCode2) {
     * String[] fields=new String[3];
     * LetvCardRechargeResponse response = null;
     * if(responseTmpCardSecretResponse!=null){
     * Integer newB=responseTmpCardSecretResponse.getBalance();
     * Integer addB=responseTmpCardSecretResponse.getCharge_ledian();
     * if(newB!=null&&addB!=null){
     * fields[1]=(newB-addB)+"";
     * fields[2]=newB+"";
     * }
     * }
     * if (!LetvPaymentError.PAYMENT_CENTER_SUCCESS_LEKA_RECHARGE_0000.equals(
     * responseTmpCardSecretResponse.getResponse())) {//失败
     * // failure
     * response = new
     * LetvCardRechargeResponse(LetvPaymentCommon.PAYMENT_STATUS_FAILURE
     * ,responseTmpCardSecretResponse.getResponse());
     * } else {
     * // success
     * response = new LetvCardRechargeResponse(fields,orderCode2);
     * }
     * log.debug("returned LetvCardRechargeResponse = {}", response);
     * return response;
     * }
     */
}
