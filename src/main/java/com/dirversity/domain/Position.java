package com.dirversity.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Position.
 */
@Entity
@Table(name = "position")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Position implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "title_short_form", nullable = false)
    private String titleShortForm;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "position_employees",
               joinColumns = @JoinColumn(name = "position_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "employees_id", referencedColumnName = "id"))
    private Set<User> employees = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Position title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleShortForm() {
        return titleShortForm;
    }

    public Position titleShortForm(String titleShortForm) {
        this.titleShortForm = titleShortForm;
        return this;
    }

    public void setTitleShortForm(String titleShortForm) {
        this.titleShortForm = titleShortForm;
    }

    public String getDescription() {
        return description;
    }

    public Position description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<User> getEmployees() {
        return employees;
    }

    public Position employees(Set<User> users) {
        this.employees = users;
        return this;
    }

    public Position addEmployees(User user) {
        this.employees.add(user);
        return this;
    }

    public Position removeEmployees(User user) {
        this.employees.remove(user);
        return this;
    }

    public void setEmployees(Set<User> users) {
        this.employees = users;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Position)) {
            return false;
        }
        return id != null && id.equals(((Position) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Position{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", titleShortForm='" + getTitleShortForm() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
