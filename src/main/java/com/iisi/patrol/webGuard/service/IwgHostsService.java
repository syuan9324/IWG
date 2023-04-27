package com.iisi.patrol.webGuard.service;

import com.iisi.patrol.webGuard.domain.IwgHosts;
import com.iisi.patrol.webGuard.repository.IwgHostsRepository;
import com.iisi.patrol.webGuard.service.dto.IwgHostsDTO;
import com.iisi.patrol.webGuard.service.dto.IwgHostsDTO;
import com.iisi.patrol.webGuard.service.dto.mapper.IwgHostsMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IwgHostsService {

    private final IwgHostsRepository iwgHostsRepository;

    private final IwgHostsMapper iwgHostsMapper;

    public IwgHostsService(IwgHostsRepository iwgHostsRepository, IwgHostsMapper iwgHostsMapper) {
        this.iwgHostsRepository = iwgHostsRepository;
        this.iwgHostsMapper = iwgHostsMapper;
    }

    public IwgHosts save(IwgHosts iwgHosts) {
//        IwgHosts = fa1IdleOverseeReviewRepository.save(fa1IdleOverseeReview);
//        return fa1IdleOverseeReviewMapper.toDto(fa1IdleOverseeReview);
        return iwgHostsRepository.save(iwgHosts);
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

}
