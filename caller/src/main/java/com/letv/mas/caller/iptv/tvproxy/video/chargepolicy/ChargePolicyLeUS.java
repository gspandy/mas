package com.letv.mas.caller.iptv.tvproxy.video.chargepolicy;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AlbumMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.VideoMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.video.constants.VideoConstants;

/**
 * 美国Le.com收费策略，按购买会员付费
 * @author KevinYi
 */
public class ChargePolicyLeUS implements ChargePolicy {

    private VideoMysqlTable videoMysqlTable;

    private AlbumMysqlTable albumMysqlTable;

    public ChargePolicyLeUS(VideoMysqlTable videoMysqlTable, AlbumMysqlTable albumMysqlTable) {
        this.videoMysqlTable = videoMysqlTable;
        this.albumMysqlTable = albumMysqlTable;
    }

    @Override
    public Integer getChargePolicy() {
        if ((albumMysqlTable == null)
                || (albumMysqlTable.getPay_platform() == null)
                || (albumMysqlTable.getPay_platform() != null && !(albumMysqlTable.getPay_platform().indexOf(
                        CommonConstants.TV_PAY_CODE) > -1))) {
            return ChargeTypeConstants.chargePolicy.FREE;
        } else {
            if (videoMysqlTable != null
                    && videoMysqlTable.getCategory() != null
                    && (videoMysqlTable.getCategory() == VideoConstants.Category.FILM
                            || videoMysqlTable.getCategory() == VideoConstants.Category.TV
                            || videoMysqlTable.getCategory() == VideoConstants.Category.VARIETY
                            || videoMysqlTable.getCategory() == VideoConstants.Category.CARTOON
                            || videoMysqlTable.getCategory() == VideoConstants.Category.DFILM || videoMysqlTable
                            .getCategory() == VideoConstants.Category.TEACH)) {
                if (videoMysqlTable.getVideo_type() != null
                        && (VideoConstants.VideoType.ZHENG_PIAN == videoMysqlTable.getVideo_type())) {
                    return ChargeTypeConstants.chargePolicy.CHARGE_BY_VIP;
                } else {
                    return ChargeTypeConstants.chargePolicy.FREE;
                }
            } else {
                return ChargeTypeConstants.chargePolicy.CHARGE_BY_VIP;
            }
        }

    }
}
