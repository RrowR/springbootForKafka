package com.example.transactionalformultipledatasource.controller;

import com.example.transactionalformultipledatasource.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Rrow
 * @date: 2023/4/16 23:34
 * Description:
 */
@RestController
@Slf4j
public class UserServiceController {

    @Autowired
    private UserService userService;

    @GetMapping("/serviceInsert")
    public String serviceInsert(String username,String password) {
        userService.insert(username,password);
        log.info("插入成功了....{},{}",username,password);
        return "success";
    }

}
