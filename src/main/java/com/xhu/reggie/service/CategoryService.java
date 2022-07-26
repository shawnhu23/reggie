package com.xhu.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xhu.reggie.entity.Category;

public interface CategoryService extends IService<Category> {

    public void remove(Long id);
}
