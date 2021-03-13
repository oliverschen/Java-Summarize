package com.github.oliverschen.common;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @author ck
 */
public class JsonUtil {

    private  static ObjectMapper om = new ObjectMapper();

    static {
        //FAIL_ON_UNKNOWN_PROPERTIES在序列化的时候，如果遇到不认识的字段的处理方式
        //默认启用特性，这意味着在遇到未知属性时抛出JsonMappingException。在引入该特性之前，这是默认的默认设置。
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // Java8 新时间日期类型支持
        om.registerModule(new JavaTimeModule());
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 注入字段类型「类信息」
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    }

    /**
     * 将json转换成对象Class
     */
    public static <T> T json2Object(String src, Class<T> clazz) throws IOException {
        if (StringUtils.isEmpty(src) || clazz == null) {
            return null;
        }
        return clazz.equals(String.class) ? (T) src : om.readValue(src, clazz);
    }

}
