package com.drug.notifier.contoller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
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
