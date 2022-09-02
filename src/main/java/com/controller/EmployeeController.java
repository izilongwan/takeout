package com.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aop.anno.LogAnno;
import com.aop.anno.LogsAnno;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.BaseContext;
import com.entity.Employee;
import com.entity.R;
import com.mapper.EmployeeMapper;
import com.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Resource
    EmployeeService employeeService;

    @Resource
    EmployeeMapper employeeMapper;

    @Value("${key.employee}")
    String employeeKey;

    @Value("${key.updated-employee}")
    String updatedEmployee;

    public void setPasswordNull(List<Employee> list) {
        list.forEach(o -> {
            o.setPassword(null);
        });
    }

    @GetMapping
    @LogAnno("people")
    @LogAnno("china")
    // @LogsAnno({ @LogAnno("people"), @LogAnno("china") })
    public R<List<Employee>> list() {
        List<Employee> rs = employeeService.list();
        setPasswordNull(rs);
        return R.SUCCESS(rs);
    }

    @GetMapping("{id}")
    public R<Employee> one(@PathVariable String id) {
        Employee u = employeeService.getById(id);

        List<Employee> list = new ArrayList<>();

        list.add(u);

        setPasswordNull(list);

        return R.SUCCESS(u);
    }

    @PutMapping
    public R<Object> add(@RequestBody Employee employee) {
        String password = DigestUtils.md5DigestAsHex(employee.getPassword().getBytes());

        employee.setPassword(password);

        boolean rs = employeeService.save(employee);

        return R.SUCCESS(rs);
    }

    @PostMapping
    @Transactional
    public R<Boolean> update(@RequestBody Employee employee) {
        UpdateWrapper<Employee> updateWrapper = new UpdateWrapper<>();

        String password = employee.getPassword();

        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();

        queryWrapper.lambda().eq(Employee::getUsername, employee.getUsername());

        Employee u = employeeService.getOne(queryWrapper);

        BaseContext.set(updatedEmployee, u.getId());

        updateWrapper
                .lambda()
                .eq(employee.getUsername() != null, Employee::getUsername,
                        employee.getUsername())
                .set(employee.getStatus() != null, Employee::getStatus, employee.getStatus())
                .set(employee.getName() != null, Employee::getName, employee.getName())
                .set(employee.getPhone() != null, Employee::getPhone, employee.getPhone())
                .set(employee.getSex() != null, Employee::getSex, employee.getSex())
                .set(password != null, Employee::getPassword,
                        DigestUtils.md5DigestAsHex((password == null ? "" : password).getBytes()))
                .set(Employee::getUpdateUser, u.getId())
                .set(Employee::getUpdateTime, LocalDateTime.now());

        return R.SUCCESS(employeeService.update(updateWrapper));
    }

    @PostMapping("login")
    public R<Object> login(HttpServletRequest req, HttpSession sess, @RequestBody Employee employee) {
        String username = String.valueOf(employee.getUsername());
        String password = employee.getPassword();

        if (username == null || password == null) {
            return R.ERROR("账号或密码不能为空");
        }

        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();

        queryWrapper.lambda().eq(Employee::getUsername, username);
        Employee u = employeeService.getOne(queryWrapper);

        if (u == null) {
            return R.ERROR("账号或密码不正确");
        }

        password = DigestUtils.md5DigestAsHex(password.getBytes());

        if (!u.getPassword().equals(password)) {
            return R.ERROR("账号或密码不正确");
        }

        if (u.getStatus() == 0) {
            return R.ERROR("账号被锁定");
        }

        sess.setAttribute(employeeKey, u.getId());
        BaseContext.set(employeeKey, u.getId());

        u.setPassword(null);

        List<Employee> list = new ArrayList<>();

        list.add(u);

        setPasswordNull(list);

        return R.SUCCESS(u);
    }

    @GetMapping("logout")
    public R<Object> logout(HttpSession sess) {
        Object v = sess.getAttribute(employeeKey);

        if (v != null) {
            sess.removeAttribute(employeeKey);
            return R.SUCCESS();
        }

        return R.ERROR();
    }

    @GetMapping("{page}/{pageSize}")
    public R<Page<Employee>> page(@PathVariable long page, @PathVariable long pageSize, Employee employee) {

        Page<Employee> pg = new Page<>(page, pageSize);
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();

        String name = employee.getName();
        Integer status = employee.getStatus();

        queryWrapper
                .lambda()
                .like(name != null, Employee::getName, name)
                .eq(status != null, Employee::getStatus, status)
                .orderByDesc(Employee::getUpdateTime);

        Page<Employee> rs = employeeService.page(pg, queryWrapper);

        setPasswordNull(rs.getRecords());
        return R.SUCCESS(rs);
    }

}
