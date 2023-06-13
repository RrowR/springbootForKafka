package com.example.transactionalformultipledatasource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
})
public class TransactionConsistentApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionConsistentApplication.class, args);
    }

}
