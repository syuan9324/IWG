package com.iisi.pccdeploy.service;

import com.iisi.pccdeploy.utils.CommonSshUtils;
import com.jcraft.jsch.JSchException;


public class CheckDeployFinishThread implements Runnable {

    private ConnectionConfig connectionConfig;

    private String warName;

    private String jbossHome;

    private String status = "N";

    public CheckDeployFinishThread(ConnectionConfig connectionConfig, String warName,String jbossHome) {
        this.connectionConfig = connectionConfig;
        this.warName = warName;
        this.jbossHome = jbossHome;
    }

    @Override
    public void run() {
        int count = 1;
        while (true){
            try {
                if(count==1){
                    // deploy 通常要比較久 第一次就等1分半
                    Thread.sleep(90000);
                }else{
                    Thread.sleep(10000);
                }

                //判斷是否剛好有兩個
                String command = String.format("ls %sstandalone/deployments/%s* | wc -l", jbossHome, warName);//ls /home/tailinh/wildfly26/wildfly/standalone/deployments/pwc-rest.war* | wc -l
                String fileCount = CommonSshUtils.useSshCommand(connectionConfig,command);

                //判斷是否有deployed //test -e /home/tailinh/wildfly26/wildfly/standalone/deployments/pwc-rest.war.deployed && echo file exists || echo file not found
                String checkDeployedCommand = String.format("test -e %sstandalone/deployments/%s.deployed && echo file exists || echo file not found",jbossHome, warName);
                String result =  CommonSshUtils.useSshCommand(connectionConfig,checkDeployedCommand);
                System.out.println("fileCount:"+fileCount);
                System.out.println("result:"+result);

                if("2".equals(fileCount) && "file exists".equals(result)){
                    status = "Y";
                    return;
                }
                count++;


            } catch (InterruptedException | JSchException e) {
                e.printStackTrace();
            }
            //超過 90秒 + 190秒 => time out
            if(count == 20) {
                System.out.println("=========time out======");
                status = "N";
                return;
            }
            if(Thread.interrupted()) {
                System.out.println("=========interrupted======");
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