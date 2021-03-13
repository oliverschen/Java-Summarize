package com.github.oliverschen.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author ck
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String name;
    private String phone;
}
