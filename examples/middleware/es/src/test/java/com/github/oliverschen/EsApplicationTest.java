package com.github.oliverschen;

import com.github.oliverschen.service.EsService;
import org.elasticsearch.action.DocWriteResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EsApplicationTest {

    @Autowired
    private EsService esService;

    @Test
    public void createIndex() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1);
        map.put("name", "xihongshi");
        map.put("age", 12);
        map.put("city", "shenzhen");
        boolean result = esService.createIndex("lib");
        assertEquals(result, true);

    }

}
