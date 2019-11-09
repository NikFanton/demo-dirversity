package com.dirversity.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link com.dirversity.domain.UserGroup} entity.
 */
public class UserGroupDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private Instant creationDate;


    private Long userGroupTypeId;

    private String userGroupTypeName;

    private Set<UserDTO> users = new HashSet<>();

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

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Long getUserGroupTypeId() {
        return userGroupTypeId;
    }

    public void setUserGroupTypeId(Long userGroupTypeId) {
        this.userGroupTypeId = userGroupTypeId;
    }

    public String getUserGroupTypeName() {
        return userGroupTypeName;
    }

    public void setUserGroupTypeName(String userGroupTypeName) {
        this.userGroupTypeName = userGroupTypeName;
    }

    public Set<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDTO> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserGroupDTO userGroupDTO = (UserGroupDTO) o;
        if (userGroupDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userGroupDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserGroupDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", userGroupType=" + getUserGroupTypeId() +
            ", userGroupType='" + getUserGroupTypeName() + "'" +
            "}";
    }
}
