package com.dirversity.service.mapper;

import com.dirversity.domain.*;
import com.dirversity.service.dto.CurriculumTagDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CurriculumTag} and its DTO {@link CurriculumTagDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CurriculumTagMapper extends EntityMapper<CurriculumTagDTO, CurriculumTag> {


    @Mapping(target = "curricula", ignore = true)
    @Mapping(target = "removeCurriculum", ignore = true)
    CurriculumTag toEntity(CurriculumTagDTO curriculumTagDTO);

    default CurriculumTag fromId(Long id) {
        if (id == null) {
            return null;
        }
        CurriculumTag curriculumTag = new CurriculumTag();
        curriculumTag.setId(id);
        return curriculumTag;
    }
}
