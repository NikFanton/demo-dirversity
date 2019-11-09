package com.dirversity.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.dirversity.domain.enumeration.AccessType;

/**
 * A Rule.
 */
@Entity
@Table(name = "rule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Rule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "access_type")
    private AccessType accessType;

    @Column(name = "jhi_from")
    private Instant from;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "rule_users",
               joinColumns = @JoinColumn(name = "rule_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"))
    private Set<User> users = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "rule_user_groups",
               joinColumns = @JoinColumn(name = "rule_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "user_groups_id", referencedColumnName = "id"))
    private Set<UserGroup> userGroups = new HashSet<>();

    @ManyToMany(mappedBy = "rules")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Resource> resources = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Rule name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AccessType getAccessType() {
        return accessType;
    }

    public Rule accessType(AccessType accessType) {
        this.accessType = accessType;
        return this;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }

    public Instant getFrom() {
        return from;
    }

    public Rule from(Instant from) {
        this.from = from;
        return this;
    }

    public void setFrom(Instant from) {
        this.from = from;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Rule users(Set<User> users) {
        this.users = users;
        return this;
    }

    public Rule addUsers(User user) {
        this.users.add(user);
        return this;
    }

    public Rule removeUsers(User user) {
        this.users.remove(user);
        return this;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<UserGroup> getUserGroups() {
        return userGroups;
    }

    public Rule userGroups(Set<UserGroup> userGroups) {
        this.userGroups = userGroups;
        return this;
    }

    public Rule addUserGroups(UserGroup userGroup) {
        this.userGroups.add(userGroup);
        userGroup.getRules().add(this);
        return this;
    }

    public Rule removeUserGroups(UserGroup userGroup) {
        this.userGroups.remove(userGroup);
        userGroup.getRules().remove(this);
        return this;
    }

    public void setUserGroups(Set<UserGroup> userGroups) {
        this.userGroups = userGroups;
    }

    public Set<Resource> getResources() {
        return resources;
    }

    public Rule resources(Set<Resource> resources) {
        this.resources = resources;
        return this;
    }

    public Rule addResources(Resource resource) {
        this.resources.add(resource);
        resource.getRules().add(this);
        return this;
    }

    public Rule removeResources(Resource resource) {
        this.resources.remove(resource);
        resource.getRules().remove(this);
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
        if (!(o instanceof Rule)) {
            return false;
        }
        return id != null && id.equals(((Rule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Rule{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", accessType='" + getAccessType() + "'" +
            ", from='" + getFrom() + "'" +
            "}";
    }
}
