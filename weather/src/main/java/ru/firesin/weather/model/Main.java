package ru.firesin.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Author:    firesin
 * Date:      28.10.2023
 */

@Data
public class Main {
    private Integer temp;

    @JsonProperty("feels_like")
    private Integer feelsLike;
    private Integer pressure;
    private Integer humidity;


}
