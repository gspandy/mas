package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db;

import java.util.List;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.QrCodeMysqlTable;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
/**
 * 访问定向引导
 */
@Component(value = "v2.QrCodeMysqlDao")
public interface QrCodeMysqlDao {

    public List<QrCodeMysqlTable> getList();

    public QrCodeMysqlTable getQrCode(@Param(value = "id") Integer id);

    public QrCodeMysqlTable getQrCodeByChannelid(@Param(value = "channelid") Integer channelid);

    public List<QrCodeMysqlTable> getListByCid(@Param(value = "channelid") Integer channelid);

    public List<QrCodeMysqlTable> getListByStream(@Param(value = "stream") String stream);

    public void update(QrCodeMysqlTable data);

    public void insert(QrCodeMysqlTable data);

    public void delete(@Param(value = "id") Integer id);
}
