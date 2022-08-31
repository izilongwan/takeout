package com.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.Setmeal;
import com.mapper.SetmealMapper;
import com.service.SetmealService;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Resource
    SetmealService setmealService;

    public int countById(String id) {
        QueryWrapper<Setmeal> queryWrapper = new QueryWrapper<>();

        queryWrapper.lambda().eq(Setmeal::getCategoryId, id);

        return setmealService.count(queryWrapper);
    }
}
