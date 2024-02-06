package com.iisi.patrol.webGuard.service;

import com.iisi.patrol.webGuard.service.sshService.ConnectionConfig;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * hard code connection in service for test
 * 之後要改從db 拿資料
 */
@Component
public class ConnectionConfigService {

    private final List<ConnectionConfig> connectionConfigs;


    public ConnectionConfigService() {
        this.connectionConfigs = new ArrayList<>();
        this.connectionConfigs.add(new ConnectionConfig("192.168.57.202","tailinh","IIsi@940450",22));
    }

    public List<ConnectionConfig> getConnectionList(){
        return this.connectionConfigs;
    }

}
