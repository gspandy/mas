package com.letv.mas.caller.iptv.tvproxy.music;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.dto.PageResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MusicController {

    @Autowired
    MusicService musicService;

    /**
     * 子频道列表
     * @return
     */
    @RequestMapping("/music/nav/list")
    public PageResponse<MusicNav> channel_list(@RequestParam(value = "fid", defaultValue = "0") Long fid,
                                               @RequestParam(value = "vnum", required = false, defaultValue = "1.0") Float vnum,
                                               @RequestParam(value = "broadcastId", required = false) Integer broadcastId) {
        return musicService.getMusicNavlList(fid, broadcastId, vnum);
    }

    @RequestMapping("/test")
    public Response<String> test() {
        musicService.test();
        Response<String> res = new Response<>();
        res.setData("test.....");
        return res;
    }

}
