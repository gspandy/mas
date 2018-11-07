package com.letv.mas.caller.iptv.tvproxy.notification;

import com.letv.mas.caller.iptv.tvproxy.common.model.AlbumVideoAccess;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.VideoMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.Response;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.notification.model.dto.QrCodeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通知
 */
@RestController
public class NotificationController {

    @Autowired
    AlbumVideoAccess albumVideoAccess;

    @Autowired
    QrCodeService qrCodeService;

    @RequestMapping("/notification/event/get")
    public Response<QrCodeDto> event_get(@RequestParam(value = "videoId") Long videoId,
                                         @RequestParam(value = "broadcastId", required = false) Integer broadcastId,
                                         @RequestParam(value = "stream", required = false) String stream,
                                         @RequestParam(value = "blockId", required = false) String blockId, @ModelAttribute CommonParam commonParam) {

        VideoMysqlTable video = albumVideoAccess.getVideo(videoId, commonParam);
        QrCodeDto dto = null;
        if (video != null) {
            Integer categoryId = video.getCategory();
            dto = qrCodeService.getQrCodeByStream(stream, categoryId, video.getId(),
                    video.getPid(), commonParam);
        } else {
            dto = qrCodeService.getQrCodeByStream(stream, 2, videoId, 0L, commonParam);
        }
        if (dto == null) {
            dto = new QrCodeDto();
        }
        return new Response<QrCodeDto>(dto);
    }

}
