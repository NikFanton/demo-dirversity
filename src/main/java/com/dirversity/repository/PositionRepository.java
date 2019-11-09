package com.dirversity.repository;
import com.dirversity.domain.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Position entity.
 */
@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {

    @Query(value = "select distinct position from Position position left join fetch position.employees",
        countQuery = "select count(distinct position) from Position position")
    Page<Position> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct position from Position position left join fetch position.employees")
    List<Position> findAllWithEagerRelationships();

    @Query("select position from Position position left join fetch position.employees where position.id =:id")
    Optional<Position> findOneWithEagerRelationships(@Param("id") Long id);

}
