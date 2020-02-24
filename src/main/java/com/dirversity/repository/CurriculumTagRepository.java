package com.dirversity.repository;
import com.dirversity.domain.CurriculumTag;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CurriculumTag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CurriculumTagRepository extends JpaRepository<CurriculumTag, Long> {

}
