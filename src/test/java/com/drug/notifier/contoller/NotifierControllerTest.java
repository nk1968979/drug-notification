//package com.drug.notifier.contoller;
//
//import com.drug.notifier.model.City;
//import com.drug.notifier.model.WeatherResponse;
//import com.drug.notifier.repositories.CityRepository;
//import com.drug.notifier.services.WeatherService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(MockitoExtension.class)
//public class NotifierControllerTest {
//    private MockMvc mockMvc;
//
//    @InjectMocks
//    private NotifierController weatherController;
//
//    @Mock
//    private WeatherService weatherService;
//
//    @Mock
//    private CityRepository cityRepository;
//    private String city;
//
//    @BeforeEach // For Junit5
//    public void setup() {
//        this.mockMvc = MockMvcBuilders.standaloneSetup(weatherController).build();
//        this.city="dummy";
//
//    }
//
//    @Test
//    void getWeatherResponsePositive() throws Exception {
//        WeatherResponse weatherResponse=new WeatherResponse();
//        City cityObj=mock(City.class);
//        when(weatherService.getAverageWeatherData(city)).thenReturn(weatherResponse);
//        when(cityRepository.findByName(city)).thenReturn(cityObj);
//        mockMvc.perform( MockMvcRequestBuilders
//                        .get("/data?city="+city)
//                        .accept(MediaType.APPLICATION_JSON))
//                        .andDo(print())
//                        .andExpect(status().isOk());
//    }
//
//    @Test
//    void getWeatherResponseCityRequiredException() throws Exception {
//        mockMvc.perform( MockMvcRequestBuilders
//                        .get("/data")
//                        .accept(MediaType.APPLICATION_JSON))
//                        .andDo(print())
//                        .andExpect(status().isBadRequest());//Bad Request
//    }
//
//}
