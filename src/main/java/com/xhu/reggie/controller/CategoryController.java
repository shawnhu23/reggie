package com.xhu.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhu.reggie.common.R;
import com.xhu.reggie.dto.DishDto;
import com.xhu.reggie.entity.Category;
import com.xhu.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * add new category
     * @param category
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Category category) {
        log.info(category.toString());
        categoryService.save(category);
        return R.success("Add new category successfully");
    }

    /**
     * paging query
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize) {
        log.info("page = {}, pageSize = {}, name = {}", page, pageSize);

        Page<Category> pageInfo = new Page(page, pageSize);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.orderByAsc(Category::getSort);
        categoryService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    @DeleteMapping
    public R<String> delete(Long ids) {
        log.info("Remove category, id: {}", ids);
        categoryService.remove(ids);
        return R.success("Category deleted successfully");
    }

    /**
     * update category info by ID
     * @param category
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Category category) {
        log.info("Update category info: {}", category.toString());

        categoryService.updateById(category);
        return R.success("Update category successfully");
    }

    /**
     * query category
     * @param category
     * @return
     */
    @GetMapping("/list")
    public R<List<Category>> list(Category category) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(category.getType() != null, Category::getType, category.getType());
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryService.list(queryWrapper);
        return R.success(list);
    }
}
