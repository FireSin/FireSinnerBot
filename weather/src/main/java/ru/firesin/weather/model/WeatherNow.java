package ru.firesin.weather.model;

import com.github.bfsmith.geotimezone.TimeZoneLookup;
import lombok.Data;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Author:    firesin
 * Date:      28.10.2023
 */

@Data
public class WeatherNow {
    private List<Weather> weather;
    private Coordinates coord;
    private Main main;
    private Wind wind;
    private Sys sys;
    private String name;

    private String formatTime(long timestamp) {
        String timeZoneId = new TimeZoneLookup()
                .getTimeZone(coord.getLat(), coord.getLon())
                .getResult();

        ZoneId cityTimeZone = ZoneId.of(timeZoneId);

        ZonedDateTime citySunriseDateTime = Instant
                .ofEpochSecond(timestamp)
                .atZone(cityTimeZone);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm O");
        return citySunriseDateTime.format(formatter);
    }

    @Override
    public String toString() {
        return "Погода в городе " + name +
                "\n\nОблачность: " + weather.get(0).getDescription() +
                "\nТемпература: " + main.getTemp() + "°C " + getTemperatureEmoji(main.getTemp()) +
                "\nОщущается как: " + main.getFeelsLike() + "°C" +
                "\nСкорость ветра: " + wind.getSpeed() + "м/с 🌬️" +
                "\nДавление: " + main.getPressure() + "мм рт.ст." +
                "\nВлажность: " + main.getHumidity() + "%" +
                "\n🌅 Восход: " + formatTime(sys.getSunrise()) +
                "\n🌇 Закат: " + formatTime(sys.getSunset());
    }

    private String getTemperatureEmoji(double temperature) {
        if (temperature < -10) {
            return "❄️";
        } else if (temperature < 0) {
            return "🥶";
        } else if (temperature < 10) {
            return "😬";
        } else if (temperature < 20) {
            return "😊";
        } else {
            return "☀️";
        }
    }
}
