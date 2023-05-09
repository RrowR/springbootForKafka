package com.example.transactionalformultipledatasource.controller;

import com.example.transactionalformultipledatasource.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Rrow
 * @Date 2023/3/28 17:22
 */
@Slf4j
@RestController
public class KafkaController {

    @Autowired
    private KafkaTemplate<Object, Object> kafkaTemplate;

    @Autowired
    private UserService userService;

    @KafkaListener(id = "mygroup", topics = "test")
    public void listen(String msg) {
        System.out.println(msg);
        String[] split = msg.split(",");
        log.info("收到了数据了 split[0] = {}, split[1] = {}", split[0], split[1]);
        userService.insert(split[0], split[1]);

        log.info("收到数据并执行了");
    }

    @GetMapping("/send/{input}")
    public void sendMsg(@PathVariable String input) {
        kafkaTemplate.send("test", input);
        String[] split = input.split(",");
        log.info("收到了数据了 split[0] = {}, split[1] = {}", split[0], split[1]);
        userService.insert(split[0], split[1]);
        log.info("成功发送了数据....");
    }


}
