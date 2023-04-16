package com.example.transactionalformultipledatasource.anno;

import java.lang.annotation.*;

/**
 * @author: Rrow
 * @date: 2023/4/3 23:49
 * Description:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface MultipleDataSourceTransactional {
    /**
     *  事务管理器数组
     */
    String[] transactionManagers();
}
