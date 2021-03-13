package com.github.oliverschen.controller;

import com.github.oliverschen.api.ExchangeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author ck
 */
@RestController
public class AccountController {

    @Resource
    private ExchangeService exchangeService;

    /**
     * A 转出人民币到 B 美元账户
     * 转出人民币
     * CNY ===》 USD
     */
    @GetMapping("/ex/cny")
    public boolean exChangeCny(@RequestParam("cny") BigDecimal amount) {
        return exchangeService.exChangeCny(amount);
    }


    /**
     * B 转出美元到 A 人民币账户
     * 转出美元
     * USD ===》 CNY
     */
    @GetMapping("/ex/usd")
    public boolean exchangeUsd(@RequestParam("cny") BigDecimal amount) {
        return exchangeService.exchangeUsd(amount);
    }
}
