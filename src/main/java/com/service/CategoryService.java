package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.entity.Category;
import com.entity.R;

public interface CategoryService extends IService<Category> {
    R<Object> delete(String id);
}
