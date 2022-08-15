package com.drug.notifier.services;

import com.drug.notifier.model.WeatherResponse;
import org.springframework.stereotype.Service;

@Service
public interface WeatherService {
    WeatherResponse getAverageWeatherData(String city);
}
