package com.letv.mas.caller.iptv.tvproxy.video.chargepolicy;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AlbumMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.VideoMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.video.constants.VideoConstants;

public class ChargePolicyCIBN implements ChargePolicy {

    private VideoMysqlTable videoMysqlTable;

    private AlbumMysqlTable albumMysqlTable;

    public ChargePolicyCIBN(AlbumMysqlTable albumMysqlTable, VideoMysqlTable videoMysqlTable) {
        this.albumMysqlTable = albumMysqlTable;
        this.videoMysqlTable = videoMysqlTable;
    }

    @Override
    public Integer getChargePolicy() {
        Integer chargeType = ChargeTypeConstants.chargePolicy.FREE;

        if (albumMysqlTable != null) {
            if (videoMysqlTable != null) {
                if (videoMysqlTable.getCategory() == VideoConstants.Category.TV
                        || videoMysqlTable.getCategory() == VideoConstants.Category.FILM) {// 1.电影、电视剧的正片收费
                    if (VideoConstants.VideoType.ZHENG_PIAN == videoMysqlTable.getVideo_type()) {
                        chargeType = ChargeTypeConstants.chargePolicy.CHARGE_BY_CONTENT;
                        if (videoMysqlTable.getCategory() == VideoConstants.Category.TV
                                && videoMysqlTable.getPorder() != 1)// 电视剧非第一集试看
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
        } else { // 单视频码流付费
            chargeType = ChargeTypeConstants.chargePolicy.CHARGE_BY_STREAM;
        }

        return chargeType;
    }
}
