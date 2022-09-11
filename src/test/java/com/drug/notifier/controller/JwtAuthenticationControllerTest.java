package com.drug.notifier.controller;

import com.drug.notifier.model.Physician;
import com.drug.notifier.repositories.PhysicianRepository;
import com.drug.notifier.services.impl.JwtUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class JwtAuthenticationControllerTest {
    @Autowired
    protected WebApplicationContext context;

    @MockBean
    protected AuthenticationManager authenticationManager;

    @MockBean
    protected Authentication authentication;

    @MockBean
    private JwtUserDetailsService userDetailsService;

    @MockBean
    private PhysicianRepository physicianRepository;


    protected MockMvc mockMvc;


    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void loginTest() throws Exception {
        Mockito.when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("admin","admin"))).thenReturn(authentication);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("physician"));
        Mockito.when(userDetailsService.loadUserByUsername("admin")).thenReturn( new User("admin", "123456", authorities) );
        mockMvc.perform( MockMvcRequestBuilders
                        .post("/login")
                        .content("{\"username\":\"admin\",\"password\":\"admin\",\"role\":\"physician\"}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    void registerTest() throws Exception {
        Mockito.when(physicianRepository.save(new Physician())).thenReturn(new Physician());
        mockMvc.perform( MockMvcRequestBuilders
                        .post("/register-physician")
                        .content("{\"fullName\":\"Naveen Kumar\",\"email\":\"abc@gmail.com\",\"phoneNum\":\"987654321\",\"pass\":\"password\",\"role\":\"physician\"}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }
}
