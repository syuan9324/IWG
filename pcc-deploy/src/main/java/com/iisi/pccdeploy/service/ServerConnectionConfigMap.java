package com.iisi.pccdeploy.service;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ServerConnectionConfigMap {

    private final HostConnectionProperties hostConnectionProperties;

    public ServerConnectionConfigMap(HostConnectionProperties hostConnectionProperties) {
        this.hostConnectionProperties = hostConnectionProperties;
    }

    public Map<String, ConnectionConfig> getTestMap() {
        return hostConnectionProperties.getUatMap();
    }

    public Map<String, ConnectionConfig> getUatMap() {
        return hostConnectionProperties.getUatMap();
    }

    public Map<String, ConnectionConfig> getProdMap() {
        return hostConnectionProperties.getUatMap();
    }

}
