package com.xhu.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xhu.reggie.dto.DishDto;
import com.xhu.reggie.entity.Dish;

public interface DishService extends IService<Dish> {

    /**
     * add a new dish and its corresponding flavor
     * @param dishDto
     */
    public void saveWithFlavor(DishDto dishDto);

    /**
     * get dish and its corresponding flavor by id
     * @param id
     * @return
     */
    public DishDto getByIdWithFlavor(Long id);

    /**
     * update dish and its flavor
     * @param dishDto
     */
    public void updateWithFlavor(DishDto dishDto);
}
