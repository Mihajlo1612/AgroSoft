package com.agrosoft.farm;

import java.math.BigDecimal;

public class FarmResponse {
    
    private Long id;
    private String name;
    private String location;
    private BigDecimal sizeInHectars;
    private Long ownerId;

    public FarmResponse() {}

    public FarmResponse(Long id, String name, String location, BigDecimal sizeInHectars, Long ownerId) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.sizeInHectars = sizeInHectars;
        this.ownerId = ownerId;
    }

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getSizeInHectars() {
        return sizeInHectars;
    }

    public void setSizeInHectars(BigDecimal sizeInHectars) {
        this.sizeInHectars = sizeInHectars;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "FarmResponse [id=" + id + ", name=" + name + ", location=" + location + ", sizeInHectars="
                + sizeInHectars + ", ownerId=" + ownerId + "]";
    }

}
