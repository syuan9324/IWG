package com.iisi.pccdeploy.utils;

import com.iisi.pccdeploy.service.ConnectionConfig;
import com.iisi.pccdeploy.service.SingleServerDeployService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
public class WrappedDeployService {

    private final Logger log = LoggerFactory.getLogger(WrappedDeployService.class);
    private final DeployService deployService;

    public WrappedDeployService(DeployService deployService) {
        this.deployService = deployService;
    }

    public void test(){
        ExecutorService executor = Executors.newFixedThreadPool(3);
        ConnectionConfig conf = new ConnectionConfig();
        conf.setServerIp("192.168.57.202");
        conf.setPort(22);
        conf.setUserName("tailinh");
        conf.setPassWord("IIsi@940450");

        String warName = "pwc-rest.war";
        String jbossHome = "/home/tailinh/wildfly26/wildfly/";
        String localWarFileLocation = "C:\\\\";
        Future future1 = executor.submit(() -> {
            try {
                System.out.println("^^^^^^^future1^^^^^^^");
                Thread.sleep(240000);
                deployAndRestartServerWithConnectionConfigAndWarName(conf,warName,jbossHome,localWarFileLocation);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Future future2 = executor.submit(() -> {
            try {
                System.out.println("^^^^^^^future2^^^^^^^");
                deployAndRestartServerWithConnectionConfigAndWarName(conf,warName,jbossHome,localWarFileLocation);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        try {
            future1.get(600000, TimeUnit.SECONDS);
            future2.get(600000, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            future1.cancel(true);
            future2.cancel(true);
        } catch (Exception e) {
            // handle other exceptions
            e.printStackTrace();
        } finally {
            System.out.println("^^^^^^^finish^^^^^^^^");
            executor.shutdownNow();

        }

    }


    public boolean deployAndRestartServerWithConnectionConfigAndWarName(ConnectionConfig connectionConfig,String warName,String jbossHome,String localWarFileLocation) throws InterruptedException {
        //======step1: check server is running or not======
        log.info("======step1: check server :{} is running or not======",(connectionConfig.getServerIp()+":"+warName));

        //if server is stopped, pid is empty
        while (true){
            String pid = deployService.getRunningServiceProcessId(connectionConfig,jbossHome);
            if(StringUtils.isNotBlank(pid)){
                //server is running(common condition)

                //======step2: check war is deployed======
                log.info("======step2: check war is deployed :{} ======",(connectionConfig.getServerIp()+":"+warName));
                if(!deployService.checkOldWarIsUndeploy(connectionConfig,warName,jbossHome)){
                    //======step3: if war file is now deployed undeploy it======
                    log.info("======step3: if war file is now deployed undeploy it:{} ======",(connectionConfig.getServerIp()+":"+warName));
                    deployService.undeployWarWithName(connectionConfig,warName,jbossHome);
                }
                //======step4: stop server======
                log.info("======step4: stop server :{}======",(connectionConfig.getServerIp()+":"+warName));
                deployService.stopServerWithPid(connectionConfig,pid);

                //wait for server down
                Thread.sleep(2000);
                break;
            }else{
                break;
            }
        }

        //server is stopped

        //======step5: remove old war and mark file======
        log.info("======step5: remove old war and mark file :{}=====",(connectionConfig.getServerIp()+":"+warName));
        deployService.removeOldWarAndMarkerFilesWithName(connectionConfig,warName,jbossHome);
        Thread.sleep(1000);

        //======step6: upload war file======
        log.info("======step6: upload war file :{}======",(connectionConfig.getServerIp()+":"+warName));
        deployService.uploadWar(connectionConfig,localWarFileLocation,warName,jbossHome);
        Thread.sleep(1000);

        //======step7: start server======
        log.info("======step7: start server :{}======",(connectionConfig.getServerIp()+":"+warName));
        deployService.runServer(connectionConfig,jbossHome);

        //======step8: check war is deployed ======
        log.info("======step8: check war is deployed :{} ======",(connectionConfig.getServerIp()+":"+warName));
        boolean result = deployService.checkWarDeployedOrNotWithName(connectionConfig,warName,jbossHome);
        log.info("======step9: war is deployed :{} ======",(connectionConfig.getServerIp()+":"+warName));
        return result;

    }


    public boolean deployNoRestartServerWithConnectionConfigAndWarName(ConnectionConfig connectionConfig,String warName,String jbossHome,String localWarFileLocation) throws InterruptedException {
        // 判斷server 是否活著
        // 如果活著(通常情況)
        //        要先確認undeploy
        //        再移除檔案,
        //        再放上新的war,
        //        確定deployed
        // 如果server是停的(通常不會)
        //        直接移除檔案
        //        然後放上新的war
        //        不理他
        String pid = deployService.getRunningServiceProcessId(connectionConfig,jbossHome);

        if(StringUtils.isNotBlank(pid)){//server running

            //要先確認undeploy
            System.out.println("====== undeploy war file ======"+connectionConfig.getServerIp()+":"+warName);
            deployService.undeployWarWithName(connectionConfig,warName,jbossHome);

            //再移除檔案
            System.out.println("====== remove old war and mark file====="+connectionConfig.getServerIp()+":"+warName);
            deployService.removeOldWarAndMarkerFilesWithName(connectionConfig,warName,jbossHome);
            Thread.sleep(1000);

            //再放上新的war
            System.out.println("====== upload war file======"+connectionConfig.getServerIp()+":"+warName);
            deployService.uploadWar(connectionConfig,localWarFileLocation,warName,jbossHome);
            Thread.sleep(1000);

            //確定deployed
            System.out.println("====== check war is deployed ======"+connectionConfig.getServerIp()+":"+warName);
            boolean result = deployService.checkWarDeployedOrNotWithName(connectionConfig,warName,jbossHome);
            return result;

        }else{  //server stop
            //        直接移除檔案
            System.out.println("====== remove old war and mark file====="+connectionConfig.getServerIp()+":"+warName);
            deployService.removeOldWarAndMarkerFilesWithName(connectionConfig,warName,jbossHome);
            Thread.sleep(1000);

            //        然後放上新的war
            System.out.println("====== upload war file======"+connectionConfig.getServerIp()+":"+warName);
            deployService.uploadWar(connectionConfig,localWarFileLocation,warName,jbossHome);
            Thread.sleep(1000);
            //        不理他
            return true;
        }
    }

}
