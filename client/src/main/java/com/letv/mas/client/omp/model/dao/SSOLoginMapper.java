package com.letv.mas.client.omp.model.dao;

import com.letv.mas.client.omp.model.dto.AclDto;
import com.letv.mas.client.omp.model.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SSOLoginMapper {

    UserDto findUserByMail(String mail);

    List<AclDto> findAllAcls();

    void insertUser(String loginUser);
}
