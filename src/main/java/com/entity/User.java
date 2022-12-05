package com.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class User implements Serializable {
    String id;
    String name;
    String phone;
    String sex;
    String idNumber;
    String avator;
    int status;
    int deptId;
    Dept dept;
}
