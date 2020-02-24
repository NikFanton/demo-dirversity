package com.dirversity.service.mapper;

import com.dirversity.domain.*;
import com.dirversity.service.dto.ContentModuleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ContentModule} and its DTO {@link ContentModuleDTO}.
 */
@Mapper(componentModel = "spring", uses = {CurriculumMapper.class})
public interface ContentModuleMapper extends EntityMapper<ContentModuleDTO, ContentModule> {

    @Mapping(source = "curriculum.id", target = "curriculumId")
    @Mapping(source = "curriculum.name", target = "curriculumName")
    ContentModuleDTO toDto(ContentModule contentModule);

    @Mapping(target = "topics", ignore = true)
    @Mapping(target = "removeTopics", ignore = true)
    @Mapping(source = "curriculumId", target = "curriculum")
    ContentModule toEntity(ContentModuleDTO contentModuleDTO);

    default ContentModule fromId(Long id) {
        if (id == null) {
            return null;
        }
        ContentModule contentModule = new ContentModule();
        contentModule.setId(id);
        return contentModule;
    }
}
