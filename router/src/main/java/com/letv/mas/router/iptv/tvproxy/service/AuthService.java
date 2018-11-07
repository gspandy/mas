package com.letv.mas.router.iptv.tvproxy.service;

import com.letv.mas.router.iptv.tvproxy.constant.ErrorConsts;
import com.letv.mas.router.iptv.tvproxy.model.dao.db.mysql.ClientDao;
import com.letv.mas.router.iptv.tvproxy.model.dao.db.mysql.UserDao;
import com.letv.mas.router.iptv.tvproxy.model.dto.BaseResponseDto;
import com.letv.mas.router.iptv.tvproxy.model.xdo.ClientDo;
import com.letv.mas.router.iptv.tvproxy.model.xdo.UserDo;
import com.letv.mas.router.iptv.tvproxy.util.IdGenerator;
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
                    baseResponseDto.setCode(ErrorConsts.COM_OK);
                    baseResponseDto.setMsg(ErrorConsts.getMessage(ErrorConsts.COM_OK));
                    LOGGER.info(((UserDo) ret).toString());
                    ret = baseResponseDto;
                }
            } else if ("client".equals(model)) {
                ret = clientDao.getClientById(param);
                if (null != ret) {
                    BaseResponseDto<ClientDo> baseResponseDto = new BaseResponseDto<>();
                    baseResponseDto.setData((ClientDo) ret);
                    baseResponseDto.setCode(ErrorConsts.COM_OK);
                    baseResponseDto.setMsg(ErrorConsts.getMessage(ErrorConsts.COM_OK));
                    LOGGER.info(((ClientDo) ret).toString());
                    ret = baseResponseDto;
                }
            } else if ("genUuid".equals(model)) {
                IdGenerator idGenerator = new IdGenerator();
                ret = idGenerator.nextId();
                if (null != ret) {
                    BaseResponseDto<String> baseResponseDto = new BaseResponseDto<>();
                    baseResponseDto.setData(String.valueOf(ret));
                    baseResponseDto.setCode(ErrorConsts.COM_OK);
                    baseResponseDto.setMsg(ErrorConsts.getMessage(ErrorConsts.COM_OK));
                    LOGGER.info(String.valueOf(ret));
                    ret = baseResponseDto;
                }
            } else {
                ErrorConsts.throwException(ErrorConsts.COM_FAIL);
            }
        }

        return ret;
    }

    public BaseResponseDto<UserDo> getUser(String uuid) {
        BaseResponseDto<UserDo> baseResponseDto = null;

        UserDo userDo = userDao.getUserById(uuid);
        if (null != userDo) {
            baseResponseDto = new BaseResponseDto<>();
            baseResponseDto.setData(userDo);
            baseResponseDto.setCode(ErrorConsts.COM_OK);
            baseResponseDto.setMsg(ErrorConsts.getMessage(ErrorConsts.COM_OK));
        }

        return baseResponseDto;
    }

    public boolean checkLogin(String uuid) {
        boolean ret = false;

        UserDo userDo = userDao.getUserById(uuid);
        if (null != userDo) {
            ret = 0 == userDo.getIs_del() && 0 == userDo.getStatus();
        }

        return ret;
    }

    public String getToken(String code) {
        String token = jwtTokenUtil.genToken(code);
        return token;
    }
}
