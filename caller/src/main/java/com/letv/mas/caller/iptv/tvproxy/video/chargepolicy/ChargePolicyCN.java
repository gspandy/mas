package com.letv.mas.caller.iptv.tvproxy.video.chargepolicy;

import com.letv.mas.caller.iptv.tvproxy.common.constant.LetvStreamCommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AlbumMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.VideoMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.TerminalUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.VideoCommonUtil;

public class ChargePolicyCN implements ChargePolicy {

    private String playStream;

    private AlbumMysqlTable albumMysqlTable;

    private VideoMysqlTable videoMysqlTable;

    private Boolean positive;

    private Integer model;

    private CommonParam commonParam;

    public ChargePolicyCN(String playStream, AlbumMysqlTable albumMysqlTable, Boolean positive, Integer model,
            CommonParam commonParam) {
        this.playStream = playStream;
        this.albumMysqlTable = albumMysqlTable;
        this.positive = positive;
        this.model = model;
        this.commonParam = commonParam;
    }

    public ChargePolicyCN(String playStream, AlbumMysqlTable albumMysqlTable, VideoMysqlTable videoMysqlTable,
            Integer model, CommonParam commonParam) {
        this.playStream = playStream;
        this.albumMysqlTable = albumMysqlTable;
        this.videoMysqlTable = videoMysqlTable;
        this.positive = (1 == videoMysqlTable.getVideo_attr());
        this.model = model;
        this.commonParam = commonParam;
    }

    @Override
    public Integer getChargePolicy() {
        Integer chargeType = ChargeTypeConstants.chargePolicy.FREE;

        if (this.albumMysqlTable != null) {
            if (this.positive) {// 1.收费专辑下所有正片收费
                if (null != this.videoMysqlTable) {
                    if (VideoCommonUtil.isCharge(this.videoMysqlTable.getPay_platform(),
                            this.videoMysqlTable.getPlay_platform(), this.commonParam.getP_devType())) {
                        chargeType = ChargeTypeConstants.chargePolicy.CHARGE_YUAN_XIAN;
                    }
                } else if (this.albumMysqlTable.isPay(this.commonParam.getP_devType())) {
                    chargeType = ChargeTypeConstants.chargePolicy.CHARGE_YUAN_XIAN;
                }
            } else if (this.model == 1 && "542015".equals(this.albumMysqlTable.getSub_category())) {
                // 儿童模式进来播放教育频道下幼儿视频，不勾付费即可观看，不走码流收费
                return chargeType;
            } else if ("350".equalsIgnoreCase(this.playStream)) {// 暂时处理350收费
                chargeType = ChargeTypeConstants.chargePolicy.CHARGE_BY_STREAM;
            } else {// 2.收费码流都收费
                if (TerminalUtil.isLetvUs(commonParam)) {// 美国版，取美国的付费码流列表
                    if (LetvStreamCommonConstants.CHARGE_STREAM_SET_US.contains(this.playStream)) {
                        chargeType = ChargeTypeConstants.chargePolicy.CHARGE_BY_STREAM;
                    }
                } else if (LetvStreamCommonConstants.CHARGE_STREAM_SET.contains(this.playStream)) {
                    chargeType = ChargeTypeConstants.chargePolicy.CHARGE_BY_STREAM;
                }
            }
        } else { // 单视频码流付费
            chargeType = ChargeTypeConstants.chargePolicy.CHARGE_BY_STREAM;
        }

        return chargeType;
    }

}
