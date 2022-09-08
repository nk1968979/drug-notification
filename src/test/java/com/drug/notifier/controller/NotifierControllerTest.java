package com.drug.notifier.controller;

import com.drug.notifier.model.Patient;
import com.drug.notifier.repositories.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.context.SecurityContextHolder;

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
        String email="abc@gmail.com";
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

}
