package com.drug.notifier.contoller;

import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class NotifierController {
    @GetMapping(value = "/signin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getLoginPage() {
       return new ModelAndView("webapp/auth/landing/index.html");
    }
    @GetMapping(value = "/home", produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getHomePage(@AuthenticationPrincipal User user) {
        return new ModelAndView("index.html");
    }
}
