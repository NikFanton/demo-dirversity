package com.dirversity.service.mapper;

import com.dirversity.domain.*;
import com.dirversity.service.dto.UserGroupTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserGroupType} and its DTO {@link UserGroupTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserGroupTypeMapper extends EntityMapper<UserGroupTypeDTO, UserGroupType> {



    default UserGroupType fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserGroupType userGroupType = new UserGroupType();
        userGroupType.setId(id);
        return userGroupType;
    }
}
