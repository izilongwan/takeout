package com.common;

import java.time.LocalDateTime;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Value("${key.employee}")
    String employee;

    @Value("${key.updated-employee}")
    String updatedEmployee;

    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("createUser", (Long) BaseContext.get(employee));
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", (Long) BaseContext.get(employee));

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 只更新某些字段未触发
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", (Long) BaseContext.get(employee));
    }

}
