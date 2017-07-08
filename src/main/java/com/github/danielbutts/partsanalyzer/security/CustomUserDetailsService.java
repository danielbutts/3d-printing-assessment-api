package com.github.danielbutts.partsanalyzer.security;

import com.github.danielbutts.partsanalyzer.user.User;
import com.github.danielbutts.partsanalyzer.user.UserRepository;
import com.github.danielbutts.partsanalyzer.user.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        System.out.println("CustomUserDetailsService");
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername "+username);
        User user=userRepository.findUserByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("No user with username: " + username);
        } else {
            System.out.println("password: "+user.getPassword());
            List<String> userRoles = userRoleRepository.findRoleByUserName(username);
            return new CustomUserDetails(user,userRoles);
        }
    }
}