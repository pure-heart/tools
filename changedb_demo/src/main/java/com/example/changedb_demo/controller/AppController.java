package com.example.changedb_demo.controller;

import com.example.changedb_demo.config.DynamicDataSourceContextHolder;
import com.example.changedb_demo.config.DynamicDataSourceRegisterProperties;
import com.example.changedb_demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class AppController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DynamicDataSourceRegisterProperties dynamicDataSourceRegisterProperties;

    @RequestMapping("change/{name}")
    public String changeDb(@PathVariable("name") String name) {
        dynamicDataSourceRegisterProperties.setDefaultKey(name);
        DynamicDataSourceContextHolder.setDataSourceRouterKey(name);
        return DynamicDataSourceContextHolder.getDataSourceRouterKey();
    }

    @RequestMapping("list")
    public List<Map<String,Object>> list(){
        return userMapper.list();
    }
}
