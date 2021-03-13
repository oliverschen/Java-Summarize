package com.github.oliverschen;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author ck
 */
public class MainEntrance {
    public static void main(final String[] args) throws IOException, SQLException {
        XAOrderService orderService = new XAOrderService("/sharding.yml");
        orderService.init();
        orderService.insert();
    }
}
