package com.example.transactionalformultipledatasource.service.impl;

import com.example.transactionalformultipledatasource.anno.MultipleDataSourceTransactional;
import com.example.transactionalformultipledatasource.mapper.mapper1.Db1Mapper;
import com.example.transactionalformultipledatasource.mapper.mapper2.Db2Mapper;
import com.example.transactionalformultipledatasource.mapper.mapper3.Db3Mapper;
import com.example.transactionalformultipledatasource.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: Rrow
 * @date: 2023/4/16 23:33
 * Description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private Db1Mapper db1Mapper;

    @Autowired
    private Db2Mapper db2Mapper;

    @Autowired
    private Db3Mapper db3Mapper;

    @Override
    // 如何把上面的字符串数组传到下面的注解里去
    @MultipleDataSourceTransactional
    public int insert(String username, String password) {
        int i = 0;
        db1Mapper.insert(username + ++i,password);
        db2Mapper.insert(username + ++i,password);
        db3Mapper.insert(username + ++i,password);
        try {
            // int j = i/0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return i;
    }
}
