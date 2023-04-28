package com.iisi.patrol.webGuard.service.dto.mapper;

import com.iisi.patrol.webGuard.domain.AdmMailSend;
import com.iisi.patrol.webGuard.service.dto.AdmMailSendDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdmMailSendMapper extends EntityMapper<AdmMailSendDTO, AdmMailSend> {
}
