package ru.firesin.gigachat.model;

import lombok.Data;

import java.util.ArrayList;

/**
 * Author:    Artur Kubantsev
 * Date:      04.02.2024
 */

@Data
public class GigaChatResponse {
    private ArrayList<Choice> choices;
    private long created;
    private String model;
    private Usage usage;
}
