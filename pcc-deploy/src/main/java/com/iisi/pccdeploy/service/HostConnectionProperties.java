package com.iisi.pccdeploy.service;

import lombok.Data;

import java.util.Map;

@Data
public class HostConnectionProperties {

    private  Map<String,ConnectionConfig> uatMap;
    private  Map<String,ConnectionConfig> prodMap;
}
