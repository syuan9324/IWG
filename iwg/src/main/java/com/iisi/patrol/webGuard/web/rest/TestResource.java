package com.iisi.patrol.webGuard.web.rest;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.iisi.patrol.webGuard.domain.HostProperty;
import com.iisi.patrol.webGuard.repository.AdmMailSendRepository;
import com.iisi.patrol.webGuard.repository.AdmSmsSendRepository;
import com.iisi.patrol.webGuard.service.*;
import com.iisi.patrol.webGuard.service.dto.IwgHostsDTO;
import com.iisi.patrol.webGuard.service.sshService.ConnectionConfig;
import com.jcraft.jsch.JSchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Profile("dev")
@RestController
@RequestMapping("/api")
public class TestResource {
    private final Logger log = LoggerFactory.getLogger(TestResource.class);

    @Autowired
    InMemoryHostMapService inMemoryHostMapService;
    @Autowired
    AdmMailSendRepository admMailSendRepository;
    @Autowired
    AdmSmsSendRepository admSmsSendRepository;
    @Autowired
    ScheduledTaskServiceTest scheduledTaskServiceTest;
    @Autowired
    ScheduledTaskService scheduledTaskService;
    @Autowired
    IwgHostsService iwgHostsService;
    @Autowired
    FileComparisonService fileComparisonService;
    @Autowired
    FileCacheService fileCacheService;

    public ConnectionConfig connectionConfig = new ConnectionConfig("192.168.57.202","tailinh","IIsi@940450",22);

    @GetMapping("/testResource")
    public String testResource(){
        log.info("check resource");
        return "get resource";
    }

    @PostMapping("/service/testPermission")
    public String testPermission(@RequestBody String command) throws Exception {
        String result = CommonSSHUtils.useSshCommand(connectionConfig, command);
        //System.out.println(result);
        return result;
    }

    @GetMapping("/service/testResource")
    public String testSecurityResource(){
        log.info("check security resource");
        return "get security resource";
    }

    @GetMapping("/service/ssh")
    public String testSsh(){
        try {
            CommonSSHUtils.useSshCommand(connectionConfig,"ls -l");
        }  catch (Exception e){
            e.printStackTrace();
        }

        File file = new File("C://pwc-web.war");
        long size = file.length();
        System.out.println("size"+size);

        return "ok";
    }

    @GetMapping("/service/testSizeCompare")
    public String testSizeCompare(){
        //
        Map<String, HostProperty> map = inMemoryHostMapService.getHostMap();
        //HostProperty connectionProperty = map.get("202");
        map.forEach((k,v) -> {
            try {
//                CommonSSHUtils.testScpCommand(v.getUserName(),v.getPassword(),v.getHostName(),v.getPort());
            }  catch (Exception e){
                e.printStackTrace();
            }
            File fileLocal = new File("C://pwc-web.war");
            File fileRemote = new File("C:\\Users\\2106017\\pwc-web.war");
            long fileLocalSize = fileLocal.length();
            long fileRemoteSize = fileRemote.length();
            System.out.println("fileLocalSize:"+fileLocalSize);
            System.out.println("fileRemoteSize"+fileRemoteSize);
            if(fileLocalSize!=fileRemoteSize){
                //admMailSendRepository.warnMail();
                admSmsSendRepository.warnSms();
            }
        });

        return "ok";
    }
    @GetMapping("/service/testFileSizeCompareProd")
    public void testFileSizeCompareProd(){
        scheduledTaskService.doFileComparison();
    }


    @GetMapping("/service/testFileSizeCompare")
    public void testFileSizeCompare(){
        scheduledTaskServiceTest.doDevFileComparison();
    }

    @GetMapping("/service/testMapper")
    public IwgHostsDTO testMapper(){
        return iwgHostsService.findById(1l);
    }


    @GetMapping("/service/scpLocalToRemote")
    public void testScpLocalToRemote() throws JSchException, IOException {
        CommonSSHUtils.useScpCopyLocalFileToRemote(connectionConfig,"C:\\Users\\2106017\\comparison\\origin\\","/home/tailinh/","test12345.txt");
    }

