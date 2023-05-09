package com.example.transactionalformultipledatasource.common;

import com.example.transactionalformultipledatasource.anno.MultipleDataSourceTransactional;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author: Rrow
 * @date: 2023/5/9 16:41
 * Description:
 */
@MultipleDataSourceTransactional
public class Const {

    @Value("${config}")
    private String config;

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }
}
