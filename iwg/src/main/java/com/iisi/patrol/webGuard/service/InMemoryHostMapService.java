package com.iisi.patrol.webGuard.service;

import com.iisi.patrol.webGuard.domain.HostProperty;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InMemoryHostMapService {

    private Map<String, HostProperty> hostMap = new HashMap<>();

    public InMemoryHostMapService(Map<String, HostProperty> hostMap) {
        this.hostMap = hostMap;
        initMap();
    }

    private void initMap(){
        HostProperty s202 = new HostProperty();
        s202.setPassword("IIsi@940450");
        s202.setUserName("tailinh");
        s202.setHostName("192.168.57.202");
        s202.setPort(22);

        this.hostMap.put("202",s202);
    }

    public Map<String, HostProperty> getHostMap() {
        return hostMap;
    }

    public void setHostMap(Map<String, HostProperty> hostMap) {
        this.hostMap = hostMap;
    }
}
