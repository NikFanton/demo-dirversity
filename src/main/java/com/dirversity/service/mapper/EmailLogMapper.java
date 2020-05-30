package com.dirversity.service.mapper;

import com.dirversity.domain.*;
import com.dirversity.service.dto.EmailLogDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmailLog} and its DTO {@link EmailLogDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmailMapper.class, ResourceMapper.class})
public interface EmailLogMapper extends EntityMapper<EmailLogDTO, EmailLog> {

    @Mapping(source = "email.id", target = "emailId")
    @Mapping(source = "createdDate", target = "createdDate")
    EmailLogDTO toDto(EmailLog emailLog);

    @Mapping(source = "emailId", target = "email")
    @Mapping(source = "createdDate", target = "createdDate")
    EmailLog toEntity(EmailLogDTO emailLogDTO);

    default EmailLog fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmailLog emailLog = new EmailLog();
        emailLog.setId(id);
        return emailLog;
    }
}
