package com.iisi.patrol.webGuard.service;

import com.iisi.patrol.webGuard.domain.IwgHosts;
import com.iisi.patrol.webGuard.repository.IwgHostsRepository;
import com.iisi.patrol.webGuard.service.dto.IwgHostsDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IwgHostsService {

    private final IwgHostsRepository iwgHostsRepository;

    public IwgHostsService(IwgHostsRepository iwgHostsRepository) {
        this.iwgHostsRepository = iwgHostsRepository;
    }

    public IwgHosts save(IwgHosts iwgHosts) {
//        IwgHosts = fa1IdleOverseeReviewRepository.save(fa1IdleOverseeReview);
//        return fa1IdleOverseeReviewMapper.toDto(fa1IdleOverseeReview);
        return iwgHostsRepository.save(iwgHosts);
    }

    public List<IwgHosts> find(IwgHostsDTO iwgHosts) {
        return iwgHostsRepository.findAll();
    }


}
