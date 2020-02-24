package com.dirversity.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dirversity.domain.ContentModule} entity.
 */
public class ContentModuleDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Integer order;

    private String description;


    private Long curriculumId;

    private String curriculumName;

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

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCurriculumId() {
        return curriculumId;
    }

    public void setCurriculumId(Long curriculumId) {
        this.curriculumId = curriculumId;
    }

    public String getCurriculumName() {
        return curriculumName;
    }

    public void setCurriculumName(String curriculumName) {
        this.curriculumName = curriculumName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ContentModuleDTO contentModuleDTO = (ContentModuleDTO) o;
        if (contentModuleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contentModuleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContentModuleDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", order=" + getOrder() +
            ", description='" + getDescription() + "'" +
            ", curriculum=" + getCurriculumId() +
            ", curriculum='" + getCurriculumName() + "'" +
            "}";
    }
}
