package com.iisi.patrol.webGuard.service;

import com.iisi.patrol.webGuard.service.dto.AdmMailSendDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Service
public class IwgMailSendService {

    private final AdmMailSendService admMailSendService;

    public IwgMailSendService(AdmMailSendService admMailSendService) {
        this.admMailSendService = admMailSendService;
    }

    /**
     *
     * @param receivers 收件者們,用逗號分隔
     * @param content 信件內容
     */

    public void saveAdmMailWithReceiverAndContent(String receivers,String content){
        if(StringUtils.isBlank(receivers)) return;
        List<String> receiverList = Arrays.asList(receivers.split(",", -1));
        receiverList.forEach(receiver->{
            AdmMailSendDTO mail = new AdmMailSendDTO();
            mail.setReceiver(receiver);
            mail.setMailType("IWG");
            mail.setSubject("檔案異動通知");
            mail.setContent("注意!!檢測出檔案有異動:"+content);
            mail.setStatus("W");
            mail.setIsHtml(false);
            mail.setCreateUser("iwg");
            mail.setSourceId("iwg");
            mail.setCreateTime(Instant.now());
            admMailSendService.saveAdmMail(mail);
        });
    }

    public void saveNfsWarningMailWithReceiverAndContent(String receivers,String content){
        if(StringUtils.isBlank(receivers)) return;
        List<String> receiverList = Arrays.asList(receivers.split(",", -1));
        receiverList.forEach(receiver->{
            AdmMailSendDTO mail = new AdmMailSendDTO();
            mail.setReceiver(receiver);
            mail.setMailType("IWG");
            mail.setSubject("MOUNT 異常");
            mail.setContent(content);
            mail.setStatus("W");
            mail.setIsHtml(false);
            mail.setCreateUser("iwg");
            mail.setSourceId("iwg");
            mail.setCreateTime(Instant.now());
            admMailSendService.saveAdmMail(mail);
        });
    }

    public void saveHostLogMail(String receivers,String content){
        if(StringUtils.isBlank(receivers)) return;
        List<String> receiverList = Arrays.asList(receivers.split(",", -1));
        receiverList.forEach(receiver->{
            AdmMailSendDTO mail = new AdmMailSendDTO();
            mail.setReceiver(receiver);
            mail.setMailType("IWG");
            mail.setSubject("IWG log");
            mail.setContent(content);
            mail.setStatus("W");
            mail.setIsHtml(false);
            mail.setCreateUser("iwg");
            mail.setSourceId("iwg");
            mail.setCreateTime(Instant.now());
            admMailSendService.saveAdmMail(mail);
        });
    }

}
