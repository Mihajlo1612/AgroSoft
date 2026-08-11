package com.agrosoft.finance;

import java.math.BigDecimal;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class FinancialEntryRequest {
    
    @NotNull(message = "Tip je obavezan!")
    private EntryType type;

    @NotNull(message = "Kolicina je obavezna!")
    @PositiveOrZero(message = "Kolicina mora biti pozitivna!")
    private BigDecimal amount;

    @NotNull(message = "Godina je obavezna!")
    private Integer year;

    @NotNull(message = "Kategorija je obavezna!")
    private EntryCategory category;

    @NotNull(message = "Mesec je obavezan!")
    @Min(value = 1, message = "Unesite mesec izmedju 1 i 12!")
    @Max(value = 12, message = "Unesite mesec izmedju 1 i 12!")
    private Integer month;

    private boolean planned;

    @NotNull(message = "Opis je obavezan!")
    private String description;

    public FinancialEntryRequest() {}

    public FinancialEntryRequest(EntryType type, BigDecimal amount, EntryCategory category, Integer year, Integer month, boolean planned, String description) {
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.year = year;
        this.month = month;
        this.planned = planned;
        this.description = description;
    }

    public EntryType getType() {
        return type;
    }

    public void setType(EntryType type) {
        this.type = type;
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

    public EntryCategory getCategory() {
        return category;
    }

    public void setCategory(EntryCategory category) {
        this.category = category;
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
        return "FinancialEntryRequest [type=" + type + ", amount=" + amount + ", year=" + year + ", category="
                + category + ", month=" + month + ", planned=" + planned + ", description=" + description + "]";
    }
}
