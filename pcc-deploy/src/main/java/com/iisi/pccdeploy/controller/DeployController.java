package com.iisi.pccdeploy.controller;

import com.iisi.pccdeploy.service.ConnectionConfig;
import com.iisi.pccdeploy.service.ServerConnectionConfigMap;
import com.iisi.pccdeploy.service.SingleServerDeployService;
import com.iisi.pccdeploy.utils.CommonSshUtils;
import com.iisi.pccdeploy.utils.WrappedDeployService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@RestController
@RequestMapping("api")
public class DeployController {

    private final Logger log = LoggerFactory.getLogger(DeployController.class);

    static String testWarFileLocation = "C:\\\\";
    static String testJbossHome = "/home/tailinh/wildfly26/wildfly/";

    @Autowired
    WrappedDeployService wrappedDeployService;
    @Autowired
    SingleServerDeployService singleServerDeployService;
    @Autowired
    ServerConnectionConfigMap serverConnectionConfigMap;
    @Autowired
    CommonSshUtils commonSshUtils;

    @GetMapping("deploy-test-war")
    public String deployTestWarFile(@RequestParam("warName") String warName) throws Exception {
        Map<String, ConnectionConfig> confMap = serverConnectionConfigMap.getTestMap();
        ConnectionConfig conf = confMap.get("192.168.57.202");
        return wrappedDeployAndRestartServer(conf,warName,testJbossHome,testWarFileLocation)? "deploy finished" : "deploy failed";
    }

    @GetMapping("deploy-test-war-no-restart")
    public String deployTestWarFileNoRestart(@RequestParam("warName") String warName) throws Exception {
        Map<String, ConnectionConfig> confMap = serverConnectionConfigMap.getTestMap();
        ConnectionConfig conf = confMap.get("192.168.57.202");
        return wrappedDeployAndRestartServer(conf,warName,testJbossHome,testWarFileLocation)? "deploy finished" : "deploy failed";
    }

    /*
        =================================uat====================================
     */

    @GetMapping("deploy-uat-pwc-web-ui")
    public String deployUatPwcWebUi() throws Exception {
        Map<String, ConnectionConfig> confMap = serverConnectionConfigMap.getUatMap();
        ConnectionConfig conf = confMap.get("10.100.211.15");
        String warName = "pwc-web-ui.war";
        String uatJbossHome = "/home/iisiadmin/EAP-7.4.0/";
        String uatWarFileLocation = "C:\\Users\\iisi.tailin\\Desktop\\war-file\\";
        return wrappedDeployAndRestartServer(conf,warName,uatJbossHome,uatWarFileLocation)? "deploy finished" : "deploy failed";
    }

    @GetMapping("deploy-uat-pwc-web-test")
    public String deployUatPwcWebTest() throws Exception {
        Map<String, ConnectionConfig> confMap = serverConnectionConfigMap.getUatMap();
        ConnectionConfig conf = confMap.get("10.100.211.15");
        String warName = "pwc-web-test.war";
        String uatJbossHome = "/home/iisiadmin/EAP-7.4.0/";
        String uatWarFileLocation = "C:\\Users\\iisi.tailin\\Desktop\\war-file\\";
        return wrappedDeployAndRestartServer(conf,warName,uatJbossHome,uatWarFileLocation)? "deploy finished" : "deploy failed";
    }

    @GetMapping("deploy-uat-pwc-web")
    public String deployUatPwcWeb() throws Exception {
        Map<String, ConnectionConfig> confMap = serverConnectionConfigMap.getUatMap();
        ConnectionConfig conf = confMap.get("10.100.211.15");
        String warName = "pwc-web.war";
        String uatJbossHome = "/home/iisiadmin/EAP-7.4.0/";
        String uatWarFileLocation = "C:\\Users\\iisi.tailin\\Desktop\\war-file\\";
        return wrappedDeployAndRestartServer(conf,warName,uatJbossHome,uatWarFileLocation)? "deploy finished" : "deploy failed";
    }

