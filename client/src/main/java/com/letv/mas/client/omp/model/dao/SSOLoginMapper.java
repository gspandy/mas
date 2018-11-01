package com.letv.mas.client.omp.model.dao;

import com.letv.mas.client.omp.model.xdo.UserDo;
import com.letv.mas.client.omp.service.dto.AclDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SSOLoginMapper {

    UserDo findUserByMail(@Param("mail") String mail);

    List<AclDto> findAllAcls();

    void insertUser(@Param("mail") String loginUser);

    List<UserDo> findAllUsers();
}
