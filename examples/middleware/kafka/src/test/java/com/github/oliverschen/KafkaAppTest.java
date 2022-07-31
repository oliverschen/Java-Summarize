package com.github.oliverschen;


import com.github.oliverschen.config.KafkaSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Unit test for simple App.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class KafkaAppTest {

    @Autowired
    private KafkaSender sender;

    @Test
    public void test() {
        while (true) {
            sender.send(String.valueOf(ThreadLocalRandom.current().nextLong()));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }

    }
}
