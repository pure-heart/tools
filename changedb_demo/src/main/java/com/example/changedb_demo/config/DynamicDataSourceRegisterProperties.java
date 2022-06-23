package com.example.changedb_demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 动态多数据源配置文件
 */
@Component
@ConfigurationProperties("datasource.dynamic.register")
public class DynamicDataSourceRegisterProperties {
    public String defaultKey;

    public List<DynamicDataSourceProperties> properties;

    public List<DynamicDataSourceProperties> getProperties() {
        return properties;
    }

    public void setProperties(List<DynamicDataSourceProperties> properties) {
        this.properties = properties;
    }

    public String getDefaultKey() {
        return defaultKey;
    }

    public void setDefaultKey(String defaultKey) {
        this.defaultKey = defaultKey;
    }
}
