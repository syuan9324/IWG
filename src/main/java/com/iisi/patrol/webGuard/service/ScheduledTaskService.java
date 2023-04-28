package com.iisi.patrol.webGuard.service;

import com.iisi.patrol.webGuard.service.dto.IwgHostsDTO;
import com.iisi.patrol.webGuard.service.dto.IwgHostsTargetDTO;
import com.iisi.patrol.webGuard.service.sshService.ConnectionConfig;
import com.jcraft.jsch.JSchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

@Service
public class ScheduledTaskService {

    private final IwgHostsService iwgHostsService;

    private final IwgHostsTargetService iwgHostsTargetService;

    private final IwgHostsLogsService iwgHostsLogsService;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTaskService.class);


    public ScheduledTaskService(IwgHostsService iwgHostsService, IwgHostsTargetService iwgHostsTargetService, IwgHostsLogsService iwgHostsLogsService) {
        this.iwgHostsService = iwgHostsService;
        this.iwgHostsTargetService = iwgHostsTargetService;
        this.iwgHostsLogsService = iwgHostsLogsService;
    }

//    @Scheduled(cron = "0 0/5 * * * ?")
    public void doFileComparison() {
        List<IwgHostsDTO> hostList = iwgHostsService.findActive();
        hostList.forEach(iwgHostsDTO -> {
            log.info("start file compare");
            log.info("current host : {}", iwgHostsDTO);
            List<IwgHostsTargetDTO> iwgHostsTargetDTOs = iwgHostsTargetService.getIwgHostTargetByHost(iwgHostsDTO.getHostname(), iwgHostsDTO.getPort());
            log.info("check IwgHostsTargetLength : {}", iwgHostsTargetDTOs.size());
            for (IwgHostsTargetDTO targetDTO : iwgHostsTargetDTOs) {
                //取得要監控的file資訊
                String fileName = targetDTO.getFileName();//"pwc-web.war";
                String serverLocation = targetDTO.getTargetFileLocation();
                String fromServerLocation = targetDTO.getTargetInLocalLocation();
                String originLocation = targetDTO.getOriginFileLocation();

                log.info("check file name :{}", fileName);
                log.info("fromServerLocation path :{}", fromServerLocation);
                log.info("originLocation path :{}", originLocation);

                ConnectionConfig connectionConfig = new ConnectionConfig(iwgHostsDTO.getHostname(), iwgHostsDTO.getUsername(), iwgHostsDTO.getPassword(), iwgHostsDTO.getPort());
                try {
                    Instant triggerTime = Instant.now();
                    boolean response = this.compareSizeByCommand(connectionConfig, fileName, serverLocation,originLocation);
                    Instant finishTime = Instant.now();
                    if (response) {
                        log.info("check size of {} is normal",fileName);
                        iwgHostsLogsService.writeCheckNormalLog(iwgHostsDTO.getHostname(),iwgHostsDTO.getPort(),triggerTime,finishTime,serverLocation+fileName);
                    } else {
                        log.warn("{} size is different", fileName);
                        iwgHostsLogsService.writeCheckFailLog(iwgHostsDTO.getHostname(),iwgHostsDTO.getPort(),triggerTime,finishTime,serverLocation+fileName,iwgHostsDTO.getSmsReceiver(),iwgHostsDTO.getMailReceiver());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public boolean compareSizeByCommand(ConnectionConfig connectionConfig, String fileName, String serverLocation, String originLocation) throws Exception {

        String response = CommonSSHUtils.useSshCommand(connectionConfig, "du -b " + serverLocation + fileName);
        log.info(response);
        String serverFileName = response.split("\t")[1];
        String serverFileSize = response.split("\t")[0];
        Long localFileSize = new File(originLocation + fileName).length();
        int intLocalFileSize = localFileSize.intValue();
        int intServerFileSize = Integer.parseInt(serverFileSize);
        log.info("intLocalFileSize : {}",intLocalFileSize);
        log.info("intServerFileSize : {}",intServerFileSize);

        return intLocalFileSize == intServerFileSize;
    }


    public boolean fileSizeComparison(ConnectionConfig connectionConfig, String fileName, String serverLocation, String localLocation, String originLocation) throws JSchException, IOException {
        CommonSSHUtils.useScpCopyRemoteFile(connectionConfig, serverLocation, localLocation, fileName);
        //remote file size
        Long remoteFileSize = new File(localLocation + fileName).length();
        log.info("remote {} size is {}", fileName, remoteFileSize);

        //comparison file size
        Long comparedFileSize = new File(originLocation + fileName).length();
        log.info("local {} size is {}", fileName, remoteFileSize);
        log.info("judge size : {}", remoteFileSize.compareTo(comparedFileSize));
        return remoteFileSize.compareTo(comparedFileSize) == 0;

    }
}
