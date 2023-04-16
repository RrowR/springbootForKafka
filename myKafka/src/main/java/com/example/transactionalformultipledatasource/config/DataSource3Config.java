package com.example.transactionalformultipledatasource.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @Author Rrow
 * @Date 2023/4/3 15:45
 */
@Configuration
@MapperScan(basePackages = "com.example.transactionalformultipledatasource.mapper.mapper3", sqlSessionFactoryRef = "sqlSessionFactory3")
public class DataSource3Config {


    @Bean(name = "transactionManager3")
    public PlatformTransactionManager transactionManager3() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource3());
        return transactionManager;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.third-datasource")
    public DataSource dataSource3() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory3(@Qualifier("dataSource3") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*.xml"));
        return bean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate3(@Qualifier("sqlSessionFactory3") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
