package com.github.oliverschen.springbean;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author ck
 */
@Data
@Component("user001")
public class User {

    private String name;
    private String phone;
}
