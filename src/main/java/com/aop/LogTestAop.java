package com.aop;

import javax.annotation.Resource;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import com.service.EmployeeService;
import com.aop.inter.LogTest;

@Aspect
@Configuration
public class LogTestAop {
    @Value("${key.employee}")
    String employee;

    @Value("${key.updated-employee}")
    String updatedEmployee;

    @Resource
    EmployeeService employeeService;

    @AfterReturning("@annotation(logTest)")
    public void doo(LogTest logTest) {
        System.out.println(logTest);
    }

    @Pointcut("@annotation(com.aop.inter.LogTest)")
    public void anno() {

    }
}
