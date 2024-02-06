package com.iisi.pccdeploy.service;

import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LongRunningTask implements Runnable {

    @Override
    public void run() {
        int count = 1;
        while (true){
            try {
                Thread.sleep(10000);
                System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
                count++;


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(count == 10) {
                return;
            }
            if(Thread.interrupted()) {
                return;
            }
        }
    }
}