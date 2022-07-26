package com.xhu.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xhu.reggie.dto.SetmealDto;
import com.xhu.reggie.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {

    /**
     * add a new setmeal with its dishes
     * @param setmealDto
     */
    public void saveWithDish(SetmealDto setmealDto);

    /**
     * delete setmeal(s) with its dishes
     * @param ids
     */
    public void removeWithDish(List<Long> ids);
}
