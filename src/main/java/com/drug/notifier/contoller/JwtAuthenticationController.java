package com.drug.notifier.contoller;

import com.drug.notifier.configuration.JwtTokenUtil;
import com.drug.notifier.model.JwtRequest;
import com.drug.notifier.model.JwtResponse;
import com.drug.notifier.model.Patient;
import com.drug.notifier.model.Physician;
import com.drug.notifier.repositories.PatientRepository;
import com.drug.notifier.repositories.PhysicianRepository;
import com.drug.notifier.services.impl.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private PhysicianRepository usersRepository;
    @Autowired
    private PatientRepository patientRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        Long id=null;
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        if(!userDetails.getAuthorities().contains(new SimpleGrantedAuthority(authenticationRequest.getRole()))){
            throw new Exception("User Not found");
        }

        final String token = jwtTokenUtil.generateToken(userDetails);
        if(authenticationRequest.getRole().equalsIgnoreCase("patient")){
            id=patientRepository.findByEmail(authenticationRequest.getUsername()).getId();
            System.out.println("I am here");
        }
        return ResponseEntity.ok(new JwtResponse(token, id==null?"":id.toString()));
    }

    @PostMapping("/register-physician")
    public ResponseEntity registerUser(@RequestBody Physician userEntity) throws Exception {
        //Encoding Before saving to DB
        try {
            userEntity.setPass(passwordEncoder.encode(userEntity.getPass()));
            usersRepository.save(userEntity);
            return ResponseEntity.ok("Physician Registered Successfully");
        } catch (Exception exception) {
            throw new Exception("User Creation Failed" + exception.getMessage());
        }
    }

    @PostMapping("/register-patient")
    public ResponseEntity registerPatient(@RequestBody Patient userEntity) throws Exception {
        //Encoding Before saving to DB
        try {
            userEntity.setPass(passwordEncoder.encode(userEntity.getPass()));
            patientRepository.save(userEntity);
            return ResponseEntity.ok("Patient Registered Successfully");
        } catch (Exception exception) {
            throw new Exception("User Creation Failed" + exception.getMessage());
        }

    }


    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("User is Disabled", e);
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid Username or Password", e);
        }
    }
}