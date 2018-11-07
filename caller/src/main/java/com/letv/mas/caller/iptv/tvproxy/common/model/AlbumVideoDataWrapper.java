package com.letv.mas.caller.iptv.tvproxy.common.model;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AlbumMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.VideoMysqlTable;

import java.io.Serializable;

/**
 * Data wrapper to be stored in the cache
 */
public class AlbumVideoDataWrapper implements Serializable {
    private static final long serialVersionUID = -5339439969944351364L;

    private static final int STATUS_INIT = -1;
    public static final int STATUS_OK = 0;
    public static final int STATUS_BLOCKED = 1;
    public static final int STATUS_NOT_EXIST = 2;
    public static final int STATUS_NO_COPYRIGHT = 3;

    private int status = STATUS_INIT;
    private VideoMysqlTable video;
    private AlbumMysqlTable album;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public VideoMysqlTable getVideo() {
        return video;
    }

    public void setVideo(VideoMysqlTable video) {
        this.video = video;
    }

    public AlbumMysqlTable getAlbum() {
        return album;
    }

    public void setAlbum(AlbumMysqlTable album) {
        this.album = album;
    }
}
