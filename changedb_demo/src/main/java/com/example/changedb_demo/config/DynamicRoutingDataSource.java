package com.example.changedb_demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 动态数据源类，继承了AbstractRoutingDataSource，实现其中的determineCurrentLookupKey
 * 该方法会在每次查询前被调用，用于获取数据源
 */
@Component
public class DynamicRoutingDataSource  extends AbstractRoutingDataSource {

    /** 自动注入配置类 */
    @Autowired
    DynamicDataSourceRegisterProperties dynamicDataSourceRegisterProperties;
    private static final Logger logger = LoggerFactory.getLogger(DynamicRoutingDataSource.class);
    /**
     * 存储我们注册的数据源
     */
    private final Map<Object, Object> customDataSources = new HashMap<Object, Object>();

    /** 记录默认数据源 */
    private DataSource defaultDatasource = null;
    /**
     * 返回当前数据源表示
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        String dataSourceName = DynamicDataSourceContextHolder.getDataSourceRouterKey();
        logger.info("当前数据源是：{}", dataSourceName);
        return DynamicDataSourceContextHolder.getDataSourceRouterKey();
    }

    @Override
    public void afterPropertiesSet() {
        List<DynamicDataSourceProperties> properties = dynamicDataSourceRegisterProperties.getProperties();
        String defaultKey = dynamicDataSourceRegisterProperties.getDefaultKey();
        for (DynamicDataSourceProperties property : properties){
            DataSourceBuilder<?> builder = property.initializeDataSourceBuilder();
            DataSource dataSource = builder.type(property.getType()).build();
            customDataSources.put(property.getKey(), dataSource);
            DynamicDataSourceContextHolder.dataSourceIds.add(property.getKey());
            if(property.getKey().equals(defaultKey)){
                defaultDatasource = dataSource;
            }
        }
        //为targetDataSources赋值所有数据源
        super.setTargetDataSources(customDataSources);
        //为defaultTargetDataSource赋值指定数据源
        super.setDefaultTargetDataSource(defaultDatasource);
        super.afterPropertiesSet();
    }


}
