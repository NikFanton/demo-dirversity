package com.dirversity.service.mapper;

import com.dirversity.domain.*;
import com.dirversity.service.dto.ResourceTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ResourceType} and its DTO {@link ResourceTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ResourceTypeMapper extends EntityMapper<ResourceTypeDTO, ResourceType> {


    @Mapping(target = "resources", ignore = true)
    @Mapping(target = "removeResource", ignore = true)
    ResourceType toEntity(ResourceTypeDTO resourceTypeDTO);

    default ResourceType fromId(Long id) {
        if (id == null) {
            return null;
        }
        ResourceType resourceType = new ResourceType();
        resourceType.setId(id);
        return resourceType;
    }
}
