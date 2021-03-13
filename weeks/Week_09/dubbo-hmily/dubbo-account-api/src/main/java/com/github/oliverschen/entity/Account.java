package com.github.oliverschen.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author ck
 */
@Data
public class Account {
  private Integer id;
  private Integer accountId;
  private Integer userId;
  private String userName;
  private BigDecimal cny;
  private BigDecimal freezeCny;
  private BigDecimal usd;
  private BigDecimal freezeUsd;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;
}
