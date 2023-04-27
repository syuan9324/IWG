package com.iisi.patrol.webGuard.repository;

import com.iisi.patrol.webGuard.domain.IwgHostsTarget;
import com.iisi.patrol.webGuard.service.dto.IwgHostsTargetDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IwgHostsTargetRepository extends JpaRepository<IwgHostsTarget, Long> {
    @Query(
            value = "SELECT * FROM IWG_HOSTS_TARGET u WHERE u.HOSTNAME = :hostName and u.PORT = :port",
            nativeQuery = true)
    List<IwgHostsTarget> getIwgHostTargetByHost(@Param("hostName") String hostName,@Param("port") int port);
}
