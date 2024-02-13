package ru.firesin.gigachat.model;

import lombok.Data;

import java.util.List;

/**
 * Author:    Artur Kubantsev
 * Date:      04.02.2024
 */

@Data
public class GigaChatRequest {
    private String model;
    private List<Message> messages;
    private int temperature;
    private double topP;
    private int n;
    private boolean stream;
    private int maxTokens;
    private int repetitionPenalty;
    private int updateInterval;
}
