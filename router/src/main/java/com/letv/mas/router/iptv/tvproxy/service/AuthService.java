package com.letv.mas.router.iptv.tvproxy.service;

import com.letv.mas.router.iptv.tvproxy.model.dao.db.mysql.UserDao;
import com.letv.mas.router.iptv.tvproxy.model.dto.BaseResponseDto;
import com.letv.mas.router.iptv.tvproxy.model.xdo.UserDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by leeco on 18/10/19.
 */
@Service("AuthService")
public class AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserDao userDao;

    public Object test(String param) {
        Object ret = null;
        ret = userDao.getUserById(param);
        if (null != ret) {
            BaseResponseDto<UserDo> baseResponseDto = new BaseResponseDto<>();
            baseResponseDto.setData((UserDo) ret);
            LOGGER.info(((UserDo) ret).toString());
        }
        return ret;
    }
}
