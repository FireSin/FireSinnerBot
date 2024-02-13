package ru.firesin.weather.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.firesin.weather.model.WeatherNow;
import ru.firesin.weather.service.WeatherService;

/**
 * Author:    firesin
 * Date:      27.10.2023
 */

@Service
@Slf4j
public class OpenWeatherMapService implements WeatherService {

    private final RestTemplate restTemplate;
    private final String apiUrlWeather;
    private final String apiKey;

    public OpenWeatherMapService(@Value("${OpenWeatherMap.urlGetWeather}") String apiUrlWeather,
                                 @Value("${OpenWeatherMap.key}") String apiKey) {
        this.restTemplate = new RestTemplate();
        this.apiUrlWeather = apiUrlWeather;
        this.apiKey = apiKey;
    }

    @Override
    public String getWeather(String city) {
        try {
            return weatherData(city).toString();
        } catch (HttpStatusCodeException e) {
            if (e.getResponseBodyAsString().contains("city not found")) {
                return "404";
            } else {
                log.error("Сервис погоды: " + e.getMessage());
                return "502";
            }
        }
    }

    private WeatherNow weatherData(String city) {
        String url = apiUrlWeather.replace("{city}", city)
                .replace("{API key}", apiKey);

        return restTemplate.getForObject(url, WeatherNow.class);
    }
}
