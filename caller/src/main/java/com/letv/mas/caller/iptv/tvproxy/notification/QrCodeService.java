package com.letv.mas.caller.iptv.tvproxy.notification;


import com.letv.mas.caller.iptv.tvproxy.common.service.BaseService;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CacheContentConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.LetvStreamCommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.LocaleConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.QrCodeMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.notification.Commodity;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.notification.model.dto.CommodityDto;
import com.letv.mas.caller.iptv.tvproxy.notification.model.dto.QrCodeDto;
import com.letv.mas.caller.iptv.tvproxy.recommendation.constants.RecommendationConstants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service(value = "v2.QrCodeService")
public class QrCodeService extends BaseService {

    private final static Logger log = LoggerFactory.getLogger(QrCodeService.class);
    private final static long QRCODE_UPDATE_INTERVAL = 600000L; // 10分钟
    private static long QRCODE_LASTUPDATE_TIME = 0L; // 上次更新时间
    private Lock qrCodeLock = new ReentrantLock(); // 用于定时更新
    private List<QrCodeMysqlTable> QRCODE_FULL_LIST;

    public QrCodeDto getQrCode(Integer channelid) {

        QrCodeDto code = new QrCodeDto();
        // QrCodeMysqlTable codeMysql =
        // this.facadeMysqlDao.getQrCodeMysqlDao().getQrCodeByChannelid(channelid);
        List<QrCodeMysqlTable> list = this.facadeMysqlDao.getQrCodeMysqlDao().getListByCid(channelid);
        QrCodeMysqlTable codeMysql = null;
        if (list != null && list.size() > 0) {
            codeMysql = list.get(0);
        }
        if (codeMysql != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            try {
                code.setBeginTime(sdf.parse(codeMysql.getBeginTime()).getTime());
                code.setEndTime(sdf.parse(codeMysql.getEndTime()).getTime());
            } catch (ParseException e) {
                log.error(e.getMessage(), e);
            }
            code.setDuration(Integer.valueOf(codeMysql.getDuration()));
            code.setImageUrl(codeMysql.getImageUrl());
            code.setInterval(Integer.valueOf(codeMysql.getIntervalue()));
            code.setIntervalue(Integer.valueOf(codeMysql.getIntervalue()));
            code.setQrCode(codeMysql.getQrCode());
            code.setRegion(codeMysql.getRegion());
            code.setTitle(codeMysql.getTitle().replace("\\n", "\n"));
            code.setType(codeMysql.getType());
        }
        return code;
    }

