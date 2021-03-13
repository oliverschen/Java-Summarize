package com.github.oliverschen.proxy;

/**
 * @author ck
 */
public class OrderServiceImpl implements IOrderService {

    @Override
    public boolean placeOrder() {
        System.out.println("下单中...");
        System.out.println("订单已创建");
        System.out.println("下单完成！");
        return true;
    }
}
