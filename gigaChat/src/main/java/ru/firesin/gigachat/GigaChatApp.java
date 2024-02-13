package ru.firesin.gigachat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */

@SpringBootApplication(scanBasePackages = "ru.firesin")
public class GigaChatApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(GigaChatApp.class, args);
    }
}
