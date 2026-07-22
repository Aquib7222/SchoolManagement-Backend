package com.schoolmanagement.schoolmanagementwebsite.service;

import com.schoolmanagement.schoolmanagementwebsite.entity.User;
import com.schoolmanagement.schoolmanagementwebsite.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Override
public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = repo.findByEmail(email);
    if (user == null) throw new UsernameNotFoundException("User not found");

    return org.springframework.security.core.userdetails.User
            .withUsername(user.getEmail())
            .password(user.getPassword())
            .roles(user.getRole().toUpperCase())   // FIXED
            .build();
}

}
