package com.github.oliverschen.springbean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ck
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class School {

    private String schoolName;
    private String address;

    private Student student;
}
