package com.github.oliverschen.mapper.user1;

import com.github.oliverschen.dto.AccountDTO;
import com.github.oliverschen.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author ck
 */
@Mapper
public interface User1AccountMapper {

    /**
     * 减少人民币
     */
    void modCnyUser1(AccountDTO accountDTO);

    Account getAccount();
}
