package com.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dto.DishDto;
import com.entity.Category;
import com.entity.Dish;
import com.entity.R;
import com.service.CategoryService;
import com.service.DishService;

@RestController
@RequestMapping("/dish")
public class DishController {
    @Resource
    DishService dishService;

    @Resource
    CategoryService categoryService;

    @GetMapping("{page}/{pageSize}")
    public R<Page<DishDto>> page(@PathVariable long page, @PathVariable long pageSize, Dish dish) {
        Page<Dish> pg = new Page<>(page, pageSize);
        QueryWrapper<Dish> qw = new QueryWrapper<>();

        qw.lambda()
                .like(dish.getName() != null, Dish::getName, dish.getName())
                .orderByDesc(Dish::getUpdateTime);

        Page<Dish> pagewrap = dishService.page(pg, qw);
        Page<DishDto> dishDtoPageWrap = new Page<>();

        BeanUtils.copyProperties(pagewrap, dishDtoPageWrap, "records");

        List<DishDto> records = pagewrap.getRecords().stream().map(o -> {
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(o, dishDto);

            Long cId = o.getCategoryId();

            Category c = categoryService.getById(cId);

            dishDto.setCategoryName(c.getName());

            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPageWrap.setRecords(records);

        return R.SUCCESS(dishDtoPageWrap);
    }
}
