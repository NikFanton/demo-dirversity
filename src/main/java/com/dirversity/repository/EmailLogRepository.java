package com.dirversity.repository;
import com.dirversity.domain.EmailLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the EmailLog entity.
 */
@Repository
public interface EmailLogRepository extends JpaRepository<EmailLog, Long> {

    @Query(value = "select distinct emailLog from EmailLog emailLog left join fetch emailLog.sharedResources",
        countQuery = "select count(distinct emailLog) from EmailLog emailLog")
    Page<EmailLog> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct emailLog from EmailLog emailLog left join fetch emailLog.sharedResources")
    List<EmailLog> findAllWithEagerRelationships();

    @Query("select emailLog from EmailLog emailLog left join fetch emailLog.sharedResources where emailLog.id =:id")
    Optional<EmailLog> findOneWithEagerRelationships(@Param("id") Long id);

}
