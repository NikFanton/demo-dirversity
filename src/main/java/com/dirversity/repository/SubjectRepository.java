package com.dirversity.repository;
import com.dirversity.domain.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Subject entity.
 */
@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    @Query(value = "select distinct subject from Subject subject left join fetch subject.teachers",
        countQuery = "select count(distinct subject) from Subject subject")
    Page<Subject> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct subject from Subject subject left join fetch subject.teachers")
    List<Subject> findAllWithEagerRelationships();

    @Query("select subject from Subject subject left join fetch subject.teachers where subject.id =:id")
    Optional<Subject> findOneWithEagerRelationships(@Param("id") Long id);

}
