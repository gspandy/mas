package com.letv.mas.caller.iptv.tvproxy.music;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.dto.PageResponse;
import com.letv.mas.caller.iptv.tvproxy.common.service.BaseService;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.MusicNavMysqlTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class MusicService extends BaseService {
    private final static Logger log = LoggerFactory.getLogger(MusicService.class);

    // 获取音乐台的导航列表及其子导航标签。
    public PageResponse<MusicNav> getMusicNavlList(Long fId, Integer broadcastId, Float vnum) {
        if (fId == null) {
            fId = Long.valueOf(0);
        }
        List<MusicNavMysqlTable> list = this.facadeMysqlDao.getMusicNavMysqlDao().getList(fId, broadcastId, vnum);
        List<MusicNav> musicNavList = new LinkedList<MusicNav>();
        if (list != null && list.size() > 0) {
            for (MusicNavMysqlTable m : list) {
                MusicNav musicNav = new MusicNav();
                musicNav.setCid(m.getCid());
                musicNav.setId(m.getId());
                musicNav.setNav_code(m.getNav_code());
                musicNav.setName(m.getName());
                musicNav.setOrder_num(m.getOrder_num());
                musicNav.setParent_id(m.getParent_id());
                musicNav.setReq_type(m.getReq_type());
                musicNav.setUrl(m.getUrl());
                loadSubNav(musicNav, broadcastId, vnum);
                musicNavList.add(musicNav);
            }
        }
        return new PageResponse<MusicNav>(musicNavList);
    }

    private void loadSubNav(MusicNav musicNav, Integer broadcastId, Float vnum) {
        List<MusicNavMysqlTable> list = this.facadeMysqlDao.getMusicNavMysqlDao().getList(musicNav.getId(),
                broadcastId, vnum);
        if (list != null && list.size() > 0) {
            List<MusicNav> musicNavList = new LinkedList<MusicNav>();
            for (MusicNavMysqlTable m : list) {
                MusicNav mN = new MusicNav();
                if (musicNav.getId() != 0 && musicNav.getId() == 36) {
                    if (m.getLe_id() != null) {
                        mN.setId(m.getLe_id());
                    } else {
                        this.log.error("loadSubNav_" + musicNav.getId() + "_" + musicNav.getName() + "_" + m.getName()
                                + ": null le_id ");
                    }
                } else {
                    mN.setId(m.getId());
                }
                mN.setCid(m.getCid());
                mN.setNav_code(m.getNav_code());
                mN.setName(m.getName());
                mN.setOrder_num(m.getOrder_num());
                mN.setParent_id(m.getParent_id());
                mN.setReq_type(m.getReq_type());
                mN.setUrl(m.getUrl());
                loadSubNav(mN, broadcastId, vnum);
                musicNavList.add(mN);
                musicNav.setSubNav(musicNavList);
            }
        }
        return;
    }

    public void test() {
        this.facadeTpDao.getStaticTpDao().getHolidayConfig();
    }
}
