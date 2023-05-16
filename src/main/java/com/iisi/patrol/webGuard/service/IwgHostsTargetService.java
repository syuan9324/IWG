package com.iisi.patrol.webGuard.service;

import com.iisi.patrol.webGuard.domain.IwgHosts;
import com.iisi.patrol.webGuard.domain.IwgHostsTarget;
import com.iisi.patrol.webGuard.repository.IwgHostsTargetRepository;
import com.iisi.patrol.webGuard.service.dto.IwgHostsDTO;
import com.iisi.patrol.webGuard.service.dto.IwgHostsTargetDTO;
import com.iisi.patrol.webGuard.service.dto.mapper.IwgHostsTargetMapper;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IwgHostsTargetService {

    private final IwgHostsTargetRepository iwgHostsTargetRepository;

    private final IwgHostsTargetMapper iwgHostsTargetMapper;

    public IwgHostsTargetService(IwgHostsTargetRepository iwgHostsTargetRepository, IwgHostsTargetMapper iwgHostsTargetMapper) {
        this.iwgHostsTargetRepository = iwgHostsTargetRepository;
        this.iwgHostsTargetMapper = iwgHostsTargetMapper;
    }

    public List<IwgHostsTargetDTO> getIwgHostTargetByHost(String hostName ,int port){
        return iwgHostsTargetRepository.getIwgHostTargetByHost(hostName,port).stream().map(iwgHostsTargetMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }
    public List<IwgHostsTargetDTO> getDevIwgHostTargetByHost(String hostName ,int port){
        return iwgHostsTargetRepository.getDevIwgHostTargetByHost(hostName,port).stream().map(iwgHostsTargetMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    public List<IwgHostsTargetDTO> findAll(){
        return iwgHostsTargetRepository.findAll().stream().map(iwgHostsTargetMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    public IwgHostsTargetDTO findById(long id){
        Optional<IwgHostsTarget> domain = iwgHostsTargetRepository.findById(id);
        return domain.map(iwgHostsTargetMapper::toDto).orElse(null);
    }
}
