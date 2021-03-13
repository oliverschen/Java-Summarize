package com.github.oliverschen.api;

import org.dromara.hmily.annotation.Hmily;

import java.math.BigDecimal;

/**
 * @author ck
 */
public interface AccountUser2Service {

    @Hmily
    void modUsdUser2(BigDecimal amount);
}
