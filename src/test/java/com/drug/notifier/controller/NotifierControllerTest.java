package com.drug.notifier.controller;

import com.drug.notifier.model.Patient;
import com.drug.notifier.model.Physician;
import com.drug.notifier.repositories.PatientRepository;
import com.drug.notifier.repositories.PhysicianRepository;
import com.drug.notifier.repositories.PrescriptionRepository;
import com.drug.notifier.services.impl.JwtUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class NotifierControllerTest {
    @Autowired
    protected WebApplicationContext context;

    @MockBean
    private PatientRepository patientRepository;

    @MockBean
    private PhysicianRepository physicianRepository;

    @MockBean
    private PrescriptionRepository prescriptionRepository;

    @MockBean
    private JwtUserDetailsService userDetailsService;

    protected MockMvc mockMvc;

    protected org.springframework.security.core.userdetails.User loggedUser;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        loggedUser =  (User)  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Test
    @WithMockUser(username = "admin", password = "123456", authorities = "physician")
    void getRoleWithUserPhysicianTest() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                        .get("/getRole").with(user(loggedUser))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role",is("physician")));
    }

    @Test
    @WithMockUser(username = "admin", password = "123456", authorities = "patient")
    void getRoleWithUserPatientTest() throws Exception {
        Patient patient=new Patient();
        patient.setId(123L);
        Mockito.when(patientRepository.findByEmail("admin")).thenReturn(patient);
        mockMvc.perform( MockMvcRequestBuilders
                        .get("/getRole").with(user(loggedUser))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(123)))
                .andExpect(jsonPath("$.role",is("patient")));
    }

    @Test
    @WithMockUser(username = "admin", password = "123456", authorities = "physician")
    void getAllPatients() throws Exception {
        List<Patient> patients=new ArrayList<>();
        patients.add(new Patient());
        Mockito.when(patientRepository.findAll()).thenReturn(patients);
        Physician physician=new Physician();
        physician.setFullName("Naveen");
        Mockito.when(physicianRepository.findByEmail("admin")).thenReturn(physician);
        mockMvc.perform( MockMvcRequestBuilders
                        .get("/allPatients").with(user(loggedUser))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username",is("Naveen")));
    }

    @Test
    @WithMockUser(username = "admin", password = "123456", authorities = "physician")
    void getAllPrescriptionsAsPhysician() throws Exception {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("physician"));
        Mockito.when(userDetailsService.loadUserByUsername("admin")).thenReturn( new User("admin", "123456", authorities) );
        Physician physician=new Physician();
        physician.setFullName("Naveen");
        Mockito.when(physicianRepository.findByEmail("admin")).thenReturn(physician);
        mockMvc.perform( MockMvcRequestBuilders
                        .get("/getPrescriptions?id=123").with(user(loggedUser))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", password = "123456", authorities = "patient")
    void getAllPrescriptionsAsPatient() throws Exception {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("patient"));
        Mockito.when(userDetailsService.loadUserByUsername("admin")).thenReturn( new User("admin", "123456", authorities) );
        Patient patient=new Patient();
        patient.setFullName("Naveen");
        Mockito.when(patientRepository.findByEmail("admin")).thenReturn(patient);
        mockMvc.perform( MockMvcRequestBuilders
                        .get("/getPrescriptions?id=123").with(user(loggedUser))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", password = "123456", authorities = "patient")
    void deletePrescription() throws Exception {
        Mockito.doNothing().when(prescriptionRepository).deleteById(12323456L);
        mockMvc.perform( MockMvcRequestBuilders
                        .post("/delete-prescription?id=12323456").with(user(loggedUser))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }


}
