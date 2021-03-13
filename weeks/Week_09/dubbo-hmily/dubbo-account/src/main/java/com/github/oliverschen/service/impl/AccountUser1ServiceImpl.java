package com.github.oliverschen.service.impl;

import com.github.oliverschen.api.AccountUser1Service;
import com.github.oliverschen.dto.AccountDTO;
import com.github.oliverschen.entity.Account;
import com.github.oliverschen.mapper.user1.User1AccountMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.dromara.hmily.common.exception.HmilyRuntimeException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author ck
 */
@Slf4j
@DubboService
public class AccountUser1ServiceImpl implements AccountUser1Service {

    @Resource
    private User1AccountMapper user1AccountMapper;


    /**
     * 转出人民币
     * @param amount 转出金额
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modCnyUser1(BigDecimal amount) {
        Account account = user1AccountMapper.getAccount();
        // -amount
        if (amount.compareTo(BigDecimal.ZERO) < 0 && account.getCny().compareTo(amount) < 0) {
            throw new HmilyRuntimeException("余额不足");
        }
        user1AccountMapper.modCnyUser1(buildAccountDTO(account.getUserId(),amount));

    }

    private AccountDTO buildAccountDTO(Integer userId, BigDecimal amount) {
        return AccountDTO.builder().amount(amount).userId(userId).build();
    }

}
