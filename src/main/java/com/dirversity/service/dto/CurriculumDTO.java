package com.dirversity.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link com.dirversity.domain.Curriculum} entity.
 */
public class CurriculumDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String description;

    private String explanatoryNote;

    @Min(value = 0)
    private Integer year;

    @Min(value = 0)
    private Integer credits;


    private Long originFileId;

    private String originFileName;

    private Set<CurriculumTagDTO> curriculumTags = new HashSet<>();

    private Set<UserDTO> teachers = new HashSet<>();

    private Set<ContentModuleDTO> contentModules = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExplanatoryNote() {
        return explanatoryNote;
    }

    public void setExplanatoryNote(String explanatoryNote) {
        this.explanatoryNote = explanatoryNote;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Long getOriginFileId() {
        return originFileId;
    }

    public void setOriginFileId(Long resourceId) {
        this.originFileId = resourceId;
    }

    public String getOriginFileName() {
        return originFileName;
    }

    public void setOriginFileName(String resourceName) {
        this.originFileName = resourceName;
    }

    public Set<CurriculumTagDTO> getCurriculumTags() {
        return curriculumTags;
    }

    public void setCurriculumTags(Set<CurriculumTagDTO> curriculumTags) {
        this.curriculumTags = curriculumTags;
    }

    public Set<UserDTO> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<UserDTO> users) {
        this.teachers = users;
    }

    public Set<ContentModuleDTO> getContentModules() {
        return contentModules;
    }

    public void setContentModules(Set<ContentModuleDTO> contentModules) {
        this.contentModules = contentModules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CurriculumDTO curriculumDTO = (CurriculumDTO) o;
        if (curriculumDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), curriculumDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CurriculumDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", explanatoryNote='" + getExplanatoryNote() + "'" +
            ", year=" + getYear() +
            ", credits=" + getCredits() +
            ", originFile=" + getOriginFileId() +
            ", originFile='" + getOriginFileName() + "'" +
            "}";
    }
}
