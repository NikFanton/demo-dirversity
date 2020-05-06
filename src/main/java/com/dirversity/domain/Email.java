package com.dirversity.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Email.
 */
@Entity
@Table(name = "email")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Email implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "body")
    private String body;

    @Column(name = "title")
    private String title;

    @Size(min = 2, max = 10)
    @Column(name = "lang_key", length = 10)
    private String langKey;

    @Column(name = "share_date_time")
    private Instant shareDateTime;

    @NotNull
    @Size(max = 50)
    @Column(name = "created_by", length = 50, nullable = false)
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Size(max = 50)
    @Column(name = "last_modified_by", length = 50)
    private String lastModifiedBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "email_to_users",
               joinColumns = @JoinColumn(name = "email_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "to_users_id", referencedColumnName = "id"))
    private Set<User> toUsers = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "email_cc_users",
               joinColumns = @JoinColumn(name = "email_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "cc_users_id", referencedColumnName = "id"))
    private Set<User> ccUsers = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "email_to_users_groups",
               joinColumns = @JoinColumn(name = "email_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "to_users_groups_id", referencedColumnName = "id"))
    private Set<UserGroup> toUsersGroups = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "email_cc_user_groups",
               joinColumns = @JoinColumn(name = "email_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "cc_user_groups_id", referencedColumnName = "id"))
    private Set<UserGroup> ccUserGroups = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "email_resources",
               joinColumns = @JoinColumn(name = "email_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "resources_id", referencedColumnName = "id"))
    private Set<Resource> resources = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public Email body(String body) {
        this.body = body;
        return this;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public Email title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLangKey() {
        return langKey;
    }

    public Email langKey(String langKey) {
        this.langKey = langKey;
        return this;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public Instant getShareDateTime() {
        return shareDateTime;
    }

    public Email shareDateTime(Instant shareDateTime) {
        this.shareDateTime = shareDateTime;
        return this;
    }

    public void setShareDateTime(Instant shareDateTime) {
        this.shareDateTime = shareDateTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Email createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Email createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public Email lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Email lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<User> getToUsers() {
        return toUsers;
    }

    public Email toUsers(Set<User> users) {
        this.toUsers = users;
        return this;
    }

    public Email addToUsers(User user) {
        this.toUsers.add(user);
        return this;
    }

    public Email removeToUsers(User user) {
        this.toUsers.remove(user);
        return this;
    }

    public void setToUsers(Set<User> users) {
        this.toUsers = users;
    }

    public Set<User> getCcUsers() {
        return ccUsers;
    }

    public Email ccUsers(Set<User> users) {
        this.ccUsers = users;
        return this;
    }

    public Email addCcUsers(User user) {
        this.ccUsers.add(user);
        return this;
    }

    public Email removeCcUsers(User user) {
        this.ccUsers.remove(user);
        return this;
    }

    public void setCcUsers(Set<User> users) {
        this.ccUsers = users;
    }

    public Set<UserGroup> getToUsersGroups() {
        return toUsersGroups;
    }

    public Email toUsersGroups(Set<UserGroup> userGroups) {
        this.toUsersGroups = userGroups;
        return this;
    }

    public void setToUsersGroups(Set<UserGroup> userGroups) {
        this.toUsersGroups = userGroups;
    }

    public Set<UserGroup> getCcUserGroups() {
        return ccUserGroups;
    }

    public Email ccUserGroups(Set<UserGroup> userGroups) {
        this.ccUserGroups = userGroups;
        return this;
    }

    public void setCcUserGroups(Set<UserGroup> userGroups) {
        this.ccUserGroups = userGroups;
    }

    public Set<Resource> getResources() {
        return resources;
    }

    public Email resources(Set<Resource> resources) {
        this.resources = resources;
        return this;
    }

    public void setResources(Set<Resource> resources) {
        this.resources = resources;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Email)) {
            return false;
        }
        return id != null && id.equals(((Email) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Email{" +
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
