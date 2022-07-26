package com.xhu.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhu.reggie.common.R;
import com.xhu.reggie.entity.Employee;
import com.xhu.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * employee login
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        // md5
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        // query the database
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        if (emp == null) {
            return R.error("Login failed");
        }

        if (!emp.getPassword().equals(password)) {
            return R.error("Login failed");
        }

        if (emp.getStatus() == 0) {
            return R.error("Account is locked");
        }

        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    /**
     * employee logout
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employeeInfo");
        return R.success("Logout successfully");
    }

    /**
     * add a new employee
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("new employee, info: {}", employee.toString());
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//
//        Long empId = (Long) request.getSession().getAttribute("employee");
//        employee.setCreateUser(empId);
//        employee.setUpdateUser(empId);

        employeeService.save(employee);
        return R.success("add new employee successfully");
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
        log.info("page = {}, pageSize = {}, name = {}", page, pageSize, name);

        Page pageInfo = new Page(page, pageSize);
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        employeeService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * update employee info based on ID
     * @param request
     * @param employee
     * @return
     */
    @PutMapping
    public R<String>  update(HttpServletRequest request, @RequestBody Employee employee) {
        log.info(employee.toString());

        Long empId = (Long) request.getSession().getAttribute("employee");
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(empId);
        employeeService.updateById(employee);
        return R.success("Employee info updated successfully");
    }

    /**
     * query employee info by ID
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        log.info("Query employee info by ID");
        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return R.success(employee);
        }
        return R.error("Employee does not exist");
    }
}
