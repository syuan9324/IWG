package com.iisi.pccdeploy.service;

import com.iisi.pccdeploy.utils.CommonSshUtils;
import com.jcraft.jsch.JSchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class CheckUndeployFinishThread implements Runnable {

    private final Logger log = LoggerFactory.getLogger(CheckUndeployFinishThread.class);

    private ConnectionConfig connectionConfig;

    private String warName;

    private String jbossHome;

    private String status = "N";

    public CheckUndeployFinishThread(ConnectionConfig connectionConfig, String warName,String jbossHome) {
        this.connectionConfig = connectionConfig;
        this.warName = warName;
        this.jbossHome = jbossHome;
    }

    @Override
    public void run() {
        int count = 1;
        while (true){
            try {
                Thread.sleep(10000);
                //判斷是否剛好有兩個
                String command = String.format("ls %sstandalone/deployments/%s* | wc -l", jbossHome, warName);//ls /home/tailinh/wildfly26/wildfly/standalone/deployments/pwc-rest.war* | wc -l
                String fileCount = CommonSshUtils.useSshCommand(connectionConfig,command);

                //判斷是否有undeployed   test -e /home/tailinh/wildfly26/wildfly/standalone/deployments/pwc-rest.war.undeployed && echo file exists || echo file not found
                String checkUnDeployedCommand = String.format("test -e %sstandalone/deployments/%s.undeployed && echo file exists || echo file not found",jbossHome, warName);
                String checkFailedCommand = String.format("test -e %sstandalone/deployments/%s.failed && echo file exists || echo file not found",jbossHome, warName);
                String unDeployedResult =  CommonSshUtils.useSshCommand(connectionConfig,checkUnDeployedCommand);
                String failedResult =  CommonSshUtils.useSshCommand(connectionConfig,checkFailedCommand);
                log.info("fileCount: {}",fileCount);
                log.info("unDeployedResult: {}",unDeployedResult);
                log.info("failedResult: {}",failedResult);

                if("2".equals(fileCount) && ("file exists".equals(unDeployedResult)|| "file exists".equals(failedResult))){
                    status = "Y";
                    return;
                }
                count++;


            } catch (InterruptedException | JSchException e) {
                e.printStackTrace();
            }
            if(count == 10) {
                log.error("=====undeploy time out=====");
                status = "N";
                return;
            }
            if(Thread.interrupted()) {
                log.error("=====undeploy interrupted=====");
                return;
            }
        }
    }


    public ConnectionConfig getConnectionConfig() {
        return connectionConfig;
    }

    public void setConnectionConfig(ConnectionConfig connectionConfig) {
        this.connectionConfig = connectionConfig;
    }

    public String getWarName() {
        return warName;
    }

    public void setWarName(String warName) {
        this.warName = warName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}