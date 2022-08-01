package com.github.oliverschen.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends BaseEntity {

    private String firstName;
    private String lastName;
    private Integer age;
    private String about;
    private List<String> interests;

}
