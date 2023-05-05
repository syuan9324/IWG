package com.iisi.patrol.webGuard.service;

import com.iisi.patrol.webGuard.domain.IwgHosts;
import com.iisi.patrol.webGuard.domain.IwgHostsTarget;
import com.iisi.patrol.webGuard.repository.IwgHostsRepository;
import com.iisi.patrol.webGuard.repository.IwgHostsTargetRepository;
import com.iisi.patrol.webGuard.service.dto.IwgHostsDTO;
import com.iisi.patrol.webGuard.service.dto.mapper.IwgHostsMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IwgHostsService {

    private final IwgHostsRepository iwgHostsRepository;
    private final IwgHostsTargetRepository iwgHostsTargetRepository;
    private final IwgHostsMapper iwgHostsMapper;

    public IwgHostsService(IwgHostsRepository iwgHostsRepository, IwgHostsTargetRepository iwgHostsTargetRepository, IwgHostsMapper iwgHostsMapper) {
        this.iwgHostsRepository = iwgHostsRepository;
        this.iwgHostsTargetRepository = iwgHostsTargetRepository;
        this.iwgHostsMapper = iwgHostsMapper;
    }

    public void save(IwgHostsDTO iwgHostsDTO) {
        IwgHosts iwgHostss = new IwgHosts();
        iwgHostss.setUsername(iwgHostsDTO.getUsername());
        iwgHostss.setPassword(iwgHostsDTO.getPassword());
        iwgHostss.setHostname(iwgHostsDTO.getHostname());
        iwgHostss.setPort(Integer.valueOf(iwgHostsDTO.getPort()));
        iwgHostss.setMailReceiver(iwgHostsDTO.getMailReceiver());
        iwgHostss.setSmsReceiver(iwgHostsDTO.getSmsReceiver());
        iwgHostss.setActive(iwgHostsDTO.getActive());
         iwgHostsRepository.save(iwgHostss);

        IwgHostsTarget iwgHostsTarget = new IwgHostsTarget();
        iwgHostsTarget.setHostname(iwgHostsDTO.getHostname());
        iwgHostsTarget.setPort(Integer.valueOf(iwgHostsDTO.getPort()));
        iwgHostsTarget.setFileName(iwgHostsDTO.getFileName());
        iwgHostsTarget.setOriginFileLocation(iwgHostsDTO.getOriginFileLocation());
        iwgHostsTarget.setTargetFileLocation(iwgHostsDTO.getTargetFileLocation());
        iwgHostsTarget.setActive(iwgHostsDTO.getActive());
        iwgHostsTarget.setOriginFolder(iwgHostsDTO.getOriginFolder());
        iwgHostsTarget.setTargetFolder(iwgHostsDTO.getTargetFolder());
        iwgHostsTargetRepository.save(iwgHostsTarget);
    }

    public List<IwgHosts> find(IwgHostsDTO iwgHosts) {
        return iwgHostsRepository.findAll();
    }

    public IwgHostsDTO findById(long id){
        Optional<IwgHosts> domain = iwgHostsRepository.findById(id);
        return domain.map(iwgHostsMapper::toDto).orElse(null);
    }

    public IwgHostsDTO findByHostNameAndPort(long id){
        Optional<IwgHosts> domain = iwgHostsRepository.findById(id);
        return domain.map(iwgHostsMapper::toDto).orElse(null);
    }

    public List<IwgHostsDTO> findActive(){
        return iwgHostsRepository.findActive().stream().map(iwgHostsMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    public List<IwgHosts> findByHostname(IwgHostsDTO iwgHostsDTO) {
        return iwgHostsRepository.findByHostname(iwgHostsDTO.getHostname()) ;
    }

}
