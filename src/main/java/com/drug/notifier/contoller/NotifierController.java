package com.drug.notifier.contoller;

import com.drug.notifier.model.Physician;
import com.drug.notifier.repositories.PatientRepository;
import com.drug.notifier.repositories.PhysicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class NotifierController {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PhysicianRepository physicianRepository;
    @GetMapping("/getRole")
    public ResponseEntity getRole(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(user.getAuthorities());
    }
    @GetMapping(value = "/signin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getLoginPage() {
       return new ModelAndView("webapp/auth/landing/index.html");
    }
    @GetMapping(value = "/allPatients", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getHomePage(@AuthenticationPrincipal User user) {
        Map response=new HashMap();
        Physician physician=physicianRepository.findByEmail(user.getUsername());
        response.put("username",physician.getFullName());
        response.put("patients",patientRepository.findAll());
        return ResponseEntity.ok(response);
    }
}
