package com.example.transactionalformultipledatasource.mapper.mapper3;

import org.apache.ibatis.annotations.Param;

/**
 * @Author Rrow
 * @Date 2023/4/3 16:32
 */
public interface Db3Mapper {

    int insert(@Param("username") String username, @Param("password") String password);

}
