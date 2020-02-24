package com.dirversity.service.mapper;

import com.dirversity.domain.*;
import com.dirversity.service.dto.TopicDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Topic} and its DTO {@link TopicDTO}.
 */
@Mapper(componentModel = "spring", uses = {ContentModuleMapper.class})
public interface TopicMapper extends EntityMapper<TopicDTO, Topic> {

    @Mapping(source = "contentModule.id", target = "contentModuleId")
    @Mapping(source = "contentModule.name", target = "contentModuleName")
    TopicDTO toDto(Topic topic);

    @Mapping(source = "contentModuleId", target = "contentModule")
    @Mapping(target = "resources", ignore = true)
    @Mapping(target = "removeResource", ignore = true)
    Topic toEntity(TopicDTO topicDTO);

    default Topic fromId(Long id) {
        if (id == null) {
            return null;
        }
        Topic topic = new Topic();
        topic.setId(id);
        return topic;
    }
}
