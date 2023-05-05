package com.iisi.patrol.webGuard.repository;

import com.iisi.patrol.webGuard.domain.IwgHosts;
import com.iisi.patrol.webGuard.domain.IwgHostsLogs;
import com.iisi.patrol.webGuard.service.dto.IwgHostsLogsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IwgHostsLogsRepository extends JpaRepository<IwgHostsLogs, Long> , JpaSpecificationExecutor<IwgHosts> {
// "SELECT * FROM IWG_HOSTS_LOGS WHERE 1=1 " +
//         "/*APPEND_WHERE*/ " +
//         "AND HOSTNAME LIKE CONCAT('%', :hostname, '%') " +
//         "AND [RESULT] = :result ",
    @Query(value = "SELECT * from IWG_HOSTS_LOGS where 1=1"+
            "/*APPEND_WHERE*/ " +
            "AND HOSTNAME LIKE CONCAT ('%',:hostname,'%')" +
//            "/*APPEND_WHERE*/ " +
            " AND [RESULT] = :result  ", nativeQuery = true)
    List<IwgHostsLogs> findByResultAndHostname(@Param("result")String result,@Param("hostname")String hostname);
}