    public QrCodeDto getQrCodeByStream(String stream, Integer channelid, Long vid, Long pid, CommonParam commonParam) {

        QrCodeDto code = new QrCodeDto();
        List<QrCodeMysqlTable> list = null;
        String streamStr = "";
        if (stream != null && !stream.equals("")) {
            if (stream.trim().equals(LetvStreamCommonConstants.CODE_NAME_1080p)
                    || stream.trim().equals(LetvStreamCommonConstants.CODE_NAME_1080p3m)
                    || stream.trim().equals(LetvStreamCommonConstants.CODE_NAME_1080p6m)) {
                streamStr = "1080p";
            } else if (stream.trim().equals(LetvStreamCommonConstants.CODE_NAME_3d1080p)
                    || stream.trim().equals(LetvStreamCommonConstants.CODE_NAME_3d720p)
                    || stream.trim().equals(LetvStreamCommonConstants.CODE_NAME_3d1080p6M)) {
                streamStr = "3d";
            } else if (stream.trim().equals(LetvStreamCommonConstants.CODE_NAME_DOLBY_1000)
                    || stream.trim().equals(LetvStreamCommonConstants.CODE_NAME_DOLBY_1300)
                    || stream.trim().equals(LetvStreamCommonConstants.CODE_NAME_DOLBY_720p)
                    || stream.trim().equals(LetvStreamCommonConstants.CODE_NAME_DOLBY_1080p)) {
                streamStr = "db";
            } else if (stream.trim().equals(LetvStreamCommonConstants.CODE_NAME_4K)) {
                streamStr = "4k";
            } else {
                streamStr = stream;
            }
            list = this.getQrListByStream(streamStr);
        }
        if (list == null || list.size() == 0) {
            list = this.getQrListByCid(channelid);
        }
        /*
         * List<QrCodeMysqlTable> list =
         * this.facadeMysqlDao.getQrCodeMysqlDao().getListByStream(streamStr);
         * if (list == null || list.size() == 0) {
         * list =
         * this.facadeMysqlDao.getQrCodeMysqlDao().getListByCid(channelid);
         * }
         */
        QrCodeMysqlTable codeMysql = null;
        if (list != null && list.size() > 0) {
            codeMysql = list.get(0);
        }
        if (codeMysql != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                code.setBeginTime(sdf.parse(codeMysql.getBeginTime()).getTime());
                code.setEndTime(sdf.parse(codeMysql.getEndTime()).getTime());
            } catch (ParseException e) {
                log.error(e.getMessage(), e);
            }
            code.setDuration(Integer.valueOf(codeMysql.getDuration()));
            code.setImageUrl(codeMysql.getImageUrl());
            code.setInterval(Integer.valueOf(codeMysql.getIntervalue()));
            code.setIntervalue(Integer.valueOf(codeMysql.getIntervalue()));
            code.setQrCode(codeMysql.getQrCode());
            code.setRegion(codeMysql.getRegion());
            code.setStream(codeMysql.getStream());
            code.setTitle(codeMysql.getTitle().replace("\\n", "\n"));
            code.setType(codeMysql.getType());
            code.setDmIdentify(this.isInDanmuPids(RecommendationConstants.dmVids, vid.intValue()) ? 1 : 0);

        }

        // 广告营销列表
        List<QrCodeMysqlTable> qrList = this.getQrListByVid(vid);
        if (qrList == null || qrList.size() == 0) {
            qrList = this.getQrListByPid(pid);
        }
        if (qrList != null && qrList.size() > 0) {
            List<QrCode> qrcodeList = new ArrayList<QrCode>();
            code.setVid(String.valueOf(vid));
            code.setPid(String.valueOf(pid));
            for (QrCodeMysqlTable qrcode : qrList) {
                QrCode qcode = new QrCode();
                qcode.setCardImage(qrcode.getCardImage());
                qcode.setCommodityName(qrcode.getCommodityName());
                qcode.setCurrentPrice(qrcode.getCurrentPrice());
                qcode.setDetailImage(qrcode.getDetailImage());
                qcode.setListImage(qrcode.getListImage());
                qcode.setOriginalPrice(qrcode.getOriginalPrice());
                qcode.setSource(qrcode.getSource());
                qcode.setTriggerTime(qrcode.getTriggerTime());
                qrcodeList.add(qcode);
            }
            code.setQrList(qrcodeList);
        }
        // 边看边买
        code.setCommodityData(this.getCommodityData(vid, pid, commonParam));

