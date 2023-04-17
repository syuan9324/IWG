package com.iisi.patrol.webGuard.service.sshService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionConfig {

    private String serverIp;
    private String userName;
    private String passWord;


}
