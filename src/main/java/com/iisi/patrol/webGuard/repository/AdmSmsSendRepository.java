package com.iisi.patrol.webGuard.repository;


import com.iisi.patrol.webGuard.domain.AdmSmsSend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the AdmSmsSend entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdmSmsSendRepository extends JpaRepository<AdmSmsSend, Long>, JpaSpecificationExecutor<AdmSmsSend> {
    @Query(value = "SELECT TOP (:count1) * FROM ADM_SMS_SEND  WHERE  STATUS ='W' or STATUS ='w'",nativeQuery = true)
    List<AdmSmsSend> findAdmSmsList(@Param("count1") Integer count1);
}
