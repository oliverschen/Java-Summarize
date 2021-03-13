package com.github.oliverschen.api;

import org.dromara.hmily.annotation.Hmily;

import java.math.BigDecimal;

/**
 * @author ck
 */
public interface ExchangeService {

    @Hmily
    boolean exChangeCny(BigDecimal amount);

    @Hmily
    boolean exchangeUsd(BigDecimal amount);
}
