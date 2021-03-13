package com.github.oliverschen.service.impl;

import com.github.oliverschen.api.AccountUser2Service;
import com.github.oliverschen.dto.AccountDTO;
import com.github.oliverschen.entity.Account;
import com.github.oliverschen.mapper.user2.User2AccountMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.dromara.hmily.common.exception.HmilyRuntimeException;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author ck
 */
@DubboService
public class AccountUser2ServiceImpl implements AccountUser2Service {

    @Resource
    private User2AccountMapper user2AccountMapper;

    /**
     * 增加美元：usd = cny * 7
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modUsdUser2(BigDecimal amount) {
        Account account = user2AccountMapper.getAccount();
        BigDecimal usd = amount.divide(BigDecimal.valueOf(7), 2, RoundingMode.HALF_DOWN);
        if (usd.compareTo(BigDecimal.ZERO) < 0 && account.getUsd().compareTo(usd) < 0) {
            throw new HmilyRuntimeException("余额不足");
        }
        user2AccountMapper.modUser2(buildAccount(usd, account.getUserId()));
    }

    private AccountDTO buildAccount(BigDecimal usd, Integer userId) {
        return AccountDTO.builder().userId(userId).amount(usd).build();
    }
}
