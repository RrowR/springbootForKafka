package com.study.mykafka.controller;

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

    @KafkaListener(id = "mygroup", topics = "test")
    public void listen(String input) {
        log.info("Received: {}", input);
    }

    @GetMapping("/send/{input}")
    public void sendMsg(@PathVariable String input) {
        kafkaTemplate.send("test", input);
    }

}