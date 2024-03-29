package com.github.oliverschen.constant;

import com.github.oliverschen.exception.ExceptionInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 异常枚举
 */
@Getter
@AllArgsConstructor
public enum CommonEnum implements ExceptionInterface {

    TABLE_NOT_FOUND("H0000", "table not found"),
    TABLE_CREATE_ERROR("H0001", "table create error"),
    TABLE_INSERT_SINGLE_ERROR("H0002", "table insert single data error"),
    TABLE_DELETE_SINGLE_ERROR("H0003", "table delete single data error"),
    TABLE_GET_SINGLE_ERROR("H0004", "table get single data error"),
    TABLE_IS_EXISTS("H0005", "table is exists"),


    INDEX_CREATE_ERROR("E0000", "index create error"),
    DOCUMENT_CREATE_ERROR("E0001", "document create error"),
    DOCUMENT_CREATE_ENTITY_NOT_NULL_ERROR("E0002", "document create error,entity is not null"),


    ;
    private final String code;
    private final String msg;

}
