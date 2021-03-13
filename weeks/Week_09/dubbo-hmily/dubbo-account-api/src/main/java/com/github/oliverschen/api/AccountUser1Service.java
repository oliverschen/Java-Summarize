package com.github.oliverschen.api;

import org.dromara.hmily.annotation.Hmily;

import java.math.BigDecimal;

/**
 * @author ck
 */
public interface AccountUser1Service {

    @Hmily
    void modCnyUser1(BigDecimal amount);
}
