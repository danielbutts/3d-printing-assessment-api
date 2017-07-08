package com.github.danielbutts.partsanalyzer.repository;

import com.github.danielbutts.partsanalyzer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by danielbutts on 7/5/17.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserById(Long id);

    User findUserByUsername(String username);

}