    @PostMapping("/service/test/ssh")
    public String testPostMapCommand(@RequestBody String command) throws Exception {
        String result = CommonSSHUtils.useSshCommand(connectionConfig, command);
        //String[] fileListString = result.split("\n");
        Map<String,String> filesInFolderSizeMap = new HashMap<>();
        List<String> fileListString = Arrays.asList(result.split("\n"));
//        fileListString.remove(0);
        for(String singleFile : fileListString){
            System.out.println("=========="+singleFile);
            List<String> fileDetailRow = Arrays.asList(singleFile.split(" "));
            filesInFolderSizeMap.put(fileDetailRow.get(1),fileDetailRow.get(0));
        }
        log.info("check map");
        for(Map.Entry<String,String> map : filesInFolderSizeMap.entrySet()){
            log.info("file name: {}, with size {}",map.getKey(),map.getValue());
        }
        Map<String, String> localFileMap = Stream.of(new File("C:\\Users\\2106017\\welcome-content").listFiles())
                .filter(file -> !file.isDirectory())
                .collect(Collectors.toMap(File::getName, x->Long.toString(x.length())));
        log.info("check local");
        for(Map.Entry<String,String> map : localFileMap.entrySet()){
            log.info("file name: {}, with size {}",map.getKey(),map.getValue());
        }
        MapDifference<String, String> diff = checkIsMapEqual(filesInFolderSizeMap, localFileMap);
        log.info("====checkIsMapEqual===={}",diff.areEqual());
        Map<String, MapDifference.ValueDifference<String>> ddd = diff.entriesDiffering();
        log.info("diff on ValueDifference size :{}",ddd.size());
        for(Map.Entry<String, MapDifference.ValueDifference<String>> map :ddd.entrySet()){
            log.info("ddd name: {}, with size {}",map.getKey(),map.getValue());
        }

        Map<String, String> diffOnRight = diff.entriesOnlyOnRight();
        log.info("diff on right size :{}",diffOnRight.size());
        Map<String, String> diffOnLeft = diff.entriesOnlyOnRight();
        log.info("diff on left size :{}",diffOnLeft.size());
        for(Map.Entry<String,String> map : diff.entriesOnlyOnRight().entrySet()){
            log.info("diff file name: {}, with size {}",map.getKey(),map.getValue());
        }
        return result;
    }



    public MapDifference<String,String> checkIsMapEqual(Map<String, String> origin, Map<String, String> fromServer) {
        return Maps.difference(origin, fromServer);
    }

    @GetMapping("/service/check-fcs-method")
    public void testFcsMethod() throws JSchException, IOException {
        String targetFolderName = "/opt/wildfly-4/welcome-content/";
        String originFolderName = "C:\\Users\\2106017\\welcome-content";
        Map<String, String> map1 = fileComparisonService.getServerFolderFilesAndMd5Map(connectionConfig, targetFolderName);
        Map<String, String> map2 = fileCacheService.getOriginFolderFilesAndMd5Map(originFolderName);
        log.info("====map1====");
        for(Map.Entry<String,String> map : map1.entrySet()){
            log.info("file name: {}, with hash {}",map.getKey(),map.getValue());
        }
        log.info("====map2====");
        for(Map.Entry<String,String> map : map2.entrySet()){
            log.info("file name: {}, with hash {}",map.getKey(),map.getValue());
        }

    }

    @GetMapping("/service/doDevFileComparisonInMd5")
    public void doDevFileComparisonInMd5() throws JSchException, IOException {
        scheduledTaskServiceTest.doDevFileComparisonInMd5();
    }

    @GetMapping("/service/doDevFileComparison")
    public void doFileComparisonInMd5() throws JSchException, IOException {
        scheduledTaskService.doFileComparisonInMd5();
    }


    @GetMapping("/service/testCache")
    public Map<String, String> getOriginFolderFilesAndMd5Map(){
        return fileCacheService.getOriginFolderFilesAndMd5Map("C:\\welcome-content");
    }
}
