package com.dirversity.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.dirversity.domain.Email} entity.
 */
public class EmailDTO implements Serializable {

    private Long id;

    private String body;

    private String title;

    @Size(min = 2, max = 10)
    private String langKey;

    private Instant shareDateTime;

    @NotNull
    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;


    private Set<UserDTO> toUsers = new HashSet<>();

    private Set<UserDTO> ccUsers = new HashSet<>();

    private Set<UserGroupDTO> toUsersGroups = new HashSet<>();

    private Set<UserGroupDTO> ccUserGroups = new HashSet<>();

    private Set<ResourceDTO> resources = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public Instant getShareDateTime() {
        return shareDateTime;
    }

    public void setShareDateTime(Instant shareDateTime) {
        this.shareDateTime = shareDateTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<UserDTO> getToUsers() {
        return toUsers;
    }

    public void setToUsers(Set<UserDTO> users) {
        this.toUsers = users;
    }

    public Set<UserDTO> getCcUsers() {
        return ccUsers;
    }

    public void setCcUsers(Set<UserDTO> users) {
        this.ccUsers = users;
    }

    public Set<UserGroupDTO> getToUsersGroups() {
        return toUsersGroups;
    }

    public void setToUsersGroups(Set<UserGroupDTO> userGroups) {
        this.toUsersGroups = userGroups;
    }

    public Set<UserGroupDTO> getCcUserGroups() {
        return ccUserGroups;
    }

    public void setCcUserGroups(Set<UserGroupDTO> userGroups) {
        this.ccUserGroups = userGroups;
    }

    public Set<ResourceDTO> getResources() {
        return resources;
    }

    public void setResources(Set<ResourceDTO> resources) {
        this.resources = resources;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmailDTO emailDTO = (EmailDTO) o;
        if (emailDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), emailDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmailDTO{" +
            "id=" + getId() +
            ", body='" + getBody() + "'" +
            ", title='" + getTitle() + "'" +
            ", langKey='" + getLangKey() + "'" +
            ", shareDateTime='" + getShareDateTime() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
