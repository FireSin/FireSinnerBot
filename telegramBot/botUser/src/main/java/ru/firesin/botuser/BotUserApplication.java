package ru.firesin.botuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Author:    firesin
 * Date:      26.10.2023
 */

@SpringBootApplication(scanBasePackages = "ru.firesin")
@EnableJpaRepositories(basePackages = "ru.firesin.users")
@EntityScan(basePackages = "ru.firesin.users")
public class BotUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(BotUserApplication.class, args);
    }

}
