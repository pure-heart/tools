package com.example.changedb_demo.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * 数据源配置，这里继承了springboot的单数据源配置类，并且为配置分配了key
 */
@Component
@Primary
@ConfigurationProperties("datasource.dynamic.properties")
public class DynamicDataSourceProperties extends DataSourceProperties{
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
