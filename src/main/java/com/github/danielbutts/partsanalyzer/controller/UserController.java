package com.github.danielbutts.partsanalyzer.controller;

import com.github.danielbutts.partsanalyzer.model.User;
import com.github.danielbutts.partsanalyzer.repository.PartRepository;
import com.github.danielbutts.partsanalyzer.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danielbutts on 7/5/17.
 */

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

    private final UserRepository repository;
    private final PartRepository partRepository;

    public UserController(UserRepository repository, PartRepository partRepository) {
        this.repository = repository;
        this.partRepository = partRepository;
    }

    @GetMapping("")
    public Iterable<User> all() {
        return this.repository.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        Long userId =  Long.parseLong(id);
        return this.repository.findById(userId);
    }

    @PostMapping("")
    public User create(@RequestBody User user) throws Exception {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return this.repository.save(user);
    }

    @PostMapping("/batch")
    public ArrayList<User> createMultiple(@RequestBody List<User> users) throws Exception {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        ArrayList<User> createdUsers = new ArrayList<User>();
        for (User user : users) {
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPassword);
            createdUsers.add(this.repository.save(user));
        }
        return createdUsers;
    }

    @PostMapping("/{id}/parts")
    public User addPartsToUser(@PathVariable String id, @RequestBody List<Long> partIds) {
        Long userId =  Long.parseLong(id);
        for (Long partId : partIds) {
            this.repository.addPartToUser(partId, userId);
        }
        return this.repository.findById(userId);
    }

    @DeleteMapping("/{uid}/parts/{pid}")
    public User addPartsToUser(@PathVariable String uid, @PathVariable String pid, @RequestBody List<Long> partIds) {
        Long userId =  Long.parseLong(uid);
        Long partId =  Long.parseLong(pid);
        this.repository.removePartFromUser(partId);
        return this.repository.findById(userId);
    }

    @PatchMapping("")
    public User update(@RequestBody User user) {
        User existingUser = this.repository.findById(user.getId());

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
        if (user.getParts() != null) {
            existingUser.setParts(user.getParts());
        }

        return this.repository.save(existingUser);
    }
}
