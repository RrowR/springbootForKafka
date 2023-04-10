package com.study.mykafka.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
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
    private KafkaListenerEndpointRegistry registry;

    @KafkaListener(id = "mygroup", topics = "test")
    public void listen(ConsumerRecords<String,String> records, Acknowledgment acknowledgment) {
        records.forEach(record -> {
            log.info("topic = {}, offset = {}, value = {}", record.topic(), record.offset(), record.value());
        });
        acknowledgment.acknowledge();
    }

    @GetMapping("/send/{input}")
    public void sendMsg(@PathVariable String input) {
        kafkaTemplate.send("test", input);
    }

    @GetMapping("/start")
    public String start() {
        if (!registry.getListenerContainer("mygroup").isRunning()) {
            registry.getListenerContainer("mygroup").start();
        }
        // 将其恢复
        registry.getListenerContainer("mygroup").resume();
        return "启动成功";
    }

    @GetMapping("/stop")
    public String stop() {
        registry.getListenerContainer("mygroup").pause();
        return "已停止";
    }

    @GetMapping("/test")
    public String test() {
        for (String id : registry.getListenerContainerIds()) {
            System.out.println(id);
        }
        return "查询的结果输出至控制台";
    }

}
