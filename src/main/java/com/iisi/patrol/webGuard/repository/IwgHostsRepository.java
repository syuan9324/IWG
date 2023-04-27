package com.iisi.patrol.webGuard.repository;

import com.iisi.patrol.webGuard.domain.IwgHosts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data SQL repository for the AdmMailSend entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IwgHostsRepository extends JpaRepository<IwgHosts, Long> , JpaSpecificationExecutor<IwgHosts> {
    @Query(
            value = "SELECT * FROM IWG_HOSTS ih WHERE ih.ACTIVE = 'Y'",
            nativeQuery = true)
    List<IwgHosts> findActive();
}
