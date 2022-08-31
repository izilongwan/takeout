package com.aop;

import javax.annotation.Resource;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.aop.anno.LogAnno;
import com.service.EmployeeService;

@Aspect
@Configuration
public class LogAnnoAop {
    @Value("${key.employee}")
    String employee;

    @Value("${key.updated-employee}")
    String updatedEmployee;

    @Resource
    EmployeeService employeeService;

    @AfterReturning("@annotation(logAnno)")
    public void doo(LogAnno logAnno) {
        System.out.println(logAnno);
    }

    @Pointcut("@annotation(com.aop.inter.LogAnno)")
    public void anno() {

    }
}
