package com.drug.notifier.service;

import com.drug.notifier.model.Patient;
import com.drug.notifier.model.Physician;
import com.drug.notifier.repositories.PatientRepository;
import com.drug.notifier.repositories.PhysicianRepository;
import com.drug.notifier.services.impl.JwtUserDetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

@ExtendWith(MockitoExtension.class)
public class JwtUserDetailsServiceTest {
    @Mock
    private PhysicianRepository physicianRepository;
    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private JwtUserDetailsService userDetailsService;

    private String username="admin";
    @Test
    void loadUserByUsernameAsPhysicianTest(){
        Physician physician=new Physician();
        physician.setEmail(username);
        physician.setPass("abc");
        physician.setRole("role");
        Mockito.when(physicianRepository.findByEmail(username)).thenReturn(physician);
        UserDetails userDetails=userDetailsService.loadUserByUsername(username);
        Assertions.assertEquals(userDetails.getUsername(),physician.getEmail());

    }

    @Test
    void loadUserByUsernameAsPatientTest(){
        Patient patient=new Patient();
        patient.setEmail(username);
        patient.setPass("abc");
        patient.setRole("role");
        Mockito.when(patientRepository.findByEmail(username)).thenReturn(patient);
        UserDetails userDetails=userDetailsService.loadUserByUsername(username);
        Assertions.assertEquals(userDetails.getUsername(),patient.getEmail());

    }
}
