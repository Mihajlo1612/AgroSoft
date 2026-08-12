package com.agrosoft.farm;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.agrosoft.auth.JwtResponse;
import com.agrosoft.auth.LoginRequest;
import com.agrosoft.auth.RegisterRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
public class FarmControllerTest {
    
    @Autowired
    private TestRestTemplate restTemplate;

    private String registerAndLogin() {
        String uuid = UUID.randomUUID().toString();
        RegisterRequest registerRequest = new RegisterRequest("user" + uuid + "@test.com", "user" + uuid, "lozinka123", "lozinka123");

        restTemplate.postForEntity("/api/auth/register", registerRequest, String.class);

        LoginRequest loginRequest = new LoginRequest("user" + uuid, "lozinka123");

        ResponseEntity<JwtResponse> loginResponse = restTemplate.postForEntity("/api/auth/login", loginRequest, JwtResponse.class);

        return loginResponse.getBody().getToken();
    }

    @Test
    public void createFarm_returns201_whenDataIsValid() {
        String token = registerAndLogin();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        FarmRequest farmRequest = new FarmRequest("Test Farma", "Vranovo", BigDecimal.valueOf(10.5));

        HttpEntity<FarmRequest> entity = new HttpEntity<>(farmRequest, headers);
        ResponseEntity<FarmResponse> response = restTemplate.postForEntity("/api/farms", entity, FarmResponse.class);

        // assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void farmDetails_returns404_whenFarmDoesNotExist() {
        String token = registerAndLogin();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<FarmResponse> response = restTemplate.exchange("/api/farms/" + Long.MAX_VALUE, HttpMethod.GET, entity, FarmResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void farmDetails_return403_whenNotOwner() {

        String ownerToken = registerAndLogin();
        String otherToken = registerAndLogin();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(ownerToken);

        FarmRequest farmRequest = new FarmRequest("Owner's farm", "Radinac", BigDecimal.valueOf(11.5));

        HttpEntity<FarmRequest> entity = new HttpEntity<>(farmRequest, headers);
        ResponseEntity<FarmResponse> response = restTemplate.postForEntity("/api/farms", entity, FarmResponse.class);

        Long farmId = response.getBody().getId();

        HttpHeaders headers2 = new HttpHeaders();
        headers2.setBearerAuth(otherToken);
        
        HttpEntity<Void> entity2 = new HttpEntity<>(headers2);
        ResponseEntity<FarmResponse> finalResponse = restTemplate.exchange("/api/farms/" + farmId, HttpMethod.GET, entity2, FarmResponse.class);

        assertThat(finalResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void farmDetails_return402_whenNotOwner() {

        String ownerToken = registerAndLogin();
        String otherToken = registerAndLogin();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(ownerToken);

        FarmRequest farmRequest = new FarmRequest("Owner's farm", "Radinac", BigDecimal.valueOf(11.5));

        HttpEntity<FarmRequest> entity = new HttpEntity<>(farmRequest, headers);
        ResponseEntity<FarmResponse> response = restTemplate.postForEntity("/api/farms", entity, FarmResponse.class);

        Long farmId = response.getBody().getId();

        HttpHeaders headers2 = new HttpHeaders();
        headers2.setBearerAuth(otherToken);
        
        HttpEntity<Void> entity2 = new HttpEntity<>(headers2);
        ResponseEntity<FarmResponse> finalResponse = restTemplate.exchange("/api/farms/" + farmId, HttpMethod.GET, entity2, FarmResponse.class);

        assertThat(finalResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}
