package com.example.transactionalformultipledatasource.mapper.mapper1;

import org.apache.ibatis.annotations.Param;

/**
 * @Author Rrow
 * @Date 2023/4/3 16:32
 */
public interface Db1Mapper {

    int insert(@Param("username") String username, @Param("password") String password);

}
