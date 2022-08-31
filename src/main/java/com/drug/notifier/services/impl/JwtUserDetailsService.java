package com.drug.notifier.services.impl;

import java.util.ArrayList;
import java.util.List;

import com.drug.notifier.model.Patient;
import com.drug.notifier.model.Physician;
import com.drug.notifier.repositories.PatientRepository;
import com.drug.notifier.repositories.PhysicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private PhysicianRepository physicianRepository;

	@Autowired
	private PatientRepository patientRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Physician userEntity=physicianRepository.findByEmail(username);
		Patient patient=patientRepository.findByEmail(username);
		List<GrantedAuthority> authorities = new ArrayList<>();
		if (userEntity!=null) {
			authorities.add(new SimpleGrantedAuthority(userEntity.getRole()));
			return new User(userEntity.getEmail(), userEntity.getPass(), authorities);
		}else if(patient!=null){
			authorities.add(new SimpleGrantedAuthority(patient.getRole()));
			return new User(patient.getEmail(), patient.getPass(), authorities);
		}else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}
}