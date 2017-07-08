package com.github.danielbutts.partsanalyzer.repository;

import com.github.danielbutts.partsanalyzer.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by danielbutts on 7/7/17.
 */

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    @Query("select a.role from UserRole a, User b where b.username=?1 and b.id = a.userId")
    public List<String> findRoleByUserName(String username);
}
