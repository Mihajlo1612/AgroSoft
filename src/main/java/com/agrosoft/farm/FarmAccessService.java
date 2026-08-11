package com.agrosoft.farm;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.agrosoft.user.User;

@Service
public class FarmAccessService {
    
    private FarmRepository farmRepository;

    public FarmAccessService(FarmRepository farmRepository) {
        this.farmRepository = farmRepository;
    }

    public Farm requireOwnedFarm(Long farmId, User currentUser) {
        Optional<Farm> farmOptional = farmRepository.findById(farmId);
        if (farmOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Gazdinstvo ne postoji!");
        }

        Farm farm = farmOptional.get();

        if (!farm.getOwner().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Zabranjen pristup!");
        }

        return farm;        
    }
}
