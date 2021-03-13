package com.github.oliverschen.service.impl;

import com.github.oliverschen.api.AccountUser1Service;
import com.github.oliverschen.api.AccountUser2Service;
import com.github.oliverschen.api.ExchangeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.dromara.hmily.annotation.HmilyTCC;

import java.math.BigDecimal;

/**
 * @author ck
 */
@Slf4j
@DubboService
public class ExchangeServiceImpl implements ExchangeService {

    @DubboReference
    private AccountUser1Service accountUser1Service;
    @DubboReference
    private AccountUser2Service accountUser2Service;

    @Override
    @HmilyTCC(confirmMethod = "exConfirm",cancelMethod = "exCancel")
    public boolean exChangeCny(BigDecimal amount) {
        accountUser1Service.modCnyUser1(amount.negate());
        accountUser2Service.modUsdUser2(amount);
        return true;
    }

    @Override
    @HmilyTCC(confirmMethod = "exConfirm",cancelMethod = "exCancel")
    public boolean exchangeUsd(BigDecimal amount) {
        accountUser2Service.modUsdUser2(amount.negate());
        accountUser1Service.modCnyUser1(amount);
        return true;
    }

    public boolean exConfirm(BigDecimal amount) {
        log.info("===exChange confirm ok===");
        return true;
    }

    public boolean exCancel(BigDecimal amount) {
        log.info("===exChange cancel ok===");
        return true;
    }

}
