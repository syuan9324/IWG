package com.iisi.patrol.webGuard.service;

import com.iisi.patrol.webGuard.domain.AdmMailSend;
import com.iisi.patrol.webGuard.repository.AdmMailSendRepository;
import com.iisi.patrol.webGuard.service.dto.AdmMailSendDTO;
import com.iisi.patrol.webGuard.service.dto.mapper.AdmMailSendMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class AdmMailSendService {

    private final AdmMailSendRepository admMailSendRepository;

    private final AdmMailSendMapper admMailSendMapper;

    public AdmMailSendService(AdmMailSendRepository admMailSendRepository, AdmMailSendMapper admMailSendMapper) {
        this.admMailSendRepository = admMailSendRepository;
        this.admMailSendMapper = admMailSendMapper;
    }

    @Transactional
    public AdmMailSendDTO saveAdmMail(AdmMailSendDTO admMailSendDTO){
        AdmMailSend admMailSend = admMailSendMapper.toEntity(admMailSendDTO);
        AdmMailSend savedAdmMailSend = admMailSendRepository.save(admMailSend);
        return admMailSendMapper.toDto(savedAdmMailSend);
    }

    @Transactional AdmMailSendDTO saveAdmMailWithReceiverAndContent(String receiver,String content){
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
        return this.saveAdmMail(mail);
    }

}
