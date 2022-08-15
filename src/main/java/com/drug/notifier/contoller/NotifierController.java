package com.drug.notifier.contoller;

import com.drug.notifier.repositories.CityRepository;
import com.drug.notifier.services.WeatherService;
import com.drug.notifier.model.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class NotifierController {
    @GetMapping(value = "/signin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getLoginPage() {
        System.out.println("I am Sign Page");
       return new ModelAndView("webapp/auth/signin.html");
    }
    @GetMapping(value = "/home", produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getHomePage() {
        System.out.println("I am Home Page");
        return new ModelAndView("index.html");
    }
}
