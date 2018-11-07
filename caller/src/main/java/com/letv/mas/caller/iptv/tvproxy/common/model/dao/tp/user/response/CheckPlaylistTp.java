package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response;

/**
 * 检查剧集追剧,收藏状态返回参数
 * @author dunhongqin
 */
public class CheckPlaylistTp {

    private String code;

    private PlayList data;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public PlayList getData() {
        return this.data;
    }

    public void setData(PlayList data) {
        this.data = data;
    }

    public static class PlayList {
        /**
         * 
         */
        private static final long serialVersionUID = 6169359895669658063L;
        private Integer id;
        private Integer fromtype;

        public Integer getId() {
            return this.id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getFromtype() {
            return this.fromtype;
        }

        public void setFromtype(Integer fromtype) {
            this.fromtype = fromtype;
        }
    }
}
