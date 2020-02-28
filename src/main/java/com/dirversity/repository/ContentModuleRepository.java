package com.dirversity.repository;

import com.dirversity.domain.ContentModule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ContentModule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContentModuleRepository extends JpaRepository<ContentModule, Long> {

    @Query(value = "select distinct contentModule from ContentModule contentModule left join fetch contentModule.curriculum curriculum where curriculum.id = :curriculumId",
        countQuery = "select count(distinct contentModule) from ContentModule contentModule")
    Page<ContentModule> findAllForCurriculum(Pageable pageable, @Param("curriculumId") Long curriculumId);
}
