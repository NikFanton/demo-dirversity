package com.dirversity.service.dto;
import javax.persistence.Lob;
import java.time.Instant;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link com.dirversity.domain.Resource} entity.
 */
public class ResourceDTO implements Serializable {

    private Long id;

    private String name;

    private String author;

    private String accessUrl;

    private Instant createDate;


    private Long publisherId;

    private String publisherLastName;

    private Set<ResourceTypeDTO> resourceTypes = new HashSet<>();

    private Set<RuleDTO> rules = new HashSet<>();

    @Lob
    private byte[] data;

    private String dataContentType;

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAccessUrl() {
        return accessUrl;
    }

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long userId) {
        this.publisherId = userId;
    }

    public String getPublisherLastName() {
        return publisherLastName;
    }

    public void setPublisherLastName(String userLastName) {
        this.publisherLastName = userLastName;
    }

    public Set<ResourceTypeDTO> getResourceTypes() {
        return resourceTypes;
    }

    public void setResourceTypes(Set<ResourceTypeDTO> resourceTypes) {
        this.resourceTypes = resourceTypes;
    }

    public Set<RuleDTO> getRules() {
        return rules;
    }

    public void setRules(Set<RuleDTO> rules) {
        this.rules = rules;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getDataContentType() {
        return dataContentType;
    }

    public void setDataContentType(String dataContentType) {
        this.dataContentType = dataContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ResourceDTO resourceDTO = (ResourceDTO) o;
        if (resourceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resourceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ResourceDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", author='" + getAuthor() + "'" +
            ", accessUrl='" + getAccessUrl() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", publisher=" + getPublisherId() +
            ", publisher='" + getPublisherLastName() + "'" +
            "}";
    }
}
