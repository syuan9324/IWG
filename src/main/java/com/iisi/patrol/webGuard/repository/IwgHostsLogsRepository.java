package com.iisi.patrol.webGuard.repository;

import com.iisi.patrol.webGuard.domain.IwgHosts;
import com.iisi.patrol.webGuard.domain.IwgHostsLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IwgHostsLogsRepository extends JpaRepository<IwgHostsLogs, Long> , JpaSpecificationExecutor<IwgHosts> {
}
