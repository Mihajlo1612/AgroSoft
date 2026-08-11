package com.agrosoft.finance;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialEntryRepository extends JpaRepository<FinancialEntry, Long>{
    
    List<FinancialEntry> findByFarmId(Long farmId);
}