    @GetMapping("deploy-uat-rest")
    public String deployUatRest() throws Exception {
        Map<String, ConnectionConfig> confMap = serverConnectionConfigMap.getUatMap();
        ConnectionConfig conf = confMap.get("10.100.211.51");
        singleServerDeployService.startDeployServer(conf);
        String warName = "pwc-rest.war";
        String uatJbossHome = "/home/iisiadmin/EAP-7.4.0/";
        String uatWarFileLocation = "C:\\Users\\iisi.tailin\\Desktop\\war-file\\";
        boolean res = wrappedDeployService.deployAndRestartServerWithConnectionConfigAndWarName(conf, warName, uatJbossHome, uatWarFileLocation);
        singleServerDeployService.endDeployServer(conf);
        return res? "deploy finished" : "deploy failed";
    }
    @GetMapping("deploy-uat-batch")
    public String deployUatBatch() throws Exception {
        Map<String, ConnectionConfig> confMap = serverConnectionConfigMap.getUatMap();
        ConnectionConfig conf = confMap.get("10.100.213.11");
        String warName = "pwc-batch.war";
        String uatJbossHome = "/home/iisiadmin/EAP-7.4.0/";
        String uatWarFileLocation = "C:\\Users\\iisi.tailin\\Desktop\\war-file\\";
        return wrappedDeployAndRestartServer(conf,warName,uatJbossHome,uatWarFileLocation)? "deploy finished" : "deploy failed";
    }


    @GetMapping("deploy-uat-pwc-web-all")
    public String deployUatPwcWebAll() throws Exception {
        Map<String, ConnectionConfig> confMap = serverConnectionConfigMap.getUatMap();
        ConnectionConfig ap1 = confMap.get("10.100.211.15");
        ConnectionConfig ap2 = confMap.get("10.100.211.16");
        ConnectionConfig ap3 = confMap.get("10.100.211.17");
        ConnectionConfig ap4 = confMap.get("10.100.211.18");
        String warName = "pwc-web.war";
        String uatJbossHome = "/home/iisiadmin/EAP-7.4.0/";
        String uatWarFileLocation = "C:\\Users\\iisi.tailin\\Desktop\\war-file\\";


        List<ConnectionConfig> configs = new ArrayList<>();
        List<Future> futures = new ArrayList<>();
        if(commonSshUtils.testConnect(ap1)) {
            System.out.println("****ap1 is ok****");
            configs.add(ap1);
        }
        if(commonSshUtils.testConnect(ap2)) {
            System.out.println("****ap2 is ok****");
            configs.add(ap2);
        }
        if(commonSshUtils.testConnect(ap3)) {
            System.out.println("****ap3 is ok****");
            configs.add(ap3);
        }
        if(commonSshUtils.testConnect(ap4)) {
            System.out.println("****ap4 is ok****");
            configs.add(ap4);
        }
        ExecutorService executor = Executors.newFixedThreadPool(configs.size());

        configs.forEach(ele->{
            Future tempFuture = executor.submit(() -> {
                try {
                    wrappedDeployAndRestartServer(ele, warName, uatJbossHome, uatWarFileLocation);
                } catch (Exception e) {
//                    e.printStackTrace();
                    throw new RuntimeException("time_out");
                }
            });
            futures.add(tempFuture);
        });
        try {
            futures.forEach(ele->{
                try {
                    ele.get(900, TimeUnit.SECONDS);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    ele.cancel(true);
                    e.printStackTrace();
                }
            });
        } finally {
            executor.shutdownNow();
        }

        return "deploy finished";
    }



