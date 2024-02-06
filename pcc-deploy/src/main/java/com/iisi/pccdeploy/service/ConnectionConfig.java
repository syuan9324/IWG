package com.iisi.pccdeploy.service;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ConnectionConfig {
    private String serverIp;
    private String userName;
    private String passWord;
    private Integer port;
}
