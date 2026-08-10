package com.agrosoft.farm;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agrosoft.user.User;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/farms")
public class FarmController {

    private final FarmRepository farmRepository;

    public FarmController(FarmRepository farmRepository) {
        this.farmRepository = farmRepository;
    }
    
    @GetMapping
    public List<FarmResponse> farmsList(@AuthenticationPrincipal User currUser) {
        return farmRepository.findByOwnerId(currUser.getId())
                .stream()
                .map(this::convertToFarmResponse)
                .toList();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> farmDetails(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {
        Optional<Farm> farmOptional = farmRepository.findById(id);
        
        if (farmOptional.isEmpty()) { 
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Gazdinstvo ne postoji!");
        }
        Farm farm = farmOptional.get();

        if (!farm.getOwner().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Zabranjen pristup!");
        } 

        return ResponseEntity.ok(convertToFarmResponse(farm));
    }

    private FarmResponse convertToFarmResponse(Farm farm) {
        FarmResponse farmResponse = new FarmResponse(farm.getId(), farm.getName(), farm.getLocation(), farm.getSizeInHectars(), farm.getOwner().getId());
        return farmResponse;
    }

    @PostMapping
    public ResponseEntity<?> createFarm(@Valid @RequestBody FarmRequest farmRequest, BindingResult bindingResult, @AuthenticationPrincipal User currentUser) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .findFirst()
                    .orElse("Nevalidni podaci!");
            return ResponseEntity.badRequest().body(errorMessage);
        }

        Farm farm = new Farm(null, farmRequest.getName(), farmRequest.getLocation(), farmRequest.getSizeInHectars(), currentUser);
        Farm newFarm = farmRepository.save(farm);

        return ResponseEntity.status(HttpStatus.CREATED).body(convertToFarmResponse(newFarm));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> changeFarmDetails(@PathVariable Long id, @Valid @RequestBody FarmRequest farmRequest, BindingResult bindingResult, @AuthenticationPrincipal User currentUser) {
        
        Optional<Farm> farmOptional = farmRepository.findById(id);
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .findFirst()
                    .orElse("Nevalid podaci!");
            return ResponseEntity.badRequest().body(errorMessage);
        }

        if (farmOptional.isEmpty()) { 
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Gazdinstvo ne postoji!");
        }

        Farm farm = farmOptional.get();
        if (!farm.getOwner().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Zabranjen pristup!");
        } 

        farm.setName(farmRequest.getName());
        farm.setLocation(farmRequest.getLocation());
        farm.setSizeInHectars(farmRequest.getSizeInHectars());

        farmRepository.save(farm);
        return ResponseEntity.ok(convertToFarmResponse(farm));
    }
}
