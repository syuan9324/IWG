package com.iisi.patrol.webGuard.service;

import com.iisi.patrol.webGuard.service.sshService.ConnectionConfig;
import com.iisi.patrol.webGuard.sys.SystemInformation;
import com.jcraft.jsch.JSchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileCheckService {
    private final Logger log = LoggerFactory.getLogger(FileCheckService.class);

    private final String checkFileLocation = "/mnt/stsdat/NFS_STATUS.txt";
    private final String mailReceivers = "2106017@iisigroup.com";

    private final IwgMailSendService iwgMailSendService;
    private final SystemInformation systemInformation;
    private final List<ConnectionConfig> connectionConfigs = new ArrayList<>();

    public FileCheckService(IwgMailSendService iwgMailSendService, SystemInformation systemInformation) {
        this.iwgMailSendService = iwgMailSendService;
        this.systemInformation = systemInformation;
        initConnectionConfigMap();
    }

    public void checkMountStatus() {
        connectionConfigs.forEach(this::doMountCheck);

    }

    private void doMountCheck(ConnectionConfig connectionConfig) {
        try {
            String cmd = String.format("test -e %s && echo file exists || echo file not found", checkFileLocation);
            String result = CommonSSHUtils.useSshCommand(connectionConfig, cmd);
            log.info("******* server:{} mount check result:{}", connectionConfig.getServerIp(), result);
            if (!"file exists".equals(result)) {
                //handle error
                log.warn("mount is dropped");
                String content = String.format("server:%s mount異常,請盡快確認", connectionConfig.getServerIp());
                iwgMailSendService.saveNfsWarningMailWithReceiverAndContent(mailReceivers,content);
            }
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    private void initConnectionConfigMap() {
        if (systemInformation.isDev()) initDevMap();
        if (systemInformation.isSit()) initSitMap();
        if (systemInformation.isUat()) initUatMap();
        if (systemInformation.isProd()) initProdMap();
    }

    private void initDevMap() {
        ConnectionConfig ap = new ConnectionConfig("192.168.57.202", "tailinh", PassWordEncodeUtils.decodePassword("aXdnSUlzaUA5NDA0NTA="), 22);
        this.connectionConfigs.add(ap);
    }

    private void initSitMap() {
        ConnectionConfig ap = new ConnectionConfig("192.168.57.202", "tailinh", PassWordEncodeUtils.decodePassword("aXdnSUlzaUA5NDA0NTA="), 22);
        this.connectionConfigs.add(ap);
    }

    private void initUatMap() {
        ConnectionConfig ap1 = new ConnectionConfig("10.100.211.15", "iisiadmin", PassWordEncodeUtils.decodePassword("aXdnMXFhejJ3c3gjRURD"), 22);
        ConnectionConfig ap2 = new ConnectionConfig("10.100.211.51", "iisiadmin", PassWordEncodeUtils.decodePassword("aXdnMXFhejJ3c3gjRURD"), 22);
        ConnectionConfig ap3 = new ConnectionConfig("10.100.213.11", "iisiadmin", PassWordEncodeUtils.decodePassword("aXdnMXFhejJ3c3gjRURD"), 22);
        this.connectionConfigs.add(ap1);
        this.connectionConfigs.add(ap2);
        this.connectionConfigs.add(ap3);
    }

    private void initProdMap() {
        ConnectionConfig ap1 = new ConnectionConfig("10.100.11.11", "iisiadmin", PassWordEncodeUtils.decodePassword("aXdnMXFhejJ3c3gjRURD"), 22);
        ConnectionConfig ap2 = new ConnectionConfig("10.100.11.12", "iisiadmin", PassWordEncodeUtils.decodePassword("aXdnMXFhejJ3c3gjRURD"), 22);
        ConnectionConfig ap3 = new ConnectionConfig("10.100.11.13", "iisiadmin", PassWordEncodeUtils.decodePassword("aXdnMXFhejJ3c3gjRURD"), 22);
        ConnectionConfig rest = new ConnectionConfig("10.100.11.51", "iisiadmin", PassWordEncodeUtils.decodePassword("aXdnMXFhejJ3c3gjRURD"), 22);
        ConnectionConfig batch = new ConnectionConfig("10.100.13.1", "iisiadmin", PassWordEncodeUtils.decodePassword("aXdnMXFhejJ3c3gjRURD"), 22);
        this.connectionConfigs.add(ap1);
        this.connectionConfigs.add(ap2);
        this.connectionConfigs.add(ap3);
        this.connectionConfigs.add(rest);
        this.connectionConfigs.add(batch);
    }

}
