package com.example.transactionalformultipledatasource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TransactionConsistentApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionConsistentApplication.class, args);
    }

}
