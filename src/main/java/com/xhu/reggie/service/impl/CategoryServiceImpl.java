package com.xhu.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhu.reggie.common.CustomException;
import com.xhu.reggie.entity.Category;
import com.xhu.reggie.entity.Dish;
import com.xhu.reggie.entity.Setmeal;
import com.xhu.reggie.mapper.CategoryMapper;
import com.xhu.reggie.service.CategoryService;
import com.xhu.reggie.service.DishService;
import com.xhu.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /**
     * delete category by id if it is not related to a dish or setmeal
     * @param id
     */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int dishCount = dishService.count(dishLambdaQueryWrapper);
        if (dishCount > 0) {
            throw new CustomException("The current category cannot be deleted because a dish is related to it");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int setmealCount = setmealService.count(setmealLambdaQueryWrapper);
        if (setmealCount > 0) {
            throw new CustomException("The current category cannot be deleted because a setmeal is related to it");
        }

        super.removeById(id);
    }
}
