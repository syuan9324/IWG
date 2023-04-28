package com.iisi.patrol.webGuard.repository;

import com.iisi.patrol.webGuard.domain.AdmMailSend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AdmMailSendRepository extends JpaRepository<AdmMailSend, Long> {}
