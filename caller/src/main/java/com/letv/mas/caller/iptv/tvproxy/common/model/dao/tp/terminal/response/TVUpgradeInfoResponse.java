package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.terminal.response;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TVUpgradeInfoResponse {
    private String code;// 状态码A000000:成功 ,A000001:参数无效,A000004:没有数据,E000000:服务异常
    private String message;
    private String timestamp;// 时间戳
    private CurrentVersionDto currentVersion;
    private NewVersionDto newVersion;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public CurrentVersionDto getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(CurrentVersionDto currentVersion) {
        this.currentVersion = currentVersion;
    }

    public NewVersionDto getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(NewVersionDto newVersion) {
        this.newVersion = newVersion;
    }

    @XmlRootElement
    public class CurrentVersionDto {
        private String currentVersion_Code;// 当前版本的唯一标识，最好是数字类型"1234567"
        private String currentVersion_Name;// 当前版本的版本号 “1.2.5”

        public String getCurrentVersion_Code() {
            return currentVersion_Code;
        }

        public void setCurrentVersion_Code(String currentVersion_Code) {
            this.currentVersion_Code = currentVersion_Code;
        }

        public String getCurrentVersion_Name() {
            return currentVersion_Name;
        }

        public void setCurrentVersion_Name(String currentVersion_Name) {
            this.currentVersion_Name = currentVersion_Name;
        }
    }

    @XmlRootElement
    public class NewVersionDto {
        private String upgrade;// 是否需要升级:1-有，0-无
        private String version_name;// 版本名称 1.1.3
        private String version_code;// 版本号 1.1.3
        private String upurl;// 升级地址
        private String desc;// 描述
        private String uptype;// 升级类型：1-强制，2-推荐
        private String rom_minimum;// 版本最低room版本
        private String timestamp;// 接口访问时间戳

        public String getUpgrade() {
            return upgrade;
        }

        public void setUpgrade(String upgrade) {
            this.upgrade = upgrade;
        }

        public String getVersion_name() {
            return version_name;
        }

        public void setVersion_name(String version_name) {
            this.version_name = version_name;
        }

        public String getVersion_code() {
            return version_code;
        }

        public void setVersion_code(String version_code) {
            this.version_code = version_code;
        }

        public String getUpurl() {
            return upurl;
        }

        public void setUpurl(String upurl) {
            this.upurl = upurl;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getUptype() {
            return uptype;
        }

        public void setUptype(String uptype) {
            this.uptype = uptype;
        }

        public String getRom_minimum() {
            return rom_minimum;
        }

        public void setRom_minimum(String rom_minimum) {
            this.rom_minimum = rom_minimum;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }

}
