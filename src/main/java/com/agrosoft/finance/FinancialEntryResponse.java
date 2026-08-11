package com.agrosoft.finance;

import java.math.BigDecimal;

public class FinancialEntryResponse {
    
    private Long id;
    private Long farmId;
    private EntryType type;
    private EntryCategory category;
    private BigDecimal amount;
    private Integer year;
    private Integer month;
    private boolean planned;
    private String description;

    public FinancialEntryResponse() {}

    public FinancialEntryResponse(Long id, Long farmId, EntryType type, EntryCategory category, BigDecimal amount,
            Integer year, Integer month, boolean planned, String description) {
        this.id = id;
        this.farmId = farmId;
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

    public Long getFarmId() {
        return farmId;
    }

    public void setFarmId(Long farmId) {
        this.farmId = farmId;
    }

    public EntryType getType() {
        return type;
    }

    public void setType(EntryType type) {
        this.type = type;
    }

    public EntryCategory getCategory() {
        return category;
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

    public boolean isPlanned() {
        return planned;
    }

    public void setPlanned(boolean planned) {
        this.planned = planned;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "FinancialEntryResponse [id=" + id + ", farmId=" + farmId + ", type=" + type + ", category=" + category
                + ", amount=" + amount + ", year=" + year + ", month=" + month + ", planned=" + planned
                + ", description=" + description + "]";
    }
}
