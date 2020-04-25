package com.dirversity.repository;

import com.dirversity.domain.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Topic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    @Query(value = "select distinct topic from Topic topic left join fetch topic.contentModule contentModule where contentModule.id = :contentModuleId",
        countQuery = "select count(distinct topic) from Topic topic")
    Page<Topic> findAllForContentModule(Pageable pageable, @Param("contentModuleId") Long contentModuleId);
}
