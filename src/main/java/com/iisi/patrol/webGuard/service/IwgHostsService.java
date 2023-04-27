package com.iisi.patrol.webGuard.service;

import com.iisi.patrol.webGuard.domain.IwgHosts;
import com.iisi.patrol.webGuard.repository.IwgHostsRepository;
import com.iisi.patrol.webGuard.service.dto.IwgHostsDTO;
import com.iisi.patrol.webGuard.service.dto.mapper.IwgHostsMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public IwgHostsDTO findById(long id){
        Optional<IwgHosts> domain = iwgHostsRepository.findById(id);
        return domain.map(iwgHostsMapper::toDto).orElse(null);
    }

    public IwgHostsDTO findByHostNameAndPort(long id){
        Optional<IwgHosts> domain = iwgHostsRepository.findById(id);
        return domain.map(iwgHostsMapper::toDto).orElse(null);
    }

}