    @GetMapping("deploy-uat-pwc-web-test-all")
    public String deployUatPwcWebTestAll() throws Exception {
        Map<String, ConnectionConfig> confMap = serverConnectionConfigMap.getUatMap();
        ConnectionConfig ap1 = confMap.get("10.100.211.15");
        ConnectionConfig ap2 = confMap.get("10.100.211.16");
        ConnectionConfig ap3 = confMap.get("10.100.211.17");
        ConnectionConfig ap4 = confMap.get("10.100.211.18");
        String warName = "pwc-web-test.war";
        String uatJbossHome = "/home/iisiadmin/EAP-7.4.0/";
        String uatWarFileLocation = "C:\\Users\\iisi.tailin\\Desktop\\war-file\\";


        List<ConnectionConfig> configs = new ArrayList<>();
        List<Future> futures = new ArrayList<>();
        if(commonSshUtils.testConnect(ap1)) {
            System.out.println("****ap1 is ok****");
            configs.add(ap1);
        }
        if(commonSshUtils.testConnect(ap2)) {
            System.out.println("****ap2 is ok****");
            configs.add(ap2);
        }
        if(commonSshUtils.testConnect(ap3)) {
            System.out.println("****ap3 is ok****");
            configs.add(ap3);
        }
        if(commonSshUtils.testConnect(ap4)) {
            System.out.println("****ap4 is ok****");
            configs.add(ap4);
        }
        ExecutorService executor = Executors.newFixedThreadPool(configs.size());

        configs.forEach(ele->{
            Future tempFuture = executor.submit(() -> {
                try {
                    wrappedDeployAndRestartServer(ele, warName, uatJbossHome, uatWarFileLocation);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            futures.add(tempFuture);
        });
        try {
            futures.forEach(ele->{
                try {
                    ele.get(900, TimeUnit.SECONDS);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    ele.cancel(true);
                    e.printStackTrace();
                }
            });
        } finally {
            executor.shutdownNow();
        }

        return "deploy finished";
    }

    @GetMapping("deploy-uat-batch-rest")
    public String deployUatBatchRest() throws Exception {
        Map<String, ConnectionConfig> confMap = serverConnectionConfigMap.getUatMap();
        ConnectionConfig conf = confMap.get("10.100.213.11");
        singleServerDeployService.startDeployServer(conf);
        String warName = "pwc-batch-rest.war";
        String uatJbossHome = "/home/iisiadmin/EAP-7.4.0/";
        String uatWarFileLocation = "C:\\Users\\iisi.tailin\\Desktop\\war-file\\";
        boolean res = wrappedDeployService.deployAndRestartServerWithConnectionConfigAndWarName(conf, warName, uatJbossHome, uatWarFileLocation);
        singleServerDeployService.endDeployServer(conf);
        return res? "deploy finished" : "deploy failed";
    }


    /*
        ===================================prod====================================
     */

    @GetMapping("deploy-prod-rest")
    public String deployProdRest() throws Exception {
        Map<String, ConnectionConfig> confMap = serverConnectionConfigMap.getProdMap();
        ConnectionConfig conf = confMap.get("10.100.11.51");
        String warName = "pwc-rest.war";
        String prodJbossHome = "/home/iisiadmin/EAP-7.4.0/";
        String prodWarFileLocation = "C:\\Users\\iisi.tailin\\Desktop\\dr-war-file\\";
        return wrappedDeployAndRestartServer(conf,warName,prodJbossHome,prodWarFileLocation)? "deploy finished" : "deploy failed";
    }

    @GetMapping("deploy-prod-batch")
    public String deployProdBatch() throws Exception {
        Map<String, ConnectionConfig> confMap = serverConnectionConfigMap.getProdMap();
        ConnectionConfig conf = confMap.get("10.100.13.1");
        String warName = "pwc-batch.war";
        String prodJbossHome = "/home/iisiadmin/EAP-7.4.0/";
        String prodWarFileLocation = "C:\\Users\\iisi.tailin\\Desktop\\dr-war-file\\";
        return wrappedDeployAndRestartServer(conf,warName,prodJbossHome,prodWarFileLocation)? "deploy finished" : "deploy failed";
    }

    @GetMapping("deploy-prod-pwc")
    public String deployProdPwc() throws Exception {
        Map<String, ConnectionConfig> confMap = serverConnectionConfigMap.getProdMap();
        ConnectionConfig ap1 = confMap.get("10.100.11.11");
        ConnectionConfig ap2 = confMap.get("10.100.11.12");
        ConnectionConfig ap3 = confMap.get("10.100.11.13");
        String warName = "pwc-web.war";
        String prodJbossHome = "/home/iisiadmin/EAP-7.4.0/";
        String prodWarFileLocation = "C:\\Users\\iisi.tailin\\Desktop\\dr-war-file\\";

        ExecutorService executor = Executors.newFixedThreadPool(2);

        //由於ap3有時候會關機 連不到

        if(commonSshUtils.testConnect(ap3)){
            //如果連的到 先部屬.12,.13
            Future future1 = executor.submit(() -> {
                try {
                    wrappedDeployAndRestartServer(ap2,warName,prodJbossHome,prodWarFileLocation);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            Future future2 = executor.submit(() -> {
                try {
                    wrappedDeployAndRestartServer(ap3,warName,prodJbossHome,prodWarFileLocation);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            try {
                future1.get(600, TimeUnit.SECONDS);
                future2.get(600, TimeUnit.SECONDS);
            } catch (TimeoutException e) {
                future1.cancel(true);
                future2.cancel(true);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                executor.shutdownNow();
            }
        }else{
            //如果連不到 就部屬.12就好
            wrappedDeployAndRestartServer(ap2,warName,prodJbossHome,prodWarFileLocation);
        }
        //最後在部屬.13
        return wrappedDeployAndRestartServer(ap1,warName,prodJbossHome,prodWarFileLocation)? "deploy finished" : "deploy failed";
    }



    private boolean wrappedDeployAndRestartServer(ConnectionConfig connectionConfig,String warName,String jbossHome,String localWarFileLocation) throws Exception{
        boolean res = false;
        try {
            singleServerDeployService.startDeployServer(connectionConfig);
            res = wrappedDeployService.deployAndRestartServerWithConnectionConfigAndWarName(connectionConfig, warName, jbossHome, localWarFileLocation);
            log.info("check res :{}",res);
            log.info("trying to stop single deploy service...");
            if (res) singleServerDeployService.endDeployServer(connectionConfig);
        }finally {
            singleServerDeployService.endDeployServer(connectionConfig);
        }
        return res;
    }

    private void testSingleServerDeploy(ConnectionConfig connectionConfig) throws Exception {

        singleServerDeployService.startDeployServer(connectionConfig);
        Thread.sleep(30000);
        singleServerDeployService.endDeployServer(connectionConfig);

    }

    @GetMapping("testSingleServerDeploy")
    public String testSingleServerDeploy() throws Exception {
        Map<String, ConnectionConfig> confMap = serverConnectionConfigMap.getProdMap();
        ConnectionConfig conf = confMap.get("10.100.13.1");
        testSingleServerDeploy(conf);
        return "ok";
    }

    //這個方法有問題,不要用
    @Deprecated
    private boolean wrappedDeployNoRestartServer(ConnectionConfig connectionConfig,String warName,String jbossHome,String localWarFileLocation) throws Exception{
        boolean res = false;
        try {
            singleServerDeployService.startDeployServer(connectionConfig);
            res = wrappedDeployService.deployNoRestartServerWithConnectionConfigAndWarName(connectionConfig, warName, jbossHome, localWarFileLocation);
        }finally {
            singleServerDeployService.endDeployServer(connectionConfig);
        }
        return res;
    }

    @GetMapping("checkServerStatus")
    public String checkServerStatus(){
        return "ok";
    }

    @GetMapping("testError")
    public String testError() throws Exception {
        if( 1 == 1){
            throw new Exception("test");
        }
        return "ok";
    }
}
