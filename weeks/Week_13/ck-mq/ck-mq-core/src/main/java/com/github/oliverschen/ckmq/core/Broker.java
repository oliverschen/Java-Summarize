package com.github.oliverschen.ckmq.core;

import lombok.Data;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author ck
 */
@Data
public class Broker {

    private static LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();


}
