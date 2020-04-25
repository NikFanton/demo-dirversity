package com.dirversity.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Resource.
 */
@Entity
@Table(name = "resource")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Resource extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @Column(name = "access_url")
    private String accessUrl;

    @Column(name = "file_id")
    private String fileId;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "resource_resource_type",
               joinColumns = @JoinColumn(name = "resource_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "resource_type_id", referencedColumnName = "id"))
    private Set<ResourceType> resourceTypes = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "resource_rules",
               joinColumns = @JoinColumn(name = "resource_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "rules_id", referencedColumnName = "id"))
    private Set<Rule> rules = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "resource_topic",
               joinColumns = @JoinColumn(name = "resource_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "topic_id", referencedColumnName = "id"))
    private Set<Topic> topics = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Resource name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public Resource author(String author) {
        this.author = author;
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAccessUrl() {
        return accessUrl;
    }

    public Resource accessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
        return this;
    }

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }

    public String getFileId() {
        return fileId;
    }

    public Resource fileId(String fileId) {
        this.fileId = fileId;
        return this;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Set<ResourceType> getResourceTypes() {
        return resourceTypes;
    }

    public Resource resourceTypes(Set<ResourceType> resourceTypes) {
        this.resourceTypes = resourceTypes;
        return this;
    }

    public Resource addResourceType(ResourceType resourceType) {
        this.resourceTypes.add(resourceType);
        resourceType.getResources().add(this);
        return this;
    }

    public Resource removeResourceType(ResourceType resourceType) {
        this.resourceTypes.remove(resourceType);
        resourceType.getResources().remove(this);
        return this;
    }

    public void setResourceTypes(Set<ResourceType> resourceTypes) {
        this.resourceTypes = resourceTypes;
    }

    public Set<Rule> getRules() {
        return rules;
    }

    public Resource rules(Set<Rule> rules) {
        this.rules = rules;
        return this;
    }

    public Resource addRules(Rule rule) {
        this.rules.add(rule);
        rule.getResources().add(this);
        return this;
    }

    public Resource removeRules(Rule rule) {
        this.rules.remove(rule);
        rule.getResources().remove(this);
        return this;
    }

    public void setRules(Set<Rule> rules) {
        this.rules = rules;
    }

    public Set<Topic> getTopics() {
        return topics;
    }

    public Resource topics(Set<Topic> topics) {
        this.topics = topics;
        return this;
    }

    public Resource addTopic(Topic topic) {
        this.topics.add(topic);
        topic.getResources().add(this);
        return this;
    }

    public Resource removeTopic(Topic topic) {
        this.topics.remove(topic);
        topic.getResources().remove(this);
        return this;
    }

    public void setTopics(Set<Topic> topics) {
        this.topics = topics;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Resource)) {
            return false;
        }
        return id != null && id.equals(((Resource) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Resource{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", author='" + getAuthor() + "'" +
            ", accessUrl='" + getAccessUrl() + "'" +
            ", fileId='" + getFileId() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
