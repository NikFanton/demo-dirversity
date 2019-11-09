package com.dirversity.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.dirversity.domain.enumeration.AccessType;

/**
 * A DTO for the {@link com.dirversity.domain.Rule} entity.
 */
public class RuleDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private AccessType accessType;

    private Instant from;


    private Set<UserDTO> users = new HashSet<>();

    private Set<UserGroupDTO> userGroups = new HashSet<>();

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

    public AccessType getAccessType() {
        return accessType;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }

    public Instant getFrom() {
        return from;
    }

    public void setFrom(Instant from) {
        this.from = from;
    }

    public Set<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDTO> users) {
        this.users = users;
    }

    public Set<UserGroupDTO> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(Set<UserGroupDTO> userGroups) {
        this.userGroups = userGroups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RuleDTO ruleDTO = (RuleDTO) o;
        if (ruleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ruleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RuleDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", accessType='" + getAccessType() + "'" +
            ", from='" + getFrom() + "'" +
            "}";
    }
}
