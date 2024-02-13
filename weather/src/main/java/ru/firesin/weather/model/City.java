package ru.firesin.weather.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Author:    firesin
 * Date:      28.10.2023
 */

@Getter
@Setter
public class City {
    private String name;
    private double lat;
    private double lon;
}
