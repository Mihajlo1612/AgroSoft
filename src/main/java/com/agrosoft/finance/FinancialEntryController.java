package com.agrosoft.finance;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agrosoft.farm.Farm;
import com.agrosoft.farm.FarmAccessService;
import com.agrosoft.user.User;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/farms/{farmId}/entries")
public class FinancialEntryController {
    
    private FinancialEntryRepository financialEntryRepository;
    private FarmAccessService farmAccessService;

    public FinancialEntryController(FinancialEntryRepository financialEntryRepository, FarmAccessService farmAccessService) {
        this.financialEntryRepository = financialEntryRepository;
        this.farmAccessService = farmAccessService;
    }

    private FinancialEntryResponse convertToFinancialEntryResponse(FinancialEntry entry) {
        FinancialEntryResponse financialResponse = new FinancialEntryResponse(
            entry.getId(), entry.getFarm().getId(), entry.getType(), entry.getCategory(), entry.getAmount(), entry.getYear(), entry.getMonth(), entry.isPlanned(), entry.getDescription()
        );
        return financialResponse;
    }

    @GetMapping
    public ResponseEntity<?> financeList(@PathVariable Long farmId, @AuthenticationPrincipal User currentUser) {
        farmAccessService.requireOwnedFarm(farmId, currentUser);

        return ResponseEntity.ok(financialEntryRepository.findByFarmId(farmId).stream()
                .map(this::convertToFinancialEntryResponse)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> financeDetails(@PathVariable Long farmId, @PathVariable Long id, @AuthenticationPrincipal User currentUser) {
       farmAccessService.requireOwnedFarm(farmId, currentUser);

        Optional<FinancialEntry> entryOptional = financialEntryRepository.findById(id);
        if (entryOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Stavka ne postoji!");
        }

        FinancialEntry entry = entryOptional.get();
        if (!entry.getFarm().getId().equals(farmId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Zabranjen pristup trazenoj stavki!");
        }

        return ResponseEntity.ok(convertToFinancialEntryResponse(entry));
    }

    @PostMapping
    public ResponseEntity<?> createEntry(@PathVariable Long farmId, @Valid @RequestBody FinancialEntryRequest financialEntryRequest, BindingResult bindingResult, @AuthenticationPrincipal User currentUser) {

        Farm farm = farmAccessService.requireOwnedFarm(farmId, currentUser);

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .findFirst()
                    .orElse("Nevalidni podaci!");
            return ResponseEntity.badRequest().body(errorMessage);
        }

        FinancialEntry entry = new FinancialEntry(null, farm, financialEntryRequest.getType(), financialEntryRequest.getCategory(), financialEntryRequest.getAmount(), financialEntryRequest.getYear(), financialEntryRequest.getMonth(), financialEntryRequest.isPlanned(), financialEntryRequest.getDescription());
        FinancialEntry saved = financialEntryRepository.save(entry);

        return ResponseEntity.status(HttpStatus.CREATED).body(convertToFinancialEntryResponse(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> changeEntryDetails(@PathVariable Long farmId, @PathVariable Long id, @Valid @RequestBody FinancialEntryRequest request, BindingResult bindingResult, @AuthenticationPrincipal User currentUser) {
        
        farmAccessService.requireOwnedFarm(farmId, currentUser);

        Optional<FinancialEntry> entryOptional = financialEntryRepository.findById(id);
        if (entryOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Stavka ne postoji!");
        }

        FinancialEntry entry = entryOptional.get();
        if (!entry.getFarm().getId().equals(farmId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Zabranjen pristup trazenoj stavki!");
        }

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .findFirst()
                    .orElse("Nevalidni podaci!");
            return ResponseEntity.badRequest().body(errorMessage);
        }

        entry.setAmount(request.getAmount());
        entry.setCategory(request.getCategory());
        entry.setType(request.getType());
        entry.setDescription(request.getDescription());
        entry.setYear(request.getYear());
        entry.setMonth(request.getMonth());
        entry.setPlanned(request.isPlanned());
        
        FinancialEntry saved = financialEntryRepository.save(entry);

        return ResponseEntity.ok(convertToFinancialEntryResponse(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEntry(@PathVariable Long farmId, @PathVariable Long id, @AuthenticationPrincipal User currentUser) {
        
        farmAccessService.requireOwnedFarm(farmId, currentUser);

        Optional<FinancialEntry> entryOptional = financialEntryRepository.findById(id);
        if (entryOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Stavka ne postoji");
        }

        FinancialEntry entry = entryOptional.get();

        if (!entry.getFarm().getId().equals(farmId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Zabranjen pristup trazenoj stavki!");
        }

        financialEntryRepository.delete(entry);
        return ResponseEntity.noContent().build();
    }
}
