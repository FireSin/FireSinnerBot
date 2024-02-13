package ru.firesin.gigachat.model;

import lombok.Data;

/**
 * Author:    Artur Kubantsev
 * Date:      04.02.2024
 */
@Data
public class Usage {
    private int promptTokens;
    private int completionTokens;
    private int totalTokens;
    private int systemTokens;
}
