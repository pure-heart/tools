//package com.example.changedb_demo.register;
//
//import com.example.changedb_demo.config.DynamicDataSourceContextHolder;
//import com.example.changedb_demo.config.DynamicDataSourceProperties;
//import com.example.changedb_demo.config.DynamicDataSourceRegisterProperties;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.source.ConfigurationPropertyNameAliases;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.stereotype.Component;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 动态数据源注册
// * 实现 ImportBeanDefinitionRegistrar 实现数据源注册
// * 实现 EnvironmentAware 用于读取application.yml配置
// */
//@Component
//public class DynamicDataSourceRegister implements InitializingBean {
//    /** 自动注入配置类 */
//    @Autowired
//    DynamicDataSourceRegisterProperties dynamicDataSourceRegisterProperties;
//
//    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceRegister.class);
//
//
//    /**
//     * 别名
//     */
//    private final static ConfigurationPropertyNameAliases aliases = new ConfigurationPropertyNameAliases();
//
//    static {
//        aliases.addAliases("url", "jdbc-url");
//        aliases.addAliases("username", "user");
//    }
//
//    /**
//     * 存储我们注册的数据源
//     */
//    private final Map<Object, Object> customDataSources = new HashMap<Object, Object>();
//
//    /** 记录默认数据源 */
//    private DataSource defaultDatasource = null;
//
//    /**
//     * 继承自InitializingBean，对象生成后自动执行
//     */
//    @Override
//    public void afterPropertiesSet() {
//        List<DynamicDataSourceProperties> properties = dynamicDataSourceRegisterProperties.getProperties();
//        String defaultKey = dynamicDataSourceRegisterProperties.getDefaultKey();
//        for (DynamicDataSourceProperties property : properties){
//            DataSourceBuilder<?> builder = property.initializeDataSourceBuilder();
//            DataSource dataSource = builder.type(property.getType()).build();
//            customDataSources.put(property.getKey(), dataSource);
//            DynamicDataSourceContextHolder.dataSourceIds.add(property.getKey());
//            if(property.getKey().equals(defaultKey)){
//                defaultDatasource = dataSource;
//            }
//        }
//    }
//
//    public Map<Object, Object> getCustomDataSources() {
//        return customDataSources;
//    }
//
//    public DataSource getDefaultDatasource() {
//        return defaultDatasource;
//    }
//}
