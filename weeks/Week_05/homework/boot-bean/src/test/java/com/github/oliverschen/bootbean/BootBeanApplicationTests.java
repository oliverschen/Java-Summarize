package com.github.oliverschen.bootbean;

import com.github.oliverschen.bean.School;
import com.github.oliverschen.bean.Student;
import com.github.oliverschen.bean.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class BootBeanApplicationTests {

    @Autowired
    @Qualifier("bootUser")
    private User bootUser;

    @Autowired
    @Qualifier("bootSchool")
    private School bootSchool;

    @Autowired
    @Qualifier("bootStudent")
    private Student bootStudent;

    @Test
    void contextLoads() {
        System.out.println(bootUser);
        System.out.println(bootSchool);
        System.out.println(bootStudent);
    }

}
