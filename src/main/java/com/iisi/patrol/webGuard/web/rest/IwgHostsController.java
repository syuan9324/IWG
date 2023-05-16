package com.iisi.patrol.webGuard.web.rest;
import com.iisi.patrol.webGuard.domain.IwgHosts;
import com.iisi.patrol.webGuard.domain.IwgHostsLogs;
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
public class IwgHostsController {

    private final IwgHostsService iwgHostsService;

    public IwgHostsController(IwgHostsService iwgHostsService) {
        this.iwgHostsService = iwgHostsService;
    }

    @PostMapping("/create/iwgHosts")
    public void createiwgHosts(@Valid @RequestBody IwgHostsDTO iwgHostsDTO) {
         iwgHostsService.save(iwgHostsDTO) ;
    }

    @PostMapping("/find/iwgHosts/all")
    public List<IwgHosts> findiwgHosts(@Valid @RequestBody IwgHostsDTO iwgHosts) {
        return iwgHostsService.find(iwgHosts) ;
    }

    @PostMapping("/find/iwgHosts")
    public List<IwgHosts> findByHostname(@Valid @RequestBody IwgHostsDTO iwgHostsDTO) {
        return iwgHostsService.findByHostname(iwgHostsDTO);
    }

    @PostMapping("/update/iwgHosts")
    public void updateiwgHosts(@Valid @RequestBody IwgHostsDTO iwgHostsDTO) {
        iwgHostsService.update(iwgHostsDTO) ;
    }


}
