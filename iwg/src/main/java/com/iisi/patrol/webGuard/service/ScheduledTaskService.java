package com.iisi.patrol.webGuard.service;

import com.iisi.patrol.webGuard.service.dto.IwgHostsDTO;
import com.iisi.patrol.webGuard.service.dto.IwgHostsLogsDTO;
import com.iisi.patrol.webGuard.service.dto.IwgHostsTargetDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ScheduledTaskService {

    private final IwgHostsService iwgHostsService;

    private final IwgHostsTargetService iwgHostsTargetService;

    private final IwgHostsLogsService iwgHostsLogsService;

    private final FileComparisonService fileComparisonService;

    private final FileCheckService fileCheckService;

    private final IwgMailSendService iwgMailSendService;


    private static final Logger log = LoggerFactory.getLogger(ScheduledTaskService.class);


    public ScheduledTaskService(IwgHostsService iwgHostsService, IwgHostsTargetService iwgHostsTargetService, IwgHostsLogsService iwgHostsLogsService, FileComparisonService fileComparisonService, FileCheckService fileCheckService, IwgMailSendService iwgMailSendService) {
        this.iwgHostsService = iwgHostsService;
        this.iwgHostsTargetService = iwgHostsTargetService;
        this.iwgHostsLogsService = iwgHostsLogsService;
        this.fileComparisonService = fileComparisonService;
        this.fileCheckService = fileCheckService;
        this.iwgMailSendService = iwgMailSendService;
    }

    //@Scheduled(cron = "0 0/3 * * * ?")
    public void doFileComparison() {
        List<IwgHostsDTO> hostList = iwgHostsService.findActive();
        hostList.forEach(iwgHostsDTO -> {
            log.info("start file compare =============================");
            log.info("current host : {}", iwgHostsDTO.getHostname());
            List<IwgHostsTargetDTO> iwgHostsTargetDTOs = iwgHostsTargetService.getIwgHostTargetByHost(iwgHostsDTO.getHostname(), iwgHostsDTO.getPort());
            log.info("check IwgHostsTargetLength : {}", iwgHostsTargetDTOs.size());
            fileComparisonService.fileCompareByHostAndTargetList(iwgHostsDTO, iwgHostsTargetDTOs);
        });
        log.info("end file compare =============================");
    }

    @Scheduled(cron = "0 0/5 * * * ?")
    public void doFileComparisonInMd5() {
        List<IwgHostsDTO> hostList = iwgHostsService.findActive();
        hostList.forEach(iwgHostsDTO -> {
            log.info("========== start file compare =============================");
            log.info("current host : {}", iwgHostsDTO.getHostname());
            List<IwgHostsTargetDTO> iwgHostsTargetDTOs = iwgHostsTargetService.getIwgHostTargetByHost(iwgHostsDTO.getHostname(), iwgHostsDTO.getPort());
            log.info("check IwgHostsTargetLength : {}", iwgHostsTargetDTOs.size());
            fileComparisonService.fileCompareInMD5ByHostAndTargetList(iwgHostsDTO, iwgHostsTargetDTOs);
        });
        log.info("========== end  file  compare =============================");
    }

    @Scheduled(cron = "0 0/5 * * * ?")
    public void doNfsMountCheck() {
        fileCheckService.checkMountStatus();
    }

    @Scheduled(cron = "0 0 18 * * ?")
    public void getIwgHostLogs() {
        List<IwgHostsLogsDTO> ls = iwgHostsLogsService.findTop50IwgHosts();
        StringBuilder contentBuilder = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
                .withZone(ZoneId.systemDefault());

        ls.forEach(ele -> {
            String timeString = formatter.format(ele.getFinishTime());
            contentBuilder.append(String.format("%s\t%s\t%s", ele.getHostname(), ele.getResult(), timeString));
            contentBuilder.append("\n");
        });
        //2202028
        log.info("send host log");
        iwgMailSendService.saveHostLogMail("2106017@iisigroup.com", contentBuilder.toString());
    }
}
