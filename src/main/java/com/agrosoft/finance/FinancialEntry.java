package com.agrosoft.finance;

import java.math.BigDecimal;
import com.agrosoft.farm.Farm;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "financial_entries")
public class FinancialEntry {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "farm_id")
    private Farm farm;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EntryType type;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EntryCategory category;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private Integer year;

    @NotNull
    private Integer month;

    private boolean planned;
    private String description;

    public FinancialEntry() { }

    public FinancialEntry(Long id, Farm farm, EntryType type, EntryCategory category, BigDecimal amount, Integer year, Integer month, boolean planned, String description) {
        this.id = id;
        this.farm = farm;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.year = year;
        this.month = month;
        this.planned = planned;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public EntryType getType() {
        return type;
    }

    public EntryCategory getCategory() {
        return category;
    }

    public void setType(EntryType type) {
        this.type = type;
    }

    public void setCategory(EntryCategory category) {
        this.category = category;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPlanned() {
        return planned;
    }

    public void setPlanned(boolean planned) {
        this.planned = planned;
    }

    @Override
    public String toString() {
        return "FinancialEntry{" +
                "id=" + id +
                ", farmId=" + (farm != null ? farm.getId() : null) +
                ", type=" + type +
                ", amount=" + amount +
                ", year=" + year +
                ", month=" + month +
                '}';
    }
}
