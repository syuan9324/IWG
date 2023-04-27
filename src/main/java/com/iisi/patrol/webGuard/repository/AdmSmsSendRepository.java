package com.iisi.patrol.webGuard.repository;


import com.iisi.patrol.webGuard.domain.AdmSmsSend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data SQL repository for the AdmSmsSend entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdmSmsSendRepository extends JpaRepository<AdmSmsSend, Long>, JpaSpecificationExecutor<AdmSmsSend> {
    @Query(value = "SELECT TOP (:count1) * FROM ADM_SMS_SEND  WHERE  STATUS ='W' or STATUS ='w'",nativeQuery = true)
    List<AdmSmsSend> findAdmSmsList(@Param("count1") Integer count1);

    default void warnSms(){
        AdmSmsSend sms = new AdmSmsSend();
        sms.setRecever("0921531997");
        sms.setSupplier("Chunghwa Telecom");
        sms.setMessage("注意!!檢測出檔案有異動");
        sms.setSmsType("IWG");
        sms.setStatus("W");
        sms.setCreateTime(Instant.now());
        sms.setSourceId("e");
        this.save(sms);
    }
}
