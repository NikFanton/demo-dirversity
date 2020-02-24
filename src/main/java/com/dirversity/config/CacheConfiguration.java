package com.dirversity.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.dirversity.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.dirversity.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.dirversity.domain.User.class.getName());
            createCache(cm, com.dirversity.domain.Authority.class.getName());
            createCache(cm, com.dirversity.domain.User.class.getName() + ".authorities");
            createCache(cm, com.dirversity.domain.UserGroup.class.getName());
            createCache(cm, com.dirversity.domain.UserGroup.class.getName() + ".users");
            createCache(cm, com.dirversity.domain.UserGroup.class.getName() + ".rules");
            createCache(cm, com.dirversity.domain.UserGroupType.class.getName());
            createCache(cm, com.dirversity.domain.Resource.class.getName());
            createCache(cm, com.dirversity.domain.Resource.class.getName() + ".resourceTypes");
            createCache(cm, com.dirversity.domain.Resource.class.getName() + ".rules");
            createCache(cm, com.dirversity.domain.ResourceType.class.getName());
            createCache(cm, com.dirversity.domain.ResourceType.class.getName() + ".resources");
            createCache(cm, com.dirversity.domain.Rule.class.getName());
            createCache(cm, com.dirversity.domain.Rule.class.getName() + ".users");
            createCache(cm, com.dirversity.domain.Rule.class.getName() + ".userGroups");
            createCache(cm, com.dirversity.domain.Rule.class.getName() + ".resources");
            createCache(cm, com.dirversity.domain.Subject.class.getName());
            createCache(cm, com.dirversity.domain.Subject.class.getName() + ".teachers");
            createCache(cm, com.dirversity.domain.Position.class.getName());
            createCache(cm, com.dirversity.domain.Position.class.getName() + ".employees");
            createCache(cm, com.dirversity.domain.Curriculum.class.getName());
            createCache(cm, com.dirversity.domain.Curriculum.class.getName() + ".contentModules");
            createCache(cm, com.dirversity.domain.Curriculum.class.getName() + ".curriculumTags");
            createCache(cm, com.dirversity.domain.Curriculum.class.getName() + ".teachers");
            createCache(cm, com.dirversity.domain.ContentModule.class.getName());
            createCache(cm, com.dirversity.domain.ContentModule.class.getName() + ".topics");
            createCache(cm, com.dirversity.domain.Topic.class.getName());
            createCache(cm, com.dirversity.domain.Topic.class.getName() + ".resources");
            createCache(cm, com.dirversity.domain.CurriculumTag.class.getName());
            createCache(cm, com.dirversity.domain.CurriculumTag.class.getName() + ".curricula");
            createCache(cm, com.dirversity.domain.Resource.class.getName() + ".topics");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }

}
