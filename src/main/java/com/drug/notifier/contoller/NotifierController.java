package com.drug.notifier.contoller;

import com.drug.notifier.model.Patient;
import com.drug.notifier.model.Physician;
import com.drug.notifier.model.Prescription;
import com.drug.notifier.repositories.PatientRepository;
import com.drug.notifier.repositories.PhysicianRepository;
import com.drug.notifier.repositories.PrescriptionRepository;
import com.drug.notifier.services.impl.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class NotifierController {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private PhysicianRepository physicianRepository;

    @GetMapping("/getRole")
    public ResponseEntity getRole(@AuthenticationPrincipal User user){
        Map response=new HashMap();
        if(user.getAuthorities().contains(new SimpleGrantedAuthority("patient"))) {
            Patient patient = patientRepository.findByEmail(user.getUsername());
            response.put("id", patient.getId());
            response.put("role","patient");
        }else {
            response.put("role","physician");
        }
        return ResponseEntity.ok(response);
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

    @GetMapping(value = "/getPrescriptions")
    public ResponseEntity getPrescriptions(@AuthenticationPrincipal User user, @RequestParam("id") int id) {
        Map response = new HashMap();
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(user.getUsername());
        if(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("patient"))){
            Patient patient=patientRepository.findByEmail(user.getUsername());
            response.put("username",patient.getFullName());
            response.put("role","patient");
            response.put("patientName",patient.getFullName());
        }else {
            Physician physician=physicianRepository.findByEmail(user.getUsername());
            response.put("username",physician.getFullName());
            response.put("role","physician");
            Optional<Patient> patient=patientRepository.findById(Long.valueOf(id));
            if(patient.isPresent()){
                response.put("patientName",patient.get().getFullName());
            }
        }

        response.put("prescriptions",prescriptionRepository.findByPatientId(id));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/add-edit-prescription")
    public ResponseEntity addEditPrescription(@RequestBody Prescription prescription, @AuthenticationPrincipal User user){
        System.out.println(user.getUsername());
        Physician physician=physicianRepository.findByEmail(user.getUsername());
        prescription.setPrescribedBy(physician.getFullName());
        prescription.setPrescriptionDate(new Timestamp(System.currentTimeMillis()));
        Prescription prescription1=prescriptionRepository.save(prescription);
        return ResponseEntity.ok(prescription1);
    }

    @PostMapping("/delete-prescription")
    public ResponseEntity delete(@RequestParam("id") Long id){
        prescriptionRepository.deleteById(id);
        return ResponseEntity.ok("Deleted Successfully");
    }

}
