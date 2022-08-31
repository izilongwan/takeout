package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.entity.Dish;

public interface DishService extends IService<Dish> {
    int countById(String id);
}
