package com.dirversity.service.mapper;

import com.dirversity.domain.*;
import com.dirversity.service.dto.RuleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Rule} and its DTO {@link RuleDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, UserGroupMapper.class})
public interface RuleMapper extends EntityMapper<RuleDTO, Rule> {


    @Mapping(target = "removeUsers", ignore = true)
    @Mapping(target = "removeUserGroups", ignore = true)
    @Mapping(target = "resources", ignore = true)
    @Mapping(target = "removeResources", ignore = true)
    Rule toEntity(RuleDTO ruleDTO);

    default Rule fromId(Long id) {
        if (id == null) {
            return null;
        }
        Rule rule = new Rule();
        rule.setId(id);
        return rule;
    }
}
