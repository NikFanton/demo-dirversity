package com.dirversity.repository;
import com.dirversity.domain.Email;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Email entity.
 */
@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {

    @Query(value = "select distinct email from Email email left join fetch email.toUsers left join fetch email.ccUsers left join fetch email.toUsersGroups left join fetch email.ccUserGroups left join fetch email.resources",
        countQuery = "select count(distinct email) from Email email")
    Page<Email> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct email from Email email left join fetch email.toUsers left join fetch email.ccUsers left join fetch email.toUsersGroups left join fetch email.ccUserGroups left join fetch email.resources")
    List<Email> findAllWithEagerRelationships();

    @Query("select email from Email email left join fetch email.toUsers left join fetch email.ccUsers left join fetch email.toUsersGroups left join fetch email.ccUserGroups left join fetch email.resources where email.id =:id")
    Optional<Email> findOneWithEagerRelationships(@Param("id") Long id);

}
