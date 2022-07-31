package com.github.oliverschen.entity;

import lombok.Data;

import java.util.List;

@Data
public class UserEntity {

    private String firstName;
    private String lastName;
    private Integer age;
    private String about;
    private List<String> interests;

}
