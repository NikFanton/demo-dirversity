package com.dirversity.repository;
import com.dirversity.domain.Rule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Rule entity.
 */
@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {

    @Query(value = "select distinct rule from Rule rule left join fetch rule.users left join fetch rule.userGroups",
        countQuery = "select count(distinct rule) from Rule rule")
    Page<Rule> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct rule from Rule rule left join fetch rule.users left join fetch rule.userGroups")
    List<Rule> findAllWithEagerRelationships();

    @Query("select rule from Rule rule left join fetch rule.users left join fetch rule.userGroups where rule.id =:id")
    Optional<Rule> findOneWithEagerRelationships(@Param("id") Long id);

}
