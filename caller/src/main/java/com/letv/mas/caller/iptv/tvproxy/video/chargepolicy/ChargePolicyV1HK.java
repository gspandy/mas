package com.letv.mas.caller.iptv.tvproxy.video.chargepolicy;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.VideoMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.video.constants.VideoConstants;

public class ChargePolicyV1HK implements ChargePolicy {

    private VideoMysqlTable videoMysqlTable;

    public ChargePolicyV1HK(VideoMysqlTable videoMysqlTable) {
        this.videoMysqlTable = videoMysqlTable;
    }

    @Override
    public Integer getChargePolicy() {
        Integer chargeType = ChargeTypeConstants.chargePolicy.FREE;

        if (videoMysqlTable != null) {
            if (videoMysqlTable.getCategory() == VideoConstants.Category.TV
                    || videoMysqlTable.getCategory() == VideoConstants.Category.FILM) {//
                // 1.电影、电视剧的正片收费
                if (VideoConstants.VideoType.ZHENG_PIAN == videoMysqlTable.getVideo_type()) {
                    chargeType = ChargeTypeConstants.chargePolicy.CHARGE_BY_CONTENT;
                    if (videoMysqlTable.getCategory() == VideoConstants.Category.TV && videoMysqlTable.getPorder() != 1)// 电视剧非第一集试看
                    {
                        chargeType = ChargeTypeConstants.chargePolicy.CHARGE_SMALL_WINDOW;
                    }
                }
            } else if (videoMysqlTable.getCategory() == VideoConstants.Category.CARTOON) {// 2.动漫收费
                chargeType = ChargeTypeConstants.chargePolicy.CHARGE_BY_CONTENT;
                if (videoMysqlTable.getPorder() != 1)// 电视剧非第一集试看
                {
                    chargeType = ChargeTypeConstants.chargePolicy.CHARGE_SMALL_WINDOW;
                }
            }
        }

        return chargeType;
    }
}
