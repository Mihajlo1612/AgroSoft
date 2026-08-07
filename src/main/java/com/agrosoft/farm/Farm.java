package com.agrosoft.farm;

import java.math.BigDecimal;

import com.agrosoft.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "farms")
public class Farm {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String location;

    @NotNull
    @PositiveOrZero
    private BigDecimal sizeInHectars;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    public Farm() { }

    public Farm(Long id, String name, String location, BigDecimal sizeInHectars, User owner) {
        this.id = id;
        this.location = location;
        this.sizeInHectars = sizeInHectars;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public User getOwner() {
        return owner;
    }

    public BigDecimal getSizeInHectars() {
        return sizeInHectars;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setSizeInHectars(BigDecimal sizeInHectars) {
        this.sizeInHectars = sizeInHectars;
    }
}
