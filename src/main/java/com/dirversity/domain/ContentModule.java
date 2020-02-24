package com.dirversity.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ContentModule.
 */
@Entity
@Table(name = "content_module")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ContentModule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "jhi_order", nullable = false)
    private Integer order;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "contentModule")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Topic> topics = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("contentModules")
    private Curriculum curriculum;

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

    public ContentModule name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public ContentModule order(Integer order) {
        this.order = order;
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getDescription() {
        return description;
    }

    public ContentModule description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Topic> getTopics() {
        return topics;
    }

    public ContentModule topics(Set<Topic> topics) {
        this.topics = topics;
        return this;
    }

    public ContentModule addTopics(Topic topic) {
        this.topics.add(topic);
        topic.setContentModule(this);
        return this;
    }

    public ContentModule removeTopics(Topic topic) {
        this.topics.remove(topic);
        topic.setContentModule(null);
        return this;
    }

    public void setTopics(Set<Topic> topics) {
        this.topics = topics;
    }

    public Curriculum getCurriculum() {
        return curriculum;
    }

    public ContentModule curriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
        return this;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContentModule)) {
            return false;
        }
        return id != null && id.equals(((ContentModule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ContentModule{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", order=" + getOrder() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
