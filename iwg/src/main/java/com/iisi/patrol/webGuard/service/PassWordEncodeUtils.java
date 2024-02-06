package com.iisi.patrol.webGuard.service;

import org.apache.commons.lang3.StringUtils;

import java.util.Base64;

public class PassWordEncodeUtils {

    public final static String salt = "iwg";

    public static String encodePassword(String password){
        byte[] encodedBytes = Base64.getEncoder().encode((PassWordEncodeUtils.salt+password).getBytes());
        String encodedStr = new String(encodedBytes);
        return encodedStr;
    }

    public static String decodePassword(String encodedPassword){
        byte[] decodedBytes = Base64.getDecoder().decode(encodedPassword.getBytes());
        String decodedStr = new String(decodedBytes);
        if(StringUtils.isNotBlank(decodedStr)){
            return decodedStr.substring(3);
        }else{
            //不確定要拋錯還是傳空字串
            return "";
        }
    }
}
