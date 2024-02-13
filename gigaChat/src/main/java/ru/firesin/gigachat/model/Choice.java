package ru.firesin.gigachat.model;

import lombok.Data;

/**
 * Author:    Artur Kubantsev
 * Date:      04.02.2024
 */
@Data
public class Choice {
    private Message message;
    private int index;
    private String finishReason;
}
