package com.letv.mas.client.omp.model.dao;

import com.letv.mas.client.omp.model.xdo.UserDo;
import com.letv.mas.client.omp.service.dto.AclDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AuthorityManagementMapper {

    int findAllUsers();

    List<UserDo> findUsersByMail(@Param("mail") String mail);

    int updateUserAcl(@Param("id")String id,@Param("type")String type,@Param("code")String code);

    List<UserDo> findPageUsers(@Param("startIndex") int pageNum, @Param("size") int pageSize);

    int deleteAcl(@Param("id")int id);

    int updateAcl(@Param("id")String id,@Param("name") String name,@Param("path") String path,@Param("_parentId") String parentId);

    int insertAcl(@Param("id")String id,@Param("name") String name,@Param("path") String path,@Param("_parentId") String parentId);

    int findAllAcls();

    List<AclDto> findPageAcls(@Param("startIndex")int i, @Param("size") int pageSize);
}