        return code;
    }

    private CommodityDto getCommodityData(Long vid, Long pid, CommonParam commonParam) {
        String videoId = String.valueOf(vid);
        String albumId = String.valueOf(pid);
        CommodityDto data = new CommodityDto();
        // get commodity data from cache
        Map<String, List<Commodity>> maps = this.facadeCacheDao.getChannelCacheDao().getCmsCommodityData(videoId,
                albumId, commonParam);
        if (!CollectionUtils.isEmpty(maps)) {
            String videoKey = CacheContentConstants.COMMODITY_DATA_PREFIX + LocaleConstant.Langcode.ZH_CN + "_"
                    + videoId;
            List<Commodity> dataList = maps.get(videoKey);// match video first
            if (CollectionUtils.isEmpty(dataList)) {
                String albumKey = CacheContentConstants.COMMODITY_DATA_PREFIX + LocaleConstant.Langcode.ZH_CN + "_"
                        + albumId;
                // if video doesn't match then match album
                dataList = maps.get(albumKey);
            }
            if (!CollectionUtils.isEmpty(dataList)) {
                data.setCommodityList(dataList);
            }
        }

        return data;
    }

    /**
     * 根据码流查询qrcode
     * @param stream
     * @return
     */
    private List<QrCodeMysqlTable> getQrListByStream(String stream) {
        List<QrCodeMysqlTable> list = new LinkedList<QrCodeMysqlTable>();
        List<QrCodeMysqlTable> fullList = this.getQrCodeFullList();
        if (fullList != null) {
            for (QrCodeMysqlTable qrcode : fullList) {
                if (StringUtils.isBlank(stream) && StringUtils.isBlank(qrcode.getStream())) {
                    list.add(qrcode);
                } else if (StringUtils.isNotBlank(stream) && stream.equals(qrcode.getStream())) {
                    list.add(qrcode);
                }
            }
        }
        return list;
    }

    /**
     * 根据频道id查询qrcode
     * @param cid
     * @return
     */
    private List<QrCodeMysqlTable> getQrListByCid(Integer cid) {
        List<QrCodeMysqlTable> list = new LinkedList<QrCodeMysqlTable>();
        List<QrCodeMysqlTable> fullList = this.getQrCodeFullList();
        if (fullList != null) {
            for (QrCodeMysqlTable qrcode : fullList) {
                if (cid == null && qrcode.getChannelid() == null) {
                    list.add(qrcode);
                } else if (cid != null && qrcode.getChannelid() != null
                        && cid.intValue() == qrcode.getChannelid().intValue()) {
                    list.add(qrcode);
                }
            }
        }
        return list;
    }

    /**
     * 获取qrcode全量配置信息
     * @return
     */
    private List<QrCodeMysqlTable> getQrCodeFullList() {
        if (this.QRCODE_FULL_LIST == null
                || ((System.currentTimeMillis() - QRCODE_LASTUPDATE_TIME) > QRCODE_UPDATE_INTERVAL)) {
            // 更新list
            if (this.qrCodeLock.tryLock()) {
                try {
                    List<QrCodeMysqlTable> fullList = this.facadeMysqlDao.getQrCodeMysqlDao().getList();
                    this.QRCODE_FULL_LIST = fullList;
                    QRCODE_LASTUPDATE_TIME = System.currentTimeMillis();
                } catch (Exception e) {
                    log.error("update QrCodeMytable List error", e);
                } finally {
                    this.qrCodeLock.unlock();
                }
            }
        }

        return this.QRCODE_FULL_LIST;
    }

    /**
     * 根据Vid查询qrcode
     * @param stream
     * @return
     */
    private List<QrCodeMysqlTable> getQrListByVid(Long vid) {
        List<QrCodeMysqlTable> list = new LinkedList<QrCodeMysqlTable>();
        List<QrCodeMysqlTable> fullList = this.getQrCodeFullList();
        if (fullList != null) {
            for (QrCodeMysqlTable qrcode : fullList) {
                if (vid == null && qrcode.getVid() == null) {
                    list.add(qrcode);
                } else if (vid != null && qrcode.getVid() != null && vid.intValue() == qrcode.getVid().intValue()) {
                    list.add(qrcode);
                }
            }
        }
        return list;
    }

    /**
     * 根据Pid查询qrcode
     * @param stream
     * @return
     */
    private List<QrCodeMysqlTable> getQrListByPid(Long pid) {
        List<QrCodeMysqlTable> list = new LinkedList<QrCodeMysqlTable>();
        List<QrCodeMysqlTable> fullList = this.getQrCodeFullList();
        if (fullList != null) {
            for (QrCodeMysqlTable qrcode : fullList) {
                if (pid == null && qrcode.getPid() == null) {
                    list.add(qrcode);
                } else if (pid != null && qrcode.getPid() != null && pid.intValue() == qrcode.getPid().intValue()) {
                    list.add(qrcode);
                }
            }
        }
        return list;
    }

    private boolean isInDanmuPids(String[] pids, Integer albumId) {

        if (albumId == null || pids == null) {
            return false;
        }
        for (String s : pids) {
            if (s.equals(albumId.toString())) {
                return true;
            }
        }
        return false;
    }
}
