package com.iisi.pccdeploy.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

@Service
public class SingleServerDeployService {

    private final Logger log = LoggerFactory.getLogger(SingleServerDeployService.class);

    private static final Set<ConnectionConfig> currentDeployedServer = Collections.synchronizedSet(new HashSet<>());

    //一次只能有一台server在做事
    //定義一個map 存現在正在上版中的server

    /*
        開始部屬
        把connection config 加進去set 裡面,
        若該connection config 已經存在,
        就一次等20秒,但如果時間超過5分鐘,就拋錯

     */
    public boolean startDeployServer(ConnectionConfig connectionConfig) throws Exception {
        log.info("SSD start check server is busy or not  at :{}",connectionConfig.getServerIp());
        if (currentDeployedServer.contains(connectionConfig)){
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<?> future = executor.submit(()->{
                while (currentDeployedServer.contains(connectionConfig)){
                    try {
                        log.info("wait 20 sec to check server {} is free",connectionConfig.getServerIp());
                        Thread.sleep(20000);
                        log.info("server {} is now {}",connectionConfig.getServerIp(),currentDeployedServer.contains(connectionConfig)?"busy":"free");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            try {
                future.get(300, TimeUnit.SECONDS);
            } catch (TimeoutException e) {
                future.cancel(true);
                throw new Exception("server is busy");
            } catch (Exception e) {
                // handle other exceptions
                e.printStackTrace();
            } finally {
                log.info("startDeployServer executor shutdown");
                executor.shutdownNow();
            }
        }
        log.info("SSD start deploy server  at :{}",connectionConfig.getServerIp());
        currentDeployedServer.add(connectionConfig);
        return true;
    }

    public boolean endDeployServer(ConnectionConfig connectionConfig){
        log.info("SSD end deploy server  at :{}",connectionConfig.getServerIp());
        if(currentDeployedServer.contains(connectionConfig)){
            currentDeployedServer.remove(connectionConfig);
        }
        return true;
    }
}
