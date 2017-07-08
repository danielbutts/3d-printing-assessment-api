package com.github.danielbutts.partsanalyzer.controller;

import com.github.danielbutts.partsanalyzer.model.User;
import com.github.danielbutts.partsanalyzer.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Created by danielbutts on 7/5/17.
 */

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public Iterable<User> all() {
        return this.repository.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        Long userId =  Long.parseLong(id);
        return this.repository.findUserById(userId);
    }

    @PostMapping("")
    public User create(@RequestBody User user) throws Exception {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        System.out.println(hashedPassword);
        user.setPassword(hashedPassword);
        return this.repository.save(user);
    }

    @PatchMapping("")
    public User update(@RequestBody User user) {
        User existingUser = this.repository.findUserById(user.getId());

        if (user.getCompany() != null) {
            existingUser.setCompany(user.getCompany());
        }
        if (user.getEmail() != null) {
            existingUser.setEmail(user.getEmail());
        }
        if (user.getFirstName() != null) {
            existingUser.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            existingUser.setLastName(user.getLastName());
        }
        if (user.getCompany() != null) {
            existingUser.setCompany(user.getCompany());
        }

        return this.repository.save(existingUser);
    }
}
