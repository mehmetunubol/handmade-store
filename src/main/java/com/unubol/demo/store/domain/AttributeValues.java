package com.unubol.demo.store.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A AttributeValues.
 */
@Entity
@Table(name = "attribute_values")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AttributeValues implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "value", nullable = false)
    private String value;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "price", precision = 21, scale = 2, nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JsonIgnoreProperties(value = "attributeValues", allowSetters = true)
    private Attribute attribute;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public AttributeValues value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public AttributeValues price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public AttributeValues attribute(Attribute attribute) {
        this.attribute = attribute;
        return this;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttributeValues)) {
            return false;
        }
        return id != null && id.equals(((AttributeValues) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttributeValues{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", price=" + getPrice() +
            "}";
    }
}
