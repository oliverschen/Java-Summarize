package com.github.oliverschen.mapper.user2;

import com.github.oliverschen.dto.AccountDTO;
import com.github.oliverschen.entity.Account;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ck
 */
@Mapper
public interface User2AccountMapper {

    void modUser2(AccountDTO accountDTO);

    Account getAccount();
}
