package ru.firesin.gigachat.service.impl;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ru.firesin.gigachat.model.TokenResponse;
import ru.firesin.gigachat.service.TokenService;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Author:    Artur Kubantsev
 * Date:      03.02.2024
 */

@Service
@Slf4j
public class TokenServiceImpl implements TokenService {
    @Getter
    private final String url;
    @Getter
    private final String key;
    private Instant expirationTime;
    private TokenResponse tokenResponse;

    public TokenServiceImpl(@Value("${Token.url}") String url,
                            @Value("${Token.key}") String key) {
        this.url = url;
        this.key = key;
        this.expirationTime = Instant.now();
    }

    @Override
    public String checkToken() {
        if (Instant.now().isAfter(expirationTime)) {
            updateToken();
        }
        return tokenResponse.getAccess_token();
    }

    private void updateToken() {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("scope", "GIGACHAT_API_PERS");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set("RqUID", UUID.randomUUID().toString());
        headers.set("Authorization", "Basic " + key);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        tokenResponse = restTemplate.postForObject(url, requestEntity, TokenResponse.class);
        this.expirationTime = Instant.ofEpochMilli(tokenResponse.getExpires_at());
    }
}
