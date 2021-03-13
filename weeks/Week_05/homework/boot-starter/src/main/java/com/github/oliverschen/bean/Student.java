package com.github.oliverschen.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ck
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    private String name;
    private Integer age;
}
