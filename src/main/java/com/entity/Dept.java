package com.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Dept implements Serializable {
    String id;
    String name;
    String createTime;
}
