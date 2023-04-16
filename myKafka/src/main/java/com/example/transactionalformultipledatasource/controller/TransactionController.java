package com.example.transactionalformultipledatasource.controller;

import com.example.transactionalformultipledatasource.anno.MultipleDataSourceTransactional;
import com.example.transactionalformultipledatasource.mapper.mapper1.Db1Mapper;
import com.example.transactionalformultipledatasource.mapper.mapper2.Db2Mapper;
import com.example.transactionalformultipledatasource.mapper.mapper3.Db3Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Rrow
 * @Date 2023/4/3 16:34
 */
@RestController
public class TransactionController {

    @Autowired
    private Db1Mapper db1Mapper;

    @Autowired
    private Db2Mapper db2Mapper;

    @Autowired
    private Db3Mapper db3Mapper;

    @GetMapping("/testInsert")
    @MultipleDataSourceTransactional(transactionManagers = {"transactionManager1","transactionManager2","transactionManager3"})
    public String testInsert(String username,String password){
        int i = 0;
        db1Mapper.insert(username + ++i,password);
        db2Mapper.insert(username + ++i,password);
        db3Mapper.insert(username + ++i,password);
        return "finish";
    }
}
