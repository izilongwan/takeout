package com.mapper;

import org.apache.ibatis.annotations.Param;

import com.entity.User;

public interface UserMapper {
    int add(User u);

    int update(User u);

    int delete(@Param("ids") int[] ids);
}
