package com.dirversity.service.mapper;

import com.dirversity.domain.*;
import com.dirversity.service.dto.UserGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserGroup} and its DTO {@link UserGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserGroupTypeMapper.class, UserMapper.class})
public interface UserGroupMapper extends EntityMapper<UserGroupDTO, UserGroup> {

    @Mapping(source = "userGroupType.id", target = "userGroupTypeId")
    @Mapping(source = "userGroupType.name", target = "userGroupTypeName")
    UserGroupDTO toDto(UserGroup userGroup);

    @Mapping(source = "userGroupTypeId", target = "userGroupType")
    @Mapping(target = "removeUsers", ignore = true)
    @Mapping(target = "rules", ignore = true)
    @Mapping(target = "removeRules", ignore = true)
    UserGroup toEntity(UserGroupDTO userGroupDTO);

    default UserGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserGroup userGroup = new UserGroup();
        userGroup.setId(id);
        return userGroup;
    }
}
