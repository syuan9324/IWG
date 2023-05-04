package com.iisi.patrol.webGuard.service;


import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Check {

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        String filePath = "C:\\Users\\2106017\\comparison\\origin\\test1234.txt";
        byte[] data = Files.readAllBytes(Paths.get(filePath));
        byte[] hash = MessageDigest.getInstance("MD5").digest(data);
        String checksum = new BigInteger(1, hash).toString(16);
        System.out.println(checksum);
    }
}
