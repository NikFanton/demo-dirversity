package com.dirversity.service.mapper;

import com.dirversity.domain.*;
import com.dirversity.service.dto.EmailDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Email} and its DTO {@link EmailDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, UserGroupMapper.class, ResourceMapper.class})
public interface EmailMapper extends EntityMapper<EmailDTO, Email> {


    @Mapping(target = "removeToUsers", ignore = true)
    @Mapping(target = "removeCcUsers", ignore = true)
    @Mapping(target = "removeToUsersGroups", ignore = true)
    @Mapping(target = "removeCcUserGroups", ignore = true)
    @Mapping(target = "removeResources", ignore = true)

    default Email fromId(Long id) {
        if (id == null) {
            return null;
        }
        Email email = new Email();
        email.setId(id);
        return email;
    }
}
