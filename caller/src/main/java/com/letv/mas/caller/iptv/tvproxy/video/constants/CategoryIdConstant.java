package com.letv.mas.caller.iptv.tvproxy.video.constants;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.MmsTpConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 2016-03-1，媒资频道关系常量类，媒资频道定义发生过变化，这里保留对应关系，下一版废除
 * @author KevinYi
 */
public final class CategoryIdConstant {

    /**
     * 一级分类对应关系，根据old_id查询新id；2016-03-17重构，计划去除转换老频道到新频道的逻辑，这里暂时保留
     */
    private static Map<Integer, Integer> CATEGRORY_OLD2NEW_MAP = new HashMap<Integer, Integer>();//

    /**
     * 一级分类对应关系，根据新id查询old_id；2016-03-17重构，计划去除转换老频道到新频道的逻辑，这里暂时保留
     */
    public static Map<Integer, Integer> CATEGRORY_NEW2OLD_MAP = new HashMap<Integer, Integer>();

    static {
        CATEGRORY_OLD2NEW_MAP.put(4, MmsTpConstant.MMS_CATEGARY_FILM);// 电影
        CATEGRORY_OLD2NEW_MAP.put(5, MmsTpConstant.MMS_CATEGARY_TV);// 电视剧
        CATEGRORY_OLD2NEW_MAP.put(6, MmsTpConstant.MMS_CATEGARY_CARTOON);// 动漫
        CATEGRORY_OLD2NEW_MAP.put(7, MmsTpConstant.MMS_CATEGARY_OTHER);// 其他
        CATEGRORY_OLD2NEW_MAP.put(66, MmsTpConstant.MMS_CATEGARY_MUSIC);// 音乐
        CATEGRORY_OLD2NEW_MAP.put(78, MmsTpConstant.MMS_CATEGARY_VARIETY);// 综艺
        CATEGRORY_OLD2NEW_MAP.put(86, MmsTpConstant.MMS_CATEGARY_ENTERTAINMENT);// 娱乐
        CATEGRORY_OLD2NEW_MAP.put(92, MmsTpConstant.MMS_CATEGARY_OPEN_CLASS);// 公开课
        CATEGRORY_OLD2NEW_MAP.put(111, MmsTpConstant.MMS_CATEGARY_DFILM);// 纪录片
        CATEGRORY_OLD2NEW_MAP.put(164, MmsTpConstant.MMS_CATEGARY_LETV_MAKE);// 乐视制造
        CATEGRORY_OLD2NEW_MAP.put(169, MmsTpConstant.MMS_CATEGARY_CAR);// 汽车
        CATEGRORY_OLD2NEW_MAP.put(186, MmsTpConstant.MMS_CATEGARY_FASHION);// 风尚
        CATEGRORY_OLD2NEW_MAP.put(221, MmsTpConstant.MMS_CATEGARY_SPORT);// 体育
        CATEGRORY_OLD2NEW_MAP.put(298, MmsTpConstant.MMS_CATEGARY_FINANCE);// 财经频道
        CATEGRORY_OLD2NEW_MAP.put(307, MmsTpConstant.MMS_CATEGARY_TRAVEL);// 旅游频道

        CATEGRORY_NEW2OLD_MAP.put(MmsTpConstant.MMS_CATEGARY_FILM, 4);// 电影
        CATEGRORY_NEW2OLD_MAP.put(MmsTpConstant.MMS_CATEGARY_TV, 5);// 电视剧
        CATEGRORY_NEW2OLD_MAP.put(MmsTpConstant.MMS_CATEGARY_ENTERTAINMENT, 86);// 娱乐
        CATEGRORY_NEW2OLD_MAP.put(MmsTpConstant.MMS_CATEGARY_SPORT, 221);// 体育
        CATEGRORY_NEW2OLD_MAP.put(MmsTpConstant.MMS_CATEGARY_CARTOON, 6);// 动漫
        CATEGRORY_NEW2OLD_MAP.put(6, null);// 资讯
        CATEGRORY_NEW2OLD_MAP.put(7, null);// 原创
        CATEGRORY_NEW2OLD_MAP.put(MmsTpConstant.MMS_CATEGARY_OTHER, 7);// 其他
        CATEGRORY_NEW2OLD_MAP.put(MmsTpConstant.MMS_CATEGARY_MUSIC, 66);// 音乐
        CATEGRORY_NEW2OLD_MAP.put(MmsTpConstant.MMS_CATEGARY_COMEDY, null);// 搞笑
        CATEGRORY_NEW2OLD_MAP.put(MmsTpConstant.MMS_CATEGARY_VARIETY, 78);// 综艺
        CATEGRORY_NEW2OLD_MAP.put(12, null);// 科教
        CATEGRORY_NEW2OLD_MAP.put(13, null);// 生活
        CATEGRORY_NEW2OLD_MAP.put(MmsTpConstant.MMS_CATEGARY_CAR, 169);// 汽车
        CATEGRORY_NEW2OLD_MAP.put(15, null);// 电视节目
        CATEGRORY_NEW2OLD_MAP.put(MmsTpConstant.MMS_CATEGARY_DFILM, 111);// 纪录片
        CATEGRORY_NEW2OLD_MAP.put(MmsTpConstant.MMS_CATEGARY_OPEN_CLASS, 92);// 公开课
        CATEGRORY_NEW2OLD_MAP.put(MmsTpConstant.MMS_CATEGARY_LETV_MAKE, 164);// 乐视制造
        CATEGRORY_NEW2OLD_MAP.put(MmsTpConstant.MMS_CATEGARY_FASHION, 186);// 风尚
        CATEGRORY_NEW2OLD_MAP.put(MmsTpConstant.MMS_CATEGARY_FINANCE, 298);// 财经频道
        CATEGRORY_NEW2OLD_MAP.put(MmsTpConstant.MMS_CATEGARY_TRAVEL, 307);// 旅游频道
        CATEGRORY_NEW2OLD_MAP.put(MmsTpConstant.MMS_CATEGARY_HOTSPOT, 30);// 热点频道
        CATEGRORY_NEW2OLD_MAP.put(MmsTpConstant.MMS_CATEGARY_QINZI, 34);// 亲子频道
    }

    public static Integer getNewCategory(Integer category) {
        Integer result = CATEGRORY_OLD2NEW_MAP.get(category);
        return result == null ? category : result;
    }

    public static Integer getOldCategory(Integer category) {
        Integer result = CATEGRORY_NEW2OLD_MAP.get(category);
        return result == null ? category : result;
    }
}
