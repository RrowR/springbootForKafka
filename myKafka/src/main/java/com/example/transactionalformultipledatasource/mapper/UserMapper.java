package com.example.transactionalformultipledatasource.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @author: Rrow
 * @date: 2023/4/3 21:04
 * Description:
 */
public interface UserMapper {
    int insert(@Param("username") String username, @Param("password") String password);
}
