package com.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entity.Dish;

public interface DishMapper extends BaseMapper<Dish> {
    List<Dish> select();

    List<Dish> selectId(long id);

    List<Dish> query(@Param("name") String name);

    List<Dish> query2(Dish dish);

    List<Dish> query3(Map<String, Object> map);

    List<Dish> query4(Dish dish);

    int add(Dish dish);

    int modify(Dish dish);
}
