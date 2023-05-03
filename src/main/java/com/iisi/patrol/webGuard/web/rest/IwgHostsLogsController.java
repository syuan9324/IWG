package com.iisi.patrol.webGuard.web.rest;
import com.iisi.patrol.webGuard.domain.IwgHosts;
import com.iisi.patrol.webGuard.domain.IwgHostsLogs;
import com.iisi.patrol.webGuard.service.IwgHostsLogsService;
import com.iisi.patrol.webGuard.service.IwgHostsService;
import com.iisi.patrol.webGuard.service.dto.IwgHostsDTO;
import com.iisi.patrol.webGuard.service.dto.IwgHostsLogsDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class IwgHostsLogsController {

    private final IwgHostsLogsService iwgHostsLogsService;

    public IwgHostsLogsController(IwgHostsService iwgHostsService, IwgHostsLogsService iwgHostsLogsService) {
        this.iwgHostsLogsService = iwgHostsLogsService;
    }


    @PostMapping("/find/iwgHostsLogs")
    public List<IwgHostsLogs> findiwgHosts(@Valid @RequestBody IwgHostsLogsDTO iwgHostsLogsDTO) {
        return iwgHostsLogsService.find(iwgHostsLogsDTO) ;
    }


}
