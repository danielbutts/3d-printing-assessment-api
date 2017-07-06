package com.github.danielbutts.partsanalyzer;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by danielbutts on 7/5/17.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserById(Long id);

}

