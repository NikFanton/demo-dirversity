package com.dirversity.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A CurriculumTag.
 */
@Entity
@Table(name = "curriculum_tag")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CurriculumTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "curriculumTags")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Curriculum> curricula = new HashSet<>();

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

    public CurriculumTag name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Curriculum> getCurricula() {
        return curricula;
    }

    public CurriculumTag curricula(Set<Curriculum> curricula) {
        this.curricula = curricula;
        return this;
    }

    public CurriculumTag addCurriculum(Curriculum curriculum) {
        this.curricula.add(curriculum);
        curriculum.getCurriculumTags().add(this);
        return this;
    }

    public CurriculumTag removeCurriculum(Curriculum curriculum) {
        this.curricula.remove(curriculum);
        curriculum.getCurriculumTags().remove(this);
        return this;
    }

    public void setCurricula(Set<Curriculum> curricula) {
        this.curricula = curricula;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CurriculumTag)) {
            return false;
        }
        return id != null && id.equals(((CurriculumTag) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CurriculumTag{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
