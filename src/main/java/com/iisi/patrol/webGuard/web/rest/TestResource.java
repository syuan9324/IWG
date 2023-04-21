package com.iisi.patrol.webGuard.web.rest;

import com.iisi.patrol.webGuard.domain.HostProperty;
import com.iisi.patrol.webGuard.service.CommonSSHUtils;
import com.iisi.patrol.webGuard.service.InMemoryHostMapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TestResource {
    private final Logger log = LoggerFactory.getLogger(TestResource.class);

    @Autowired
    InMemoryHostMapService inMemoryHostMapService;

    @GetMapping("/testResource")
    public String testResource(){
        log.info("check resource");
        return "get resource";
    }

    @GetMapping("/service/testResource")
    public String testSecurityResource(){
        log.info("check security resource");
        return "get security resource";
    }

    @GetMapping("/service/ssh")
    public String testSsh(){
        try {
            CommonSSHUtils.listFolderStructure("tailinh","IIsi@940450","192.168.57.202",22,"du -B 1 pwc-web.war");
        }  catch (Exception e){
            e.printStackTrace();
        }

        File file = new File("C://pwc-web.war");
        long size = file.length();
        System.out.println("size"+size);


        return "ok";
    }

//    @GetMapping("/service/scp")
//    public String testScp(){
//        try {
//            CommonSSHUtils.testScpCommand();
//        }  catch (Exception e){
//            e.printStackTrace();
//        }
//        return "ok";
//    }

    @GetMapping("/service/testSizeCompare")
    public String testSizeCompare(){
        //
        Map<String, HostProperty> map = inMemoryHostMapService.getHostMap();
        //HostProperty connectionProperty = map.get("202");
        map.forEach((k,v) -> {
            try {
                CommonSSHUtils.testScpCommand(v.getUserName(),v.getPassword(),v.getHostName(),v.getPort());
            }  catch (Exception e){
                e.printStackTrace();
            }
            File fileLocal = new File("C://pwc-web.war");
            File fileRemote = new File("C:\\Users\\2106017\\pwc-web.war");
            long fileLocalSize = fileLocal.length();
            long fileRemoteSize = fileRemote.length();
            System.out.println("fileLocalSize:"+fileLocalSize);
            System.out.println("fileRemoteSize"+fileRemoteSize);
        });

        return "ok";
    }
}
