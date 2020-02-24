package com.dirversity.repository;
import com.dirversity.domain.ContentModule;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ContentModule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContentModuleRepository extends JpaRepository<ContentModule, Long> {

}
