package com.dirversity.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link com.dirversity.domain.EmailLog} entity.
 */
public class EmailLogDTO implements Serializable {

    private Long id;

    private String logMessage;

    @NotNull
    private Instant createdDate;

    private Long emailId;

    private Set<ResourceDTO> sharedResources = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }

    public Long getEmailId() {
        return emailId;
    }

    public void setEmailId(Long emailId) {
        this.emailId = emailId;
    }

    public Set<ResourceDTO> getSharedResources() {
        return sharedResources;
    }

    public void setSharedResources(Set<ResourceDTO> resources) {
        this.sharedResources = resources;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmailLogDTO emailLogDTO = (EmailLogDTO) o;
        if (emailLogDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), emailLogDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmailLogDTO{" +
            "id=" + getId() +
            ", logMessage='" + getLogMessage() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", email=" + getEmailId() +
            "}";
    }
}
