package com.github.oliverschen;


import com.github.oliverschen.dto.UserDto;
import com.github.oliverschen.util.HbaseUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit test for simple App.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AppTest {

    @Autowired
    private HbaseUtils hbaseUtils;


    @Test
    public void testHbaseGet() {
        UserDto dto = hbaseUtils.getSingleRow("jr_pis_anti_detect", "1", "tf1", UserDto.class);
        System.out.println(dto);
    }
}
