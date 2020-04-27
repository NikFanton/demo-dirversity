package com.dirversity.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A UserGroup.
 */
@Entity
@Table(name = "user_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "creation_date")
    private Instant creationDate;

    @ManyToOne
    @JsonIgnoreProperties("userGroups")
    private UserGroupType userGroupType;

    @ManyToMany(fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "user_group_users",
               joinColumns = @JoinColumn(name = "user_group_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"))
    private Set<User> users = new HashSet<>();

    @ManyToMany(mappedBy = "userGroups")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Rule> rules = new HashSet<>();

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

    public UserGroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public UserGroup creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public UserGroupType getUserGroupType() {
        return userGroupType;
    }

    public UserGroup userGroupType(UserGroupType userGroupType) {
        this.userGroupType = userGroupType;
        return this;
    }

    public void setUserGroupType(UserGroupType userGroupType) {
        this.userGroupType = userGroupType;
    }

    public Set<User> getUsers() {
        return users;
    }

    public UserGroup users(Set<User> users) {
        this.users = users;
        return this;
    }

    public UserGroup addUsers(User user) {
        this.users.add(user);
        return this;
    }

    public UserGroup removeUsers(User user) {
        this.users.remove(user);
        return this;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Rule> getRules() {
        return rules;
    }

    public UserGroup rules(Set<Rule> rules) {
        this.rules = rules;
        return this;
    }

    public UserGroup addRules(Rule rule) {
        this.rules.add(rule);
        rule.getUserGroups().add(this);
        return this;
    }

    public UserGroup removeRules(Rule rule) {
        this.rules.remove(rule);
        rule.getUserGroups().remove(this);
        return this;
    }

    public void setRules(Set<Rule> rules) {
        this.rules = rules;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserGroup)) {
            return false;
        }
        return id != null && id.equals(((UserGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserGroup{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }
}
