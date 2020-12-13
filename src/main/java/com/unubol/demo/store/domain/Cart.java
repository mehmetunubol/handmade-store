package com.unubol.demo.store.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.unubol.demo.store.domain.enumeration.OrderStatus;

import com.unubol.demo.store.domain.enumeration.PaymentMethod;

/**
 * A Cart.
 */
@Entity
@Table(name = "cart")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "notes")
    private String notes;

    @NotNull
    @Column(name = "placed_date", nullable = false)
    private Instant placedDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "total_price", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalPrice;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    @OneToMany(mappedBy = "cart")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<UserAddress> addresses = new HashSet<>();

    @OneToMany(mappedBy = "cart")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<OrderItems> orders = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "carts", allowSetters = true)
    private ClientDetails clientDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public Cart notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Instant getPlacedDate() {
        return placedDate;
    }

    public Cart placedDate(Instant placedDate) {
        this.placedDate = placedDate;
        return this;
    }

    public void setPlacedDate(Instant placedDate) {
        this.placedDate = placedDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Cart status(OrderStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public Cart totalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public Cart paymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Set<UserAddress> getAddresses() {
        return addresses;
    }

    public Cart addresses(Set<UserAddress> userAddresses) {
        this.addresses = userAddresses;
        return this;
    }

    public Cart addAddress(UserAddress userAddress) {
        this.addresses.add(userAddress);
        userAddress.setCart(this);
        return this;
    }

    public Cart removeAddress(UserAddress userAddress) {
        this.addresses.remove(userAddress);
        userAddress.setCart(null);
        return this;
    }

    public void setAddresses(Set<UserAddress> userAddresses) {
        this.addresses = userAddresses;
    }

    public Set<OrderItems> getOrders() {
        return orders;
    }

    public Cart orders(Set<OrderItems> orderItems) {
        this.orders = orderItems;
        return this;
    }

    public Cart addOrder(OrderItems orderItems) {
        this.orders.add(orderItems);
        orderItems.setCart(this);
        return this;
    }

    public Cart removeOrder(OrderItems orderItems) {
        this.orders.remove(orderItems);
        orderItems.setCart(null);
        return this;
    }

    public void setOrders(Set<OrderItems> orderItems) {
        this.orders = orderItems;
    }

    public ClientDetails getClientDetails() {
        return clientDetails;
    }

    public Cart clientDetails(ClientDetails clientDetails) {
        this.clientDetails = clientDetails;
        return this;
    }

    public void setClientDetails(ClientDetails clientDetails) {
        this.clientDetails = clientDetails;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cart)) {
            return false;
        }
        return id != null && id.equals(((Cart) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cart{" +
            "id=" + getId() +
            ", notes='" + getNotes() + "'" +
            ", placedDate='" + getPlacedDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", totalPrice=" + getTotalPrice() +
            ", paymentMethod='" + getPaymentMethod() + "'" +
            "}";
    }
}
