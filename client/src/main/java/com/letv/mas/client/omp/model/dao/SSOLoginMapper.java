package com.letv.mas.client.omp.model.dao;

import com.letv.mas.client.omp.model.xdo.UserDo;
import com.letv.mas.client.omp.service.dto.AclDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SSOLoginMapper {

    UserDo findUserByMail(String mail);

    List<AclDto> findAllAcls();

    void insertUser(String loginUser);

    List<UserDo> findAllUsers();
}
