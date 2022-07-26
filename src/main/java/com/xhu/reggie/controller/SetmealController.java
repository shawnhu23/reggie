package com.xhu.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhu.reggie.common.R;
import com.xhu.reggie.dto.SetmealDto;
import com.xhu.reggie.entity.Category;
import com.xhu.reggie.entity.Setmeal;
import com.xhu.reggie.entity.ShoppingCart;
import com.xhu.reggie.service.CategoryService;
import com.xhu.reggie.service.SetmealDishService;
import com.xhu.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    /**
     * add a new setmeal
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        log.info("Setmeal info: {}", setmealDto.toString());
        setmealService.saveWithDish(setmealDto);
        return R.success("Add new setmeal successfully");
    }

    /**
     * paging query
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        Page<Setmeal> pageInfo = new Page(page, pageSize);
        Page<SetmealDto> setmealDtoPage = new Page();

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name), Setmeal::getName, name);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(pageInfo, queryWrapper);

        BeanUtils.copyProperties(pageInfo, setmealDtoPage, "records");
        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> list = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);

            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());

        setmealDtoPage.setRecords(list);
        return R.success(setmealDtoPage);
    }

    /**
     * delete setmeal(s)
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        log.info("ids: {}", ids);
        setmealService.removeWithDish(ids);
        return R.success("Setmeal removed successfully");
    }

    /**
     * query setmeal
     * @param categoryId
     * @param status
     * @return
     */
    @GetMapping("/list")
    public R<List<Setmeal>> list(Long categoryId, int status) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(categoryId != null, Setmeal::getCategoryId, categoryId);
        queryWrapper.eq(Setmeal::getStatus, status);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        List<Setmeal> list = setmealService.list(queryWrapper);
        return R.success(list);
    }

}
