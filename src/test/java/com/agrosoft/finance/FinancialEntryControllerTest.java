package com.agrosoft.finance;

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
import com.agrosoft.farm.FarmRequest;
import com.agrosoft.farm.FarmResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
public class FinancialEntryControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private String registerAndLogin() {
        String uuid = UUID.randomUUID().toString();
        RegisterRequest registerRequest = new RegisterRequest("user" + uuid + "@test.com", "user" + uuid, "lozinka123",
                "lozinka123");

        restTemplate.postForEntity("/api/auth/register", registerRequest, String.class);

        LoginRequest loginRequest = new LoginRequest("user" + uuid, "lozinka123");

        ResponseEntity<JwtResponse> loginResponse = restTemplate.postForEntity("/api/auth/login", loginRequest,
                JwtResponse.class);

        return loginResponse.getBody().getToken();
    }

    @Test
    public void createEntry_returns201_whenDataIsValid() {
        String token = registerAndLogin();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        FarmRequest farmRequest = new FarmRequest("TestFarm", "Drugovac", BigDecimal.valueOf(15.3));

        HttpEntity<FarmRequest> entity = new HttpEntity<>(farmRequest, headers);
        ResponseEntity<FarmResponse> response = restTemplate.postForEntity("/api/farms", entity, FarmResponse.class);

        Long farmId = response.getBody().getId();

        FinancialEntryRequest financialRequest = new FinancialEntryRequest(EntryType.RASHOD,
                BigDecimal.valueOf(454.234), EntryCategory.POTROSNA_ROBA, 2026, 4, true, "Jagode");

        HttpEntity<FinancialEntryRequest> financialEntity = new HttpEntity<>(financialRequest, headers);
        ResponseEntity<FinancialEntryResponse> financialResponse = restTemplate
                .postForEntity("/api/farms/" + farmId + "/entries", financialEntity, FinancialEntryResponse.class);

        assertThat(financialResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void financeDetails_returns404_whenEntryDoesNotExist() {
        String token = registerAndLogin();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        FarmRequest farmRequest = new FarmRequest("Radojevic", "Vranovo", BigDecimal.valueOf(30.1));

        HttpEntity<FarmRequest> entity = new HttpEntity<>(farmRequest, headers);
        ResponseEntity<FarmResponse> response = restTemplate.postForEntity("/api/farms", entity, FarmResponse.class);

        Long farmId = response.getBody().getId();

        HttpEntity<Void> entity2 = new HttpEntity<>(headers);
        ResponseEntity<String> financialResponse = restTemplate
                .exchange("/api/farms/" + farmId + "/entries/" + Long.MAX_VALUE, HttpMethod.GET, entity2, String.class);

        assertThat(financialResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void financeDetails_returns403_whenNotOwner() {
        String ownerToken = registerAndLogin();
        String otherToken = registerAndLogin();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(ownerToken);

        HttpHeaders headers2 = new HttpHeaders();
        headers2.setBearerAuth(otherToken);

        FarmRequest farmRequest = new FarmRequest("Draganova farma", "Seone", BigDecimal.valueOf(13.2));

        HttpEntity<FarmRequest> entity = new HttpEntity<>(farmRequest, headers);
        ResponseEntity<FarmResponse> response = restTemplate.postForEntity("/api/farms", entity, FarmResponse.class);

        Long farmId = response.getBody().getId();

        FinancialEntryRequest financialRequest = new FinancialEntryRequest(EntryType.PRIHOD,
                BigDecimal.valueOf(400.000), EntryCategory.MASINA, 2026, 7, true, "Setvospremac");

        HttpEntity<FinancialEntryRequest> entity2 = new HttpEntity<>(financialRequest, headers);
        ResponseEntity<FinancialEntryResponse> financialResponse = restTemplate
                .postForEntity("/api/farms/" + farmId + "/entries", entity2, FinancialEntryResponse.class);

        Long entryId = financialResponse.getBody().getId();

        HttpEntity<Void> entity3 = new HttpEntity<>(headers2);
        ResponseEntity<String> finalResponse = restTemplate.exchange("/api/farms/" + farmId + "/entries/" + entryId,
                HttpMethod.GET, entity3, String.class);

        assertThat(finalResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void createEntry_returns403_whenNotOwner() {
        String ownerToken = registerAndLogin();
        String otherToken = registerAndLogin();

        HttpHeaders ownerHeaders = new HttpHeaders();
        ownerHeaders.setBearerAuth(ownerToken);

        HttpHeaders otherHeaders = new HttpHeaders();
        otherHeaders.setBearerAuth(otherToken);

        FarmRequest farmRequest = new FarmRequest("Radojeva farma", "Binovac", BigDecimal.valueOf(12.5));

        HttpEntity<FarmRequest> entity = new HttpEntity<>(farmRequest, ownerHeaders);
        ResponseEntity<FarmResponse> response = restTemplate.postForEntity("/api/farms", entity, FarmResponse.class);

        Long farmId = response.getBody().getId();

        FinancialEntryRequest financialRequest = new FinancialEntryRequest(EntryType.PRIHOD,
                BigDecimal.valueOf(400.000), EntryCategory.MASINA, 2026, 7, true, "Setvospremac");

        HttpEntity<FinancialEntryRequest> entity2 = new HttpEntity<>(financialRequest, otherHeaders);
        ResponseEntity<String> financialResponse = restTemplate.postForEntity("/api/farms/" + farmId + "/entries",
                entity2, String.class);

        assertThat(financialResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void deleteEntry_returns204_whenSuccessful() {
        String ownerToken = registerAndLogin();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(ownerToken);

        FarmRequest farmRequest = new FarmRequest("TestFarm", "Drugovac", BigDecimal.valueOf(15.3));

        HttpEntity<FarmRequest> entity = new HttpEntity<>(farmRequest, headers);
        ResponseEntity<FarmResponse> response = restTemplate.postForEntity("/api/farms", entity, FarmResponse.class);

        Long farmId = response.getBody().getId();

        FinancialEntryRequest financialRequest = new FinancialEntryRequest(EntryType.RASHOD,
                BigDecimal.valueOf(454.234), EntryCategory.POTROSNA_ROBA, 2026, 4, true, "Jagode");

        HttpEntity<FinancialEntryRequest> financialEntity = new HttpEntity<>(financialRequest, headers);
        ResponseEntity<FinancialEntryResponse> financialResponse = restTemplate
                .postForEntity("/api/farms/" + farmId + "/entries", financialEntity, FinancialEntryResponse.class);

        Long entryId = financialResponse.getBody().getId();

        HttpEntity<Void> entity2 = new HttpEntity<>(headers);

        ResponseEntity<Void> finalResponse = restTemplate.exchange("/api/farms/" + farmId + "/entries/" + entryId,
                HttpMethod.DELETE, entity2, Void.class);

        assertThat(finalResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        
    }

    @Test
    public void deleteEntry_returns404_whenEntryDoesNotExist() {
        String ownerToken = registerAndLogin();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(ownerToken);

        FarmRequest farmRequest = new FarmRequest("Farmica", "Grocka", BigDecimal.valueOf(3));
        HttpEntity<FarmRequest> entity = new HttpEntity<>(farmRequest, headers);
        ResponseEntity<FarmResponse> farmResponse = restTemplate.postForEntity("/api/farms", entity, FarmResponse.class);

        Long farmId = farmResponse.getBody().getId();

        HttpEntity<Void> entity3 = new HttpEntity<>(headers);
        ResponseEntity<Void> finalResponse = restTemplate.exchange("/api/farms/" + farmId + "/entries/" + Long.MAX_VALUE, HttpMethod.DELETE, entity3, Void.class);

        assertThat(finalResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
