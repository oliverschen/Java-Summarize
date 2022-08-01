package com.github.oliverschen.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BaseEntity implements Serializable {

    public BaseEntity() {
        LocalDateTime now = LocalDateTime.now();
        this.id = UUID.randomUUID().toString().replace("-", "");
        this.updateTime = now;
        this.createTime = now;
    }

    private String id;
    private String updateBy;
    private String createdBy;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
