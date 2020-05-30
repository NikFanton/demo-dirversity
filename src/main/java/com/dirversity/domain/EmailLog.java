package com.dirversity.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A EmailLog.
 */
@Entity
@Table(name = "email_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EmailLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "log_message")
    private String logMessage;

    @ManyToOne(optional = false)
    @NotNull
    private Email email;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "email_log_shared_resources",
               joinColumns = @JoinColumn(name = "email_log_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "shared_resources_id", referencedColumnName = "id"))
    private Set<Resource> sharedResources = new HashSet<>();

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    @JsonIgnore
    private Instant createdDate = Instant.now();


    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public EmailLog logMessage(String logMessage) {
        this.logMessage = logMessage;
        return this;
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }

    public Email getEmail() {
        return email;
    }

    public EmailLog email(Email email) {
        this.email = email;
        return this;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Set<Resource> getSharedResources() {
        return sharedResources;
    }

    public EmailLog sharedResources(Set<Resource> resources) {
        this.sharedResources = resources;
        return this;
    }

    public void setSharedResources(Set<Resource> resources) {
        this.sharedResources = resources;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }
// jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmailLog)) {
            return false;
        }
        return id != null && id.equals(((EmailLog) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EmailLog{" +
            "id=" + getId() +
            ", logMessage='" + getLogMessage() + "'" +
            "}";
    }
}
