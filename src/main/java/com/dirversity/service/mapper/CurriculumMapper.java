package com.dirversity.service.mapper;

import com.dirversity.domain.*;
import com.dirversity.service.dto.CurriculumDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Curriculum} and its DTO {@link CurriculumDTO}.
 */
@Mapper(componentModel = "spring", uses = {ResourceMapper.class, CurriculumTagMapper.class, UserMapper.class})
public interface CurriculumMapper extends EntityMapper<CurriculumDTO, Curriculum> {

    @Mapping(source = "originFile.id", target = "originFileId")
    @Mapping(source = "originFile.name", target = "originFileName")
    CurriculumDTO toDto(Curriculum curriculum);

    @Mapping(source = "originFileId", target = "originFile")
    @Mapping(target = "removeContentModules", ignore = true)
    @Mapping(target = "removeCurriculumTags", ignore = true)
    @Mapping(target = "removeTeachers", ignore = true)
    Curriculum toEntity(CurriculumDTO curriculumDTO);

    default Curriculum fromId(Long id) {
        if (id == null) {
            return null;
        }
        Curriculum curriculum = new Curriculum();
        curriculum.setId(id);
        return curriculum;
    }
}
