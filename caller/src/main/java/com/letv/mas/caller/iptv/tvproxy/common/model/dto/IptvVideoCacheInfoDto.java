package com.letv.mas.caller.iptv.tvproxy.common.model.dto;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class IptvVideoCacheInfoDto implements Serializable {

    private static final long serialVersionUID = 4592125760378224204L;
    private Long id;// 视频ID
    private Integer category;// 电视剧 电影
    private Long pid;// 专辑ID
    private Integer video_type;// 视频类型
    private String episode;// 在动漫频道，可能有2.5集， 也有可能一个视频文件，包含两集
    private String name_cn;// 中文名称
    private String sub_title;// 副标题
    private Integer duration;// 时长，单位-秒
    private String pic;// 图片地址
    private String guest;// 嘉宾
    private String singer;// 歌手
    private Integer porder;// 在专辑顺序
    private String trans_code_prefix;// 转码视频截图前缀
    private String payPlatform;// 付费平台

    public IptvVideoCacheInfoDto() {
    }

    public IptvVideoCacheInfoDto(Long id, Integer category, Long pid, Integer video_type, String episode,
            String name_cn, String sub_title, Integer duration, String pic, String guest, String singer,
            Integer porder, String trans_code_prefix, String payPlatform) {
        super();
        this.id = id;
        this.category = category;
        this.pid = pid;
        this.video_type = video_type;
        this.episode = episode;
        this.name_cn = name_cn;
        this.sub_title = sub_title;
        this.duration = duration;
        // 读取400*300的图片
        this.pic = this.getPic(pic, 400, 300);
        this.guest = guest;
        this.singer = singer;
        this.porder = porder;
        this.trans_code_prefix = trans_code_prefix;
        this.payPlatform = payPlatform;
    }

    public String getPic(String pic, Integer width, Integer hight) {
        if (pic == null && this.trans_code_prefix == null) {
            return "";
        }
        String img = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, String> map = objectMapper.readValue(pic, new TypeReference<Map<String, String>>() {
            });
            if (map != null) {
                img = map.get(width + "*" + hight);
            }
        } catch (Exception e) {

        }
        if (this.trans_code_prefix != null && this.trans_code_prefix.trim().length() > 0 && img == null) {
            img = this.trans_code_prefix + "/thumb/2_" + width + "_" + hight + ".jpg";
        }
        return img == null ? "" : img;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCategory() {
        return this.category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Long getPid() {
        return this.pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Integer getVideo_type() {
        return this.video_type;
    }

    public void setVideo_type(Integer video_type) {
        this.video_type = video_type;
    }

    public String getEpisode() {
        return this.episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public String getName_cn() {
        return this.name_cn;
    }

    public void setName_cn(String name_cn) {
        this.name_cn = name_cn;
    }

    public String getSub_title() {
        return this.sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getPic() {
        return this.pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getGuest() {
        return this.guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public String getSinger() {
        return this.singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public Integer getPorder() {
        return this.porder;
    }

    public void setPorder(Integer porder) {
        this.porder = porder;
    }

    public String getTrans_code_prefix() {
        return this.trans_code_prefix;
    }

    public void setTrans_code_prefix(String trans_code_prefix) {
        this.trans_code_prefix = trans_code_prefix;
    }

    public String getPayPlatform() {
        return payPlatform;
    }

    public void setPayPlatform(String payPlatform) {
        this.payPlatform = payPlatform;
    }
}
