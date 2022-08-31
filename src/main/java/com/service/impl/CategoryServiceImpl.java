package com.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.Category;
import com.entity.R;
import com.mapper.CategoryMapper;
import com.service.CategoryService;
import com.service.DishService;
import com.service.SetmealService;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Resource
    DishService dishService;

    @Resource
    SetmealService setmealService;

    @Override
    public R<Object> delete(String id) {
        int count1 = dishService.countById(id);

        if (count1 > 0) {
            return R.ERROR("菜品被分类[" + count1 + "处]关联, 不可删除");
        }

        int count2 = setmealService.countById(id);

        if (count2 > 0) {
            return R.ERROR("菜品被套餐[" + count2 + "处]关联, 不可删除");
        }

        return R.SUCCESS(this.removeById(id));
    }

}
