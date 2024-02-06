package com.iisi.pccdeploy.utils;

import com.iisi.pccdeploy.service.CheckDeployFinishThread;
import com.iisi.pccdeploy.service.CheckUndeployFinishThread;
import com.iisi.pccdeploy.service.ConnectionConfig;
import com.jcraft.jsch.JSchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
public class DeployService {

    private final Logger log = LoggerFactory.getLogger(DeployService.class);

    private final CommonSshUtils commonSshUtils;

    public DeployService(CommonSshUtils commonSshUtils) {
        this.commonSshUtils = commonSshUtils;
    }

    public String getRunningServiceProcessId(ConnectionConfig connectionConfig,String jbossHome) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("ps -ef | grep %s | grep java | grep -v grep | awk '{print $2}'",jbossHome));

        try {
            return CommonSshUtils.useSshCommand(connectionConfig, sb.toString());
        } catch (JSchException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void stopServerWithPid(ConnectionConfig connectionConfig, String pid) {
        try {
            CommonSshUtils.useSshCommand(connectionConfig, "kill -9 " + pid);
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    public void runServer(ConnectionConfig connectionConfig, String jbossHome) {
        StringBuilder sb = new StringBuilder();
        sb.append("nohup ");
        sb.append(jbossHome+"bin");
        sb.append("/standalone.sh 1>/dev/null 2>/dev/null &");
        try {
            CommonSshUtils.useSshCommand(connectionConfig, sb.toString());
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }


    public boolean undeployWarWithName(ConnectionConfig connectionConfig, String warName, String jbossHome) {
        String status = "N";
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("rm -f %sstandalone/deployments/%s.deployed",jbossHome,warName));
        try {
            //CommonSshUtils.useSshCommand(connectionConfig, "rm -f /home/tailinh/wildfly26/wildfly/standalone/deployments/pwc-rest.war.deployed");
            log.info("*******kill .deployed****");
            log.info("cmd:{}",sb);
            CommonSshUtils.useSshCommand(connectionConfig, sb.toString());
            ExecutorService executor = Executors.newSingleThreadExecutor();
            CheckUndeployFinishThread thread = new CheckUndeployFinishThread(connectionConfig, warName,jbossHome);
            Future future = executor.submit(thread);
            try {
                future.get(120, TimeUnit.SECONDS);
            } catch (TimeoutException e) {
                future.cancel(true);
                throw new RuntimeException("time out");
            } catch (Exception e) {
                // handle other exceptions
            } finally {
                log.info("*** check undeploy with status :{} ***",thread.getStatus());
                status = thread.getStatus();
                executor.shutdownNow();
            }
        } catch (JSchException e) {
            e.printStackTrace();
        }
        if(status.equals("N")){
            throw new RuntimeException("time out");
        }
        return status.equals("Y");
    }

    public boolean checkWarDeployedOrNotWithName(ConnectionConfig connectionConfig, String warName, String jbossHome) {
        String status = "N";

        ExecutorService executor = Executors.newSingleThreadExecutor();
        CheckDeployFinishThread thread = new CheckDeployFinishThread(connectionConfig, warName,jbossHome);
        Future future = executor.submit(thread);
        try {
            future.get(300, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            future.cancel(true);
            throw new RuntimeException("time out");
        } catch (Exception e) {
            // handle other exceptions
            e.printStackTrace();
        } finally {
            System.out.println("=====check status======");
            System.out.println(thread.getStatus());
            status = thread.getStatus();
            executor.shutdownNow();
        }
        if(status.equals("N")){
            throw new RuntimeException("time out");
        }

        return status.equals("Y");
    }

    public void removeOldWarAndMarkerFilesWithName(ConnectionConfig connectionConfig, String warName,String jbossHome) {
        try {
            //"rm -f /home/tailinh/wildfly26/wildfly/standalone/deployments/pwc-rest.war*"
            String cmd = String.format("rm -f %sstandalone/deployments/%s*", jbossHome, warName);
            CommonSshUtils.useSshCommand(connectionConfig, cmd);
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    public boolean checkOldWarIsUndeploy(ConnectionConfig connectionConfig, String warName,String jbossHome) {
//        String command = "test -e /home/tailinh/wildfly26/wildfly/standalone/deployments/pwc-rest.war.undeployed && echo file exists || echo file not found";
        String command = String.format("test -e %sstandalone/deployments/%s.undeployed && echo file exists || echo file not found",jbossHome,warName);

        try {
            String result = CommonSshUtils.useSshCommand(connectionConfig, command);
            return "file exists".equals(result);
        } catch (JSchException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void uploadWar(ConnectionConfig connectionConfig,String localFileDirect,String warName,String jbossHome) {
        String localFilePath = String.format("%s%s", localFileDirect, warName);
        String remotePathName = String.format("%sstandalone/deployments/%s", jbossHome,warName);

//        commonSshUtils.uploadFile(connectionConfig, "C:\\pwc-rest.war", "/home/tailinh/wildfly26/wildfly/standalone/deployments/pwc-rest.war");
        commonSshUtils.uploadFile(connectionConfig, localFilePath, remotePathName);
    }
}
