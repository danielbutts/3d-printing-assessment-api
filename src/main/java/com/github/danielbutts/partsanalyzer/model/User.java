package com.github.danielbutts.partsanalyzer.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by danielbutts on 7/5/17.
 */

@Entity
@Table(name = "users")
public class User {

    public User () {
    }

    public User (User user) {
        this.id = id;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.username = user.username;
        this.company = user.company;
        this.password = user.password;
        this.email = user.email;
        this.isAdmin = user.isAdmin;
        this.zipCode = user.zipCode;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String username;

    private String company;

    @Column(unique = true)
    private String email;
    private String password;
    private boolean isAdmin;
    private String zipCode;

    @OneToMany
    @JoinTable(name="users_parts",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "part_id"))
    private List<Part> parts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }
}
