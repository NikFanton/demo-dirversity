package com.dirversity.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dirversity.domain.Topic} entity.
 */
public class TopicDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Integer order;

    private String description;


    private Long contentModuleId;

    private String contentModuleName;

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

    public Long getContentModuleId() {
        return contentModuleId;
    }

    public void setContentModuleId(Long contentModuleId) {
        this.contentModuleId = contentModuleId;
    }

    public String getContentModuleName() {
        return contentModuleName;
    }

    public void setContentModuleName(String contentModuleName) {
        this.contentModuleName = contentModuleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TopicDTO topicDTO = (TopicDTO) o;
        if (topicDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), topicDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TopicDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", order=" + getOrder() +
            ", description='" + getDescription() + "'" +
            ", contentModule=" + getContentModuleId() +
            ", contentModule='" + getContentModuleName() + "'" +
            "}";
    }
}
