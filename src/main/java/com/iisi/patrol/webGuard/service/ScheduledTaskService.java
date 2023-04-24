package com.iisi.patrol.webGuard.service;

import com.iisi.patrol.webGuard.service.sshService.ConnectionConfig;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ScheduledTaskService {

    private final ConnectionConfigService connectionConfigService;

    private final CommonSSHUtils commonSSHUtils;

    public ScheduledTaskService(ConnectionConfigService connectionConfigService, CommonSSHUtils commonSSHUtils) {
        this.connectionConfigService = connectionConfigService;
        this.commonSSHUtils = commonSSHUtils;
    }

    /**
     * 測試一下job
     */
//    @Scheduled(cron = "0/5 * * * * ?")
    public void testTask(){
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        System.out.println(strDate);
    }

    /**
     * 測試一下job
     */
//    @Scheduled(cron = "0/10 * * * * ?")
    public void fileComparison(){
        List<ConnectionConfig> configs = connectionConfigService.getConnectionList();
        configs.stream().forEach(connectionConfig -> {
            try {
                System.out.println(connectionConfig);
                String response = CommonSSHUtils.useSshCommand(connectionConfig, "du -B 1 pwc-web.war");
                System.out.println(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

}
