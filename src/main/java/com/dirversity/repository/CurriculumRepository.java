package com.dirversity.repository;
import com.dirversity.domain.Curriculum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Curriculum entity.
 */
@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {

    @Query(value = "select distinct curriculum from Curriculum curriculum left join fetch curriculum.curriculumTags left join fetch curriculum.teachers",
        countQuery = "select count(distinct curriculum) from Curriculum curriculum")
    Page<Curriculum> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct curriculum from Curriculum curriculum left join fetch curriculum.curriculumTags left join fetch curriculum.teachers")
    List<Curriculum> findAllWithEagerRelationships();

    @Query("select curriculum from Curriculum curriculum left join fetch curriculum.curriculumTags left join fetch curriculum.teachers where curriculum.id =:id")
    Optional<Curriculum> findOneWithEagerRelationships(@Param("id") Long id);

}
