package com.dirversity.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link com.dirversity.domain.Position} entity.
 */
public class PositionDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String titleShortForm;

    private String description;


    private Set<UserDTO> employees = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleShortForm() {
        return titleShortForm;
    }

    public void setTitleShortForm(String titleShortForm) {
        this.titleShortForm = titleShortForm;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<UserDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<UserDTO> users) {
        this.employees = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PositionDTO positionDTO = (PositionDTO) o;
        if (positionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), positionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PositionDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", titleShortForm='" + getTitleShortForm() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
