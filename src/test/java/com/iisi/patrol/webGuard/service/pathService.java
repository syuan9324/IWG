package com.iisi.patrol.webGuard.service;

public class pathService {

    public static void main(String[] args) {
        String homePath = System.getProperty("user.home");
        System.out.println(homePath);

        String pwd = "IIsi@940450";
        String encodedPwd = PassWordEncodeUtils.encodePassword(pwd);
        System.out.println(encodedPwd);
        System.out.println(PassWordEncodeUtils.decodePassword(encodedPwd));

    }
}
