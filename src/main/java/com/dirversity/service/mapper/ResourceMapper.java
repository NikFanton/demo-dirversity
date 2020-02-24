package com.dirversity.service.mapper;

import com.dirversity.domain.*;
import com.dirversity.service.dto.ResourceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Resource} and its DTO {@link ResourceDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ResourceTypeMapper.class, RuleMapper.class, TopicMapper.class})
public interface ResourceMapper extends EntityMapper<ResourceDTO, Resource> {

    @Mapping(source = "publisher.id", target = "publisherId")
    @Mapping(source = "publisher.lastName", target = "publisherLastName")
    ResourceDTO toDto(Resource resource);

    @Mapping(source = "publisherId", target = "publisher")
    @Mapping(target = "removeResourceType", ignore = true)
    @Mapping(target = "removeRules", ignore = true)
    @Mapping(target = "removeTopic", ignore = true)
    Resource toEntity(ResourceDTO resourceDTO);

    default Resource fromId(Long id) {
        if (id == null) {
            return null;
        }
        Resource resource = new Resource();
        resource.setId(id);
        return resource;
    }
}
