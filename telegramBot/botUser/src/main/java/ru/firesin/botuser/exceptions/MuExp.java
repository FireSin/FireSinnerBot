package ru.firesin.botuser.exceptions;

import lombok.Getter;

/**
 * Author:    firesin
 * Date:      26.10.2023
 */

@Getter
public class MuExp extends RuntimeException {

    private final Long id;
    private final String txt;

    public MuExp(Long id, String message) {
        super();
        this.id = id;
        this.txt = message;
    }
}
