package com.iisi.patrol.webGuard.repository;

import com.iisi.patrol.webGuard.domain.IwgHosts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data SQL repository for the AdmMailSend entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IwgHostsRepository extends JpaRepository<IwgHosts, Long> , JpaSpecificationExecutor<IwgHosts> {

}
