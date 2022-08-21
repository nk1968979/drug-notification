package com.drug.notifier.services.impl;

import java.util.ArrayList;
import java.util.List;

import com.drug.notifier.model.UserEntity;
import com.drug.notifier.repositories.UsersRepository;
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
	private UsersRepository usersRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity=usersRepository.findByEmail(username);
		if (userEntity!=null) {
			List<GrantedAuthority> authorities=new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority(userEntity.getRole()));
			return new User(userEntity.getEmail(),userEntity.getPass(), authorities);
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}
}