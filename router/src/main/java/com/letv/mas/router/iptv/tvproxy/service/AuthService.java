package com.letv.mas.router.iptv.tvproxy.service;

import com.letv.mas.router.iptv.tvproxy.model.dao.db.mysql.ClientDao;
import com.letv.mas.router.iptv.tvproxy.model.dao.db.mysql.UserDao;
import com.letv.mas.router.iptv.tvproxy.model.dto.BaseResponseDto;
import com.letv.mas.router.iptv.tvproxy.model.xdo.ClientDo;
import com.letv.mas.router.iptv.tvproxy.model.xdo.UserDo;
import com.letv.mas.router.iptv.tvproxy.util.JwtTokenUtil;
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
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ClientDao clientDao;

    public Object test(String model, String param) {
        Object ret = null;

        if (null != model) {
            if ("user".equals(model)) {
                ret = userDao.getUserById(param);
                if (null != ret) {
                    BaseResponseDto<UserDo> baseResponseDto = new BaseResponseDto<>();
                    baseResponseDto.setData((UserDo) ret);
                    LOGGER.info(((UserDo) ret).toString());
                }
            } else if ("client".equals(model)) {
                ret = clientDao.getClientById(param);
                if (null != ret) {
                    BaseResponseDto<ClientDo> baseResponseDto = new BaseResponseDto<>();
                    baseResponseDto.setData((ClientDo) ret);
                    LOGGER.info(((ClientDo) ret).toString());
                }
            }
        }

        return ret;
    }

    public String getToken(String code) {
        String token = jwtTokenUtil.genToken(code);

        return token;
    }
}
