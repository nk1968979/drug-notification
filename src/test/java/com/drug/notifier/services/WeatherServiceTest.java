package com.drug.notifier.services;

import com.drug.notifier.model.Weather;
import com.drug.notifier.repositories.WeatherRepository;
import com.drug.notifier.services.impl.WeatherServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {
    @InjectMocks
    private WeatherServiceImpl weatherService;

    @Mock
    private WeatherRepository weatherRepository;


    @Test
    void getAverageWeatherDataPositive(){
        String city="dummy";
        double temp=13.6;
        Weather weather=mock(Weather.class);
        when(weather.getDayTemp()).thenReturn(temp);
        when(weather.getNightTemp()).thenReturn(temp);
        when(weather.getPressure()).thenReturn(temp);
        ArrayList<Weather> weatherList=new ArrayList<>();
        weatherList.add(weather);
        when(weatherRepository.getLast2days(city)).thenReturn(weatherList);
        assertDoesNotThrow(()->weatherService.getAverageWeatherData(city));
        Assertions.assertEquals(weatherService.getAverageWeatherData(city).getAverageDayTemperature(),temp);
    }

    @Test
    void getAverageWeatherDataNegative(){
        String city="dummy";
        when(weatherRepository.getLast2days(city)).thenThrow(NullPointerException.class);
        assertThrows(Exception.class,()->weatherService.getAverageWeatherData(city));
    }

}
