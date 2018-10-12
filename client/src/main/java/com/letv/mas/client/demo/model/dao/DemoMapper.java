package com.letv.mas.client.demo.model.dao;

import com.letv.mas.client.demo.model.dto.AclDto;
import com.letv.mas.client.demo.model.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DemoMapper {

    UserDto findUserByMail(String mail);

    List<AclDto> findAllAcls();

    void insertUser(String loginUser);
}
