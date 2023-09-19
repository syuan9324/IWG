package com.iisi.patrol.webGuard.service;

import java.util.Arrays;
import java.util.List;

public class ArrayTest {

    public static void main(String[] args) {
        String toList  = null;
        List<String> receiverList = Arrays.asList(toList.split(",", -1));
        receiverList.forEach(System.out::println);
    }
}
