package ru.firesin.weather.service;

/**
 * Author:    firesin
 * Date:      27.10.2023
 */
public interface WeatherService {

    String getWeather(String city) throws RuntimeException;
}
