package ru.firesin.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ru.firesin")
public class WeatherApp
{
    public static void main(String[] args) {
        SpringApplication.run(WeatherApp.class, args);
    }
}
