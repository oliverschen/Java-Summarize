package com.github.oliverschen.homework.guava;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * @author ck
 * 字符串工具类
 */
public class StrTest {

    public static void main(String[] args) {
        // 拼接
        Joiner joiner = Joiner.on(",").skipNulls();
        System.out.println(joiner.join("g",null,"h",""));

        // 分割
        String str = "111,,222 , 333,444,,";
        Iterable<String> split = Splitter.on(",").trimResults().omitEmptyStrings().split(str);
        ArrayList<String> strings = Lists.newArrayList(split);
        System.out.println(strings);
    }
}
