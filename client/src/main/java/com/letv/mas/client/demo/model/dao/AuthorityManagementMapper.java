package com.letv.mas.client.demo.model.dao;

import com.letv.mas.client.demo.model.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AuthorityManagementMapper {

    int findAllUsers();

    List<UserDto> findUsersByMail(@Param("mail") String mail);

    int updateUserAcl(@Param("id")String id,@Param("type")String type,@Param("code")String code);

    List<UserDto> findPageUsers(@Param("startIndex") int pageNum,@Param("size") int pageSize);

    int deleteAcl(@Param("id")int id);

    int updateAcl(@Param("id")String id,@Param("name") String name,@Param("path") String path,@Param("_parentId") String parentId);

    int insertAcl(@Param("id")String id,@Param("name") String name,@Param("path") String path,@Param("_parentId") String parentId);
}

