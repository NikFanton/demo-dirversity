package com.dirversity.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A Curriculum.
 */
@Entity
@Table(name = "curriculum")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Curriculum implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "explanatory_note")
    private String explanatoryNote;

    @Min(value = 0)
    @Column(name = "year")
    private Integer year;

    @Min(value = 0)
    @Column(name = "credits")
    private Integer credits;

    @OneToOne
    @JoinColumn(unique = true)
    private Resource originFile;

    @OneToMany(mappedBy = "curriculum")
    @OrderBy("order")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<ContentModule> contentModules = new ArrayList<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "curriculum_curriculum_tags",
               joinColumns = @JoinColumn(name = "curriculum_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "curriculum_tags_id", referencedColumnName = "id"))
    private Set<CurriculumTag> curriculumTags = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "curriculum_teachers",
               joinColumns = @JoinColumn(name = "curriculum_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "teachers_id", referencedColumnName = "id"))
    private Set<User> teachers = new HashSet<>();

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

    public Curriculum name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Curriculum description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExplanatoryNote() {
        return explanatoryNote;
    }

    public Curriculum explanatoryNote(String explanatoryNote) {
        this.explanatoryNote = explanatoryNote;
        return this;
    }

    public void setExplanatoryNote(String explanatoryNote) {
        this.explanatoryNote = explanatoryNote;
    }

    public Integer getYear() {
        return year;
    }

    public Curriculum year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getCredits() {
        return credits;
    }

    public Curriculum credits(Integer credits) {
        this.credits = credits;
        return this;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Resource getOriginFile() {
        return originFile;
    }

    public Curriculum originFile(Resource resource) {
        this.originFile = resource;
        return this;
    }

    public void setOriginFile(Resource resource) {
        this.originFile = resource;
    }

    public List<ContentModule> getContentModules() {
        return contentModules;
    }

    public Curriculum contentModules(List<ContentModule> contentModules) {
        this.contentModules = contentModules;
        return this;
    }

    public Curriculum addContentModules(ContentModule contentModule) {
        this.contentModules.add(contentModule);
        contentModule.setCurriculum(this);
        return this;
    }

    public Curriculum removeContentModules(ContentModule contentModule) {
        this.contentModules.remove(contentModule);
        contentModule.setCurriculum(null);
        return this;
    }

    public void setContentModules(List<ContentModule> contentModules) {
        this.contentModules = contentModules;
    }

    public Set<CurriculumTag> getCurriculumTags() {
        return curriculumTags;
    }

    public Curriculum curriculumTags(Set<CurriculumTag> curriculumTags) {
        this.curriculumTags = curriculumTags;
        return this;
    }

    public Curriculum addCurriculumTags(CurriculumTag curriculumTag) {
        this.curriculumTags.add(curriculumTag);
        curriculumTag.getCurricula().add(this);
        return this;
    }

    public Curriculum removeCurriculumTags(CurriculumTag curriculumTag) {
        this.curriculumTags.remove(curriculumTag);
        curriculumTag.getCurricula().remove(this);
        return this;
    }

    public void setCurriculumTags(Set<CurriculumTag> curriculumTags) {
        this.curriculumTags = curriculumTags;
    }

    public Set<User> getTeachers() {
        return teachers;
    }

    public Curriculum teachers(Set<User> users) {
        this.teachers = users;
        return this;
    }

    public Curriculum addTeachers(User user) {
        this.teachers.add(user);
        return this;
    }

    public Curriculum removeTeachers(User user) {
        this.teachers.remove(user);
        return this;
    }

    public void setTeachers(Set<User> users) {
        this.teachers = users;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Curriculum)) {
            return false;
        }
        return id != null && id.equals(((Curriculum) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Curriculum{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", explanatoryNote='" + getExplanatoryNote() + "'" +
            ", year=" + getYear() +
            ", credits=" + getCredits() +
            "}";
    }
}
