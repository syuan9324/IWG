package com.iisi.patrol.webGuard.repository;

import com.iisi.patrol.webGuard.domain.AdmMailSend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.Instant;


/**
 * Spring Data SQL repository for the AdmMailSend entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdmMailSendRepository extends JpaRepository<AdmMailSend, Long> , JpaSpecificationExecutor<AdmMailSend> {
    AdmMailSend findBySourceId(String sourceId);

    default void warnMail(){
        AdmMailSend mail = new AdmMailSend();
        mail.setReceiver("ad10823046@alumni.scu.edu.tw");
        mail.setMailType("IWG");
        mail.setSubject("檔案異動通知");
        mail.setContent("注意!!檢測出檔案有異動");
        mail.setStatus("W");
        mail.setIsHtml(false);
        mail.setCreateTime(Instant.now());
        this.save(mail);
    }
}
