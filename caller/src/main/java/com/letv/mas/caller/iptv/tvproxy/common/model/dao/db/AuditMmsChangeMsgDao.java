package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db;

import java.util.List;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AuditMmsChangeMsg;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface AuditMmsChangeMsgDao {

    void insertMsg(AuditMmsChangeMsg message);

    List<AuditMmsChangeMsg> fetchUnprocessedMsg(int consumerId);

    void updateAllMsgStatus(@Param("newStatus") int newStatus, @Param("oldStatus") int oldStatus,
            @Param("consumerId") int consumerId);

    void updateMsgStatusById(@Param("id") String id, @Param("status") int status);

    void updateMsgStatusByIdList(@Param("idList") List<String> idList, @Param("status") int status);

    void deleteMsgById(String id);

    List<String> findUnprocessedMsg(AuditMmsChangeMsg changeMsg);
}
