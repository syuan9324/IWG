package com.iisi.pccdeploy.service;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class ServerConnectionConfigMap {

    private final Map<String,ConnectionConfig> testMap = new HashMap<>();
    private final Map<String,ConnectionConfig> uatMap = new HashMap<>();
    private final Map<String,ConnectionConfig> prodMap = new HashMap<>();

    @PostConstruct
    private void initConnectionConfigMap () {
        testMap.put("192.168.57.202",new ConnectionConfig("192.168.57.202","tailinh","IIsi@345678",22));

        uatMap.put("10.100.211.15",new ConnectionConfig("10.100.211.15","iisiadmin","1qaz2wsx#EDC",22));
        uatMap.put("10.100.211.16",new ConnectionConfig("10.100.211.16","iisiadmin","1qaz2wsx#EDC",22));
        uatMap.put("10.100.211.17",new ConnectionConfig("10.100.211.17","iisiadmin","1qaz2wsx#EDC",22));
        uatMap.put("10.100.211.18",new ConnectionConfig("10.100.211.18","iisiadmin","1qaz2wsx#EDC",22));
        uatMap.put("10.100.211.51",new ConnectionConfig("10.100.211.51","iisiadmin","1qaz2wsx#EDC",22));
        uatMap.put("10.100.213.11",new ConnectionConfig("10.100.213.11","iisiadmin","1qaz2wsx#EDC",22));

        prodMap.put("10.100.11.11",new ConnectionConfig("10.100.11.11","iisiadmin","1qaz2wsx#EDC",22));
        prodMap.put("10.100.11.12",new ConnectionConfig("10.100.11.12","iisiadmin","1qaz2wsx#EDC",22));
        prodMap.put("10.100.11.13",new ConnectionConfig("10.100.11.13","iisiadmin","1qaz2wsx#EDC",22));
        prodMap.put("10.100.11.51",new ConnectionConfig("10.100.11.51","iisiadmin","1qaz2wsx#EDC",22));
        prodMap.put("10.100.13.1",new ConnectionConfig("10.100.13.1","iisiadmin","1qaz2wsx#EDC",22));
    }

    public Map<String, ConnectionConfig> getTestMap() {
        return testMap;
    }

    public Map<String, ConnectionConfig> getUatMap() {
        return uatMap;
    }

    public Map<String, ConnectionConfig> getProdMap() {
        return prodMap;
    }
}
