package com.dirversity.service.mapper;

import com.dirversity.domain.*;
import com.dirversity.service.dto.PositionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Position} and its DTO {@link PositionDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface PositionMapper extends EntityMapper<PositionDTO, Position> {


    @Mapping(target = "removeEmployees", ignore = true)

    default Position fromId(Long id) {
        if (id == null) {
            return null;
        }
        Position position = new Position();
        position.setId(id);
        return position;
    }
}
