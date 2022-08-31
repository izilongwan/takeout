package com.controller;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entity.Category;
import com.entity.R;
import com.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    CategoryService categoryService;

    @PutMapping
    public R<Boolean> add(@RequestBody Category category) {
        return R.SUCCESS(categoryService.save(category));
    }

    @GetMapping("{page}/{pageSize}")
    @Cached
    public R<IPage<Category>> page(@PathVariable long page, @PathVariable long pageSize, Category category) {
        QueryWrapper<Category> qw = new QueryWrapper<>();

        qw.lambda()
                .like(category.getName() != null, Category::getName, category.getName())
                .orderByDesc(Category::getSort)
                .orderByAsc(Category::getType);

        Page<Category> pg = new Page<>(page, pageSize);

        return R.SUCCESS(categoryService.page(pg, qw));
    }

    @DeleteMapping("{id}")
    @Transactional
    @CacheInvalidate(name = "one")
    public R<Object> delete(@PathVariable String id) {

        return categoryService.delete(id);
    }

    @GetMapping("{id}")
    @Cached(name = "one")
    public R<Category> one(@PathVariable String id) {
        return R.SUCCESS(categoryService.getById(id));
    }
}
