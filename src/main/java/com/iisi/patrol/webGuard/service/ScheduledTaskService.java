package com.iisi.patrol.webGuard.service;

import com.iisi.patrol.webGuard.service.dto.IwgHostsDTO;
import com.iisi.patrol.webGuard.service.dto.IwgHostsTargetDTO;
import com.iisi.patrol.webGuard.service.sshService.ConnectionConfig;
import com.jcraft.jsch.JSchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Profile("dev")
public class ScheduledTaskService {

    private final ConnectionConfigService connectionConfigService;

    private final IwgHostsService iwgHostsService;

    private final IwgHostsTargetService iwgHostsTargetService;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTaskService.class);


    public ScheduledTaskService(ConnectionConfigService connectionConfigService, IwgHostsService iwgHostsService, IwgHostsTargetService iwgHostsTargetService) {
        this.connectionConfigService = connectionConfigService;
        this.iwgHostsService = iwgHostsService;
        this.iwgHostsTargetService = iwgHostsTargetService;
    }

    public void doFileComparison(){
        log.info("start file compare");
        IwgHostsDTO iwgHostsDTO = iwgHostsService.findById(5);
        List<IwgHostsTargetDTO> iwgHostsTargetDTOs = iwgHostsTargetService.getIwgHostTargetByHost(iwgHostsDTO.getHostname(),iwgHostsDTO.getPort());
        log.info("check iwgHostsDTO : {}",iwgHostsDTO.toString());
        log.info("check IwgHostsTargetDTO : {}",iwgHostsTargetDTOs.get(0).toString());
        IwgHostsTargetDTO target = iwgHostsTargetDTOs.get(0);

        String fileName = target.getFileName();//"pwc-web.war";
        String serverLocation =  target.getTargetFileLocation();
        String fromServerLocation = target.getTargetInLocalLocation();
        String originLocation = target.getOriginFileLocation();

        log.info("fromServerLocation path :{}",fromServerLocation);
        log.info("originLocation path :{}",originLocation);

        ConnectionConfig connectionConfig = new ConnectionConfig(iwgHostsDTO.getHostname(),iwgHostsDTO.getUsername(),iwgHostsDTO.getPassword(),iwgHostsDTO.getPort());
        try {
            //String response = CommonSSHUtils.useSshCommand(connectionConfig, "du -B 1 pwc-web.war");

            boolean response = this.fileSizeComparison(connectionConfig, fileName, serverLocation, fromServerLocation, originLocation);
            if (response) {
                long remoteFileSize = new File(fromServerLocation + fileName).length();
                long comparedFileSize = new File(originLocation + fileName).length();
                log.info("size of origin {} is {},size of {} from server is {}", fileName, comparedFileSize, fileName, remoteFileSize);
                log.info("check {} size normal", fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    /**
     * 以下都是測試用
     */


    /**
     * 測試一下job
     */
    // @Scheduled(cron = "0/5 * * * * ?")
    public void testTask() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        log.debug(strDate);
    }

    /**
     * 比對檔案大小
     */
    // @Scheduled(cron = "0 0/5 * * * ?")
    public void fileComparisonForDev() {
        log.info("start file compare");
        String fileName = "pwc-web.war";
        String serverLocation = "/home/tailinh/";//在window下啟動， file separator會是'\',會找不到檔案
        String fromServerLocation = System.getProperty("user.home") + File.separator + "comparison" + File.separator + "fromServer"+ File.separator;
        String originLocation = System.getProperty("user.home") + File.separator + "comparison" + File.separator + "origin"+ File.separator;
        log.info("fromServerLocation path :{}",fromServerLocation);
        log.info("originLocation path :{}",originLocation);
        List<ConnectionConfig> configs = connectionConfigService.getConnectionList();
        configs.stream().forEach(connectionConfig -> {
            try {
                //String response = CommonSSHUtils.useSshCommand(connectionConfig, "du -B 1 pwc-web.war");

                boolean response = this.fileSizeComparison(connectionConfig, fileName, serverLocation, fromServerLocation, originLocation);
                if (response) {
                    long remoteFileSize = new File(fromServerLocation + fileName).length();
                    long comparedFileSize = new File(originLocation + fileName).length();
                    log.info("size of origin {} is {},size of {} from server is {}", fileName, comparedFileSize, fileName, remoteFileSize);
                    log.info("check {} size normal", fileName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public boolean fileSizeComparison(ConnectionConfig connectionConfig, String fileName, String serverLocation, String localLocation, String originLocation) throws JSchException, IOException {
        CommonSSHUtils.useScpCopyRemoteFile(connectionConfig, serverLocation, localLocation, fileName);
        //remote file size
        long remoteFileSize = new File(localLocation + fileName).length();
        //comparison file size
        long comparedFileSize = new File(originLocation + fileName).length();

        return remoteFileSize == comparedFileSize;

    }
}
