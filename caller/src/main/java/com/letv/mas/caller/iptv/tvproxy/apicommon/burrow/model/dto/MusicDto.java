package com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

import java.io.Serializable;

public class MusicDto {

    public static class MusicSingleBurrowDto extends BaseDto implements Serializable {
        private static final long serialVersionUID = 2821261445239889191L;
        private String artist;// 歌手名称
        private String song;// 歌曲名称
        private long songId;// 歌曲ID

        public String getArtist() {
            return artist;
        }

        public void setArtist(String artist) {
            this.artist = artist;
        }

        public String getSong() {
            return song;
        }

        public void setSong(String song) {
            this.song = song;
        }

        public long getSongId() {
            return songId;
        }

        public void setSongId(long songId) {
            this.songId = songId;
        }

    }

    public static class MusicAlbumBurrowDto extends BaseDto implements Serializable {
        private static final long serialVersionUID = 5319224006917110769L;
        private String albumId;// 音乐专辑ID

        public String getAlbumId() {
            return albumId;
        }

        public void setAlbumId(String albumId) {
            this.albumId = albumId;
        }

    }
}
