package com.github.oliverschen.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ck
 */
@Data
@Builder
public class AccountDTO {

    private Integer userId;
    private BigDecimal amount;

}
