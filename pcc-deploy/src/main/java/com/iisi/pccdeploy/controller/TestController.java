package com.iisi.pccdeploy.controller;

import com.iisi.pccdeploy.service.ConnectionConfig;
import com.iisi.pccdeploy.utils.CommonSshUtils;
import com.iisi.pccdeploy.utils.DeployService;
import com.iisi.pccdeploy.utils.WrappedDeployService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class TestController {

    @Autowired
    CommonSshUtils commonSshUtils;
    @Autowired
    DeployService deployService;
    @Autowired
    WrappedDeployService wrappedDeployService;

    static String jbossHome = "/home/tailinh/wildfly26/wildfly/";
    static String warName = "pwc-rest.war";
    static String localWarFileLocation = "C:\\\\";

    @GetMapping("test")
    public void testUpload(){
        ConnectionConfig conf = new ConnectionConfig();
        conf.setServerIp("192.168.57.202");
        conf.setPort(22);
        conf.setUserName("tailinh");
        conf.setPassWord("IIsi@940450");
        commonSshUtils.uploadFile(conf,"C:\\output.txt","/home/tailinh/output.txt");
    }

    @GetMapping("testStop")
    public void testStop(){
        ConnectionConfig conf = new ConnectionConfig();
        conf.setServerIp("192.168.57.202");
        conf.setPort(22);
        conf.setUserName("tailinh");
        conf.setPassWord("IIsi@940450");
        String pid = deployService.getRunningServiceProcessId(conf,jbossHome);
        System.out.println("======check pid =====");
        System.out.println(pid);
        if(StringUtils.isNotBlank(pid)){
            deployService.stopServerWithPid(conf,pid);
        }
    }

    @GetMapping("testStart")
    public void testStart(){
        ConnectionConfig conf = new ConnectionConfig();
        conf.setServerIp("192.168.57.202");
        conf.setPort(22);
        conf.setUserName("tailinh");
        conf.setPassWord("IIsi@940450");
        String pid = deployService.getRunningServiceProcessId(conf,jbossHome);
        System.out.println("======check pid =====");
        System.out.println(pid);
        if(StringUtils.isBlank(pid)){
            deployService.runServer(conf,jbossHome);
        }
    }

    @GetMapping("testDeploy")
    public boolean testDeploy() throws InterruptedException {
        ConnectionConfig conf = new ConnectionConfig();
        conf.setServerIp("192.168.57.202");
        conf.setPort(22);
        conf.setUserName("tailinh");
        conf.setPassWord("IIsi@940450");
        //======step1: check server is running or not======
        System.out.println("======step1: check server is running or not======");

        //if server is stopped, pid is empty
        while (true){
            String pid = deployService.getRunningServiceProcessId(conf,jbossHome);
            if(StringUtils.isNotBlank(pid)){
                //server is running(common condition)

                //======step2: check war is deployed======
                System.out.println("======step2: check war is deployed======");
                if(!deployService.checkOldWarIsUndeploy(conf,warName,jbossHome)){
                    //======step3: if war file is now deployed undeploy it======
                    System.out.println("======step3: if war file is now deployed undeploy it======");
                    deployService.undeployWarWithName(conf,warName,jbossHome);
                }
                //======step4: stop server======
                System.out.println("======step4: stop server======");
                deployService.stopServerWithPid(conf,pid);

                //wait for server down
                Thread.sleep(2000);
                break;
            }else{
                break;
            }
        }

        //server is stopped

        //======step5: remove old war and mark file======
        System.out.println("======step5: remove old war and mark file=====");
        deployService.removeOldWarAndMarkerFilesWithName(conf,warName,jbossHome);
        Thread.sleep(1000);

        //======step6: upload war file======
        System.out.println("======step6: upload war file======");
        deployService.uploadWar(conf,localWarFileLocation,warName,jbossHome);
        Thread.sleep(1000);

        //======step7: start server======
        System.out.println("======step7: start server======");
        deployService.runServer(conf,jbossHome);

        //======step8: check war is deployed ======
        System.out.println("======step8: check war is deployed ======");
        boolean result = deployService.checkWarDeployedOrNotWithName(conf,warName,jbossHome);
        return result;
    }
    @GetMapping("undeployWarWithName")
    public void undeployWarWithName(){
        ConnectionConfig conf = new ConnectionConfig();
        conf.setServerIp("192.168.57.202");
        conf.setPort(22);
        conf.setUserName("tailinh");
        conf.setPassWord("IIsi@940450");
        deployService.undeployWarWithName(conf,warName,jbossHome);
    }


    @GetMapping("testMultiThread")
    public void testMultiThread(){
        wrappedDeployService.test();
    }
    @GetMapping("testNotAliveServer")
    public void testNotAliveServer(){
        ConnectionConfig conf = new ConnectionConfig();
        conf.setServerIp("192.168.57.202");
        conf.setPort(22);
        conf.setUserName("tailinh");
        conf.setPassWord("IIsi@940450");
        boolean res = commonSshUtils.testConnect(conf);
        System.out.println(res);

    }
}
