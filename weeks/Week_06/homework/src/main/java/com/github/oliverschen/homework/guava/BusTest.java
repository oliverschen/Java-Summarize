package com.github.oliverschen.homework.guava;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author ck
 * guava 事件总线
 */
public class BusTest {

    public static void main(String[] args) {
        EventBus bus = new EventBus("test");
        bus.register(new MsgListener());
        bus.post(new Msg().setMsg("我是消息"));
    }


    /**
     * 消息类
     */
    @Data
    @Accessors(chain = true)
    public static class Msg{
        private String msg;
    }

    /**
     * 监听
     */
    public static class MsgListener{
        @Subscribe
        public void onMsg(Msg msg) {
            System.out.format("我是监听：%s",msg.toString());
        }
    }
}
