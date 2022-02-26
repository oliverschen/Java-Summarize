package com.jihe.reactor.service.impl;

import com.jihe.reactor.service.ReactorService;
import com.jihe.reactor.vo.UserVo;
import org.springframework.stereotype.Service;

@Service
public class ReactorServiceImpl implements ReactorService {

    @Override
    public UserVo getUser() {
        return UserVo.builder()
                .userName("ck")
                .age(18)
                .mobile("17889972556")
                .build();
    }

}
