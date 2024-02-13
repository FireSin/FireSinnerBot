package ru.firesin.gigachat.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.firesin.gigachat.model.GigaChatRequest;
import ru.firesin.gigachat.model.GigaChatResponse;
import ru.firesin.gigachat.model.Message;
import ru.firesin.gigachat.service.GigaChatService;
import ru.firesin.gigachat.service.TokenService;

import java.util.Collections;

/**
 * Author:    Artur Kubantsev
 * Date:      03.02.2024
 */

@Service
@Slf4j
public class GigaChatServiceImpl implements GigaChatService {
    private final TokenService tokenService;

    public GigaChatServiceImpl(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public String getAnswer(String question) {
        try {
            tokenService.checkToken();
            return requestChat(question);
        } catch (Exception e) {
            log.debug(e.getMessage());
            System.out.println(e.getMessage());
            return "Сервис чата временно недоступен. Сообщите об этом @SinnerFire";
        }
    }

    private String requestChat(String question) {
        RestTemplate restTemplate = new RestTemplate();

        GigaChatRequest gigaChatRequest = getGigaChatRequest(question);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(tokenService.checkToken());

        HttpEntity<GigaChatRequest> requestEntity = new HttpEntity<>(gigaChatRequest, headers);

        GigaChatResponse gigaChatResponse = restTemplate.postForObject("https://gigachat.devices.sberbank.ru/api/v1/chat/completions", requestEntity, GigaChatResponse.class);

        return gigaChatResponse.getChoices().get(0).getMessage().getContent();
    }

    private static GigaChatRequest getGigaChatRequest(String question) {
        GigaChatRequest gigaChatRequest = new GigaChatRequest();
        gigaChatRequest.setModel("GigaChat");

        Message message = new Message();
        message.setRole("user");
        message.setContent(question);
        gigaChatRequest.setMessages(Collections.singletonList(message));
        gigaChatRequest.setTemperature(1);
        gigaChatRequest.setTopP(0.1);
        gigaChatRequest.setN(1);
        gigaChatRequest.setStream(false);
        gigaChatRequest.setMaxTokens(512);
        gigaChatRequest.setRepetitionPenalty(1);
        gigaChatRequest.setUpdateInterval(0);
        return gigaChatRequest;
    }
}
