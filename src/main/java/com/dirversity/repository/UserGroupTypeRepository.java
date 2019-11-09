package com.dirversity.repository;
import com.dirversity.domain.UserGroupType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UserGroupType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserGroupTypeRepository extends JpaRepository<UserGroupType, Long> {

}
