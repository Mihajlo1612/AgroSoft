package com.agrosoft.farm;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class FarmRequest {
    
    @NotBlank(message = "Naziv gazdinstva je obavezan!")
    private String name;

    @NotBlank(message = "Lokacija je obavezna!")
    private String location;

    @NotNull(message = "Velicina je obavezna!")
    @PositiveOrZero(message = "Velicina ne moze biti negativna!")
    private BigDecimal sizeInHectars;

    public FarmRequest() { }

    public FarmRequest(String name, String location, BigDecimal sizeInHectars) {
        this.name = name;
        this.location = location;
        this.sizeInHectars = sizeInHectars;
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

    @Override
    public String toString() {
        return "name='" + name + '\'' + 
                "location='" + location + '\'' + 
                "size(ha)='" + sizeInHectars;
    }
}
