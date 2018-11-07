package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response;

public class MmsFile {
    private Integer vtype;// 码流
    private String storePath;// 短地址
    private String mainUrl;// 调度地址
    private String backUrl0;// 备用调度地址1（域名备份调度）
    private String backUrl1;// 备用调度地址2（双线出口的IP调度）
    private String backUrl2;// 备用调度地址3（域名备份调度）
    private String md5;// 文件MD5
    private String storeStatus;// 视频分发状态
    private String gfmt;// 视频容器格式 example:flv
    private Long gsize;// 文件大小byte
    private Long gdur;// 总时长s
    private String gbr;// 总码率kbps
    private String vfmt;// video格式 example:AVC
    private String vbr;// video码率kbps
    private String vwidth;// video宽
    private String vheight;// video高
    private String vfps;// video帧率
    private String afmt;// audio格式
    private String abr;// audio码率kbps
    private String achannel;// audio声道数
    private String asample;// audio采样数Hz
    private String aresolution;// audio分辨率比特
    private Object audioTracks;// 音轨返回值,key：（语种_音质码率），value：音轨id

    public Integer getVtype() {
        return this.vtype;
    }

    public void setVtype(Integer vtype) {
        this.vtype = vtype;
    }

    public String getStorePath() {
        return this.storePath;
    }

    public void setStorePath(String storePath) {
        this.storePath = storePath;
    }

    public String getMainUrl() {
        return this.mainUrl;
    }

    public void setMainUrl(String mainUrl) {
        this.mainUrl = mainUrl;
    }

    public String getBackUrl0() {
        return this.backUrl0;
    }

    public void setBackUrl0(String backUrl0) {
        this.backUrl0 = backUrl0;
    }

    public String getBackUrl1() {
        return this.backUrl1;
    }

    public void setBackUrl1(String backUrl1) {
        this.backUrl1 = backUrl1;
    }

    public String getBackUrl2() {
        return this.backUrl2;
    }

    public void setBackUrl2(String backUrl2) {
        this.backUrl2 = backUrl2;
    }

    public String getMd5() {
        return this.md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getStoreStatus() {
        return this.storeStatus;
    }

    public void setStoreStatus(String storeStatus) {
        this.storeStatus = storeStatus;
    }

    public String getGfmt() {
        return this.gfmt;
    }

    public void setGfmt(String gfmt) {
        this.gfmt = gfmt;
    }

    public Long getGsize() {
        return this.gsize;
    }

    public void setGsize(Long gsize) {
        this.gsize = gsize;
    }

    public Long getGdur() {
        return this.gdur;
    }

    public void setGdur(Long gdur) {
        this.gdur = gdur;
    }

    public String getGbr() {
        return this.gbr;
    }

    public void setGbr(String gbr) {
        this.gbr = gbr;
    }

    public String getVfmt() {
        return this.vfmt;
    }

    public void setVfmt(String vfmt) {
        this.vfmt = vfmt;
    }

    public String getVbr() {
        return this.vbr;
    }

    public void setVbr(String vbr) {
        this.vbr = vbr;
    }

    public String getVwidth() {
        return this.vwidth;
    }

    public void setVwidth(String vwidth) {
        this.vwidth = vwidth;
    }

    public String getVheight() {
        return this.vheight;
    }

    public void setVheight(String vheight) {
        this.vheight = vheight;
    }

    public String getVfps() {
        return this.vfps;
    }

    public void setVfps(String vfps) {
        this.vfps = vfps;
    }

    public String getAfmt() {
        return this.afmt;
    }

    public void setAfmt(String afmt) {
        this.afmt = afmt;
    }

    public String getAbr() {
        return this.abr;
    }

    public void setAbr(String abr) {
        this.abr = abr;
    }

    public String getAchannel() {
        return this.achannel;
    }

    public void setAchannel(String achannel) {
        this.achannel = achannel;
    }

    public String getAsample() {
        return this.asample;
    }

    public void setAsample(String asample) {
        this.asample = asample;
    }

    public String getAresolution() {
        return this.aresolution;
    }

    public void setAresolution(String aresolution) {
        this.aresolution = aresolution;
    }

    public Object getAudioTracks() {
        return this.audioTracks;
    }

    public void setAudioTracks(Object audioTracks) {
        this.audioTracks = audioTracks;
    }

}
