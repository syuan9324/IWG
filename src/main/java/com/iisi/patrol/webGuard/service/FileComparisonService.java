package com.iisi.patrol.webGuard.service;

import com.iisi.patrol.webGuard.service.dto.IwgHostsDTO;
import com.iisi.patrol.webGuard.service.dto.IwgHostsTargetDTO;
import com.iisi.patrol.webGuard.service.enums.FileStatusEnums;
import com.iisi.patrol.webGuard.service.sshService.ConnectionConfig;
import com.jcraft.jsch.JSchException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

@Service
public class FileComparisonService {
    private static final Logger log = LoggerFactory.getLogger(FileComparisonService.class);

    private final IwgHostsLogsService iwgHostsLogsService;

    private final AdmMailSendService admMailSendService;

    public FileComparisonService(IwgHostsLogsService iwgHostsLogsService, AdmMailSendService admMailSendService) {
        this.iwgHostsLogsService = iwgHostsLogsService;
        this.admMailSendService = admMailSendService;
    }


    public void fileCompareByHostAndTargetList(IwgHostsDTO iwgHostsDTO, List<IwgHostsTargetDTO> iwgHostsTargetDTOs){
        for (IwgHostsTargetDTO targetDTO : iwgHostsTargetDTOs) {
            //取得要監控的file資訊
            String fileName = targetDTO.getFileName();
            String serverLocation = targetDTO.getTargetFileLocation();
            String fromServerLocation = targetDTO.getTargetInLocalLocation();
            String originLocation = targetDTO.getOriginFileLocation();

            log.info("check file name :{}", fileName);
            log.info("fromServerLocation path :{}", fromServerLocation);
            log.info("originLocation path :{}", originLocation);

            ConnectionConfig connectionConfig = new ConnectionConfig(iwgHostsDTO.getHostname(), iwgHostsDTO.getUsername(), PassWordEncodeUtils.decodePassword(iwgHostsDTO.getPassword()), iwgHostsDTO.getPort());
            try {
                Instant triggerTime = Instant.now();
                FileStatusEnums fileStatus = this.compareSizeByCommand(connectionConfig, fileName, serverLocation,originLocation);
                Instant finishTime = Instant.now();

                switch (fileStatus){
                    case FILE_SIZE_SAME:
                        log.info("check size of {} is normal",fileName);
                        iwgHostsLogsService.writeCheckNormalLog(iwgHostsDTO.getHostname(),iwgHostsDTO.getPort(),triggerTime,finishTime,serverLocation+fileName);
                        break;
                    case FILE_SIZE_DIFF:
                        // 1:寫log 2.替換檔案 3.寄信
                        log.warn("{} size is different", fileName);
                        iwgHostsLogsService.writeCheckFailLog(iwgHostsDTO.getHostname(),iwgHostsDTO.getPort(),triggerTime,finishTime,serverLocation+fileName,iwgHostsDTO.getSmsReceiver(),iwgHostsDTO.getMailReceiver());
                        CommonSSHUtils.useScpCopyLocalFileToRemote(connectionConfig,originLocation,serverLocation,fileName);
                        log.info("exchange diff file {} finish",fileName);
                        StringBuilder sb = new StringBuilder();
                        sb.append("主機:").append(iwgHostsDTO.getHostname()).append(",檔案路徑:").append(serverLocation+fileName).append(",該檔案有異動");
                        sb.append("\n並以替換為iwg主機中的正確版本");
                        admMailSendService.saveAdmMailWithReceiverAndContent(iwgHostsDTO.getMailReceiver(),sb.toString());
                        break;
                    case FILE_NOT_EXIST:
                        // 1:寫log 2.放上檔案 3.寄信
                        log.warn("{} is not exist", fileName);
                        iwgHostsLogsService.writeCheckFailLog(iwgHostsDTO.getHostname(),iwgHostsDTO.getPort(),triggerTime,finishTime,serverLocation+fileName,iwgHostsDTO.getSmsReceiver(),iwgHostsDTO.getMailReceiver());
                        CommonSSHUtils.useScpCopyLocalFileToRemote(connectionConfig,originLocation,serverLocation,fileName);
                        log.info("upload file {} finish",fileName);
                        StringBuilder sb1 = new StringBuilder();
                        sb1.append("主機:").append(iwgHostsDTO.getHostname()).append(",檔案路徑:").append(serverLocation+fileName).append(",該檔案不存在");
                        sb1.append("\n並上傳主機中的正確版本");
                        admMailSendService.saveAdmMailWithReceiverAndContent(iwgHostsDTO.getMailReceiver(),sb1.toString());
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public FileStatusEnums compareSizeByCommand(ConnectionConfig connectionConfig, String fileName, String serverLocation, String originLocation) throws Exception {

        String response = CommonSSHUtils.useSshCommand(connectionConfig, "du -b " + serverLocation + fileName);
        log.info(response);
        if(StringUtils.isNotBlank(response) && response.contains("No such file or directory")){
            return FileStatusEnums.FILE_NOT_EXIST;
        }
        String serverFileSize = response.split("\t")[0];
        Long localFileSize = new File(originLocation + fileName).length();
        int intLocalFileSize = localFileSize.intValue();
        int intServerFileSize = Integer.parseInt(serverFileSize);
        return intLocalFileSize == intServerFileSize ? FileStatusEnums.FILE_SIZE_SAME : FileStatusEnums.FILE_SIZE_DIFF;
    }
   }
