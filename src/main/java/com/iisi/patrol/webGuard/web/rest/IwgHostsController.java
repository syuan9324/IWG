package com.iisi.patrol.webGuard.web.rest;
import com.iisi.patrol.webGuard.domain.IwgHosts;
import com.iisi.patrol.webGuard.service.IwgHostsService;
import com.iisi.patrol.webGuard.service.dto.IwgHostsDTO;
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
    public IwgHosts createiwgHosts(@Valid @RequestBody IwgHosts iwgHosts) {
        System.out.print("666@@");
        return iwgHostsService.save(iwgHosts) ;
    }

    @PostMapping("/find/iwgHosts")
    public List<IwgHosts> findiwgHosts(@Valid @RequestBody IwgHostsDTO iwgHosts) {
        System.out.print("6666@@");
        return iwgHostsService.find(iwgHosts) ;
    }


}
