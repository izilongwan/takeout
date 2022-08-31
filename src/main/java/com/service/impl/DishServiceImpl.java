package com.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.Dish;
import com.mapper.DishMapper;
import com.service.DishService;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Resource
    DishService dishService;

    public int countById(String id) {
        QueryWrapper<Dish> queryWrapper = new QueryWrapper<>();

        queryWrapper.lambda().eq(Dish::getCategoryId, id);

        return dishService.count(queryWrapper);
    }
}
