package com.github.danielbutts.partsanalyzer.repository;

import com.github.danielbutts.partsanalyzer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by danielbutts on 7/5/17.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findById(Long id);

    User findByUsername(String username);

    @Query(value = "insert into users_parts (part_id, user_id) values (?1, ?2) returning part_id", nativeQuery = true)
    public Long addPartToUser(Long partId, Long userId);

    @Query(value = "delete from users_parts where part_id = ?1 returning part_id", nativeQuery = true)
    public Long removePartFromUser(Long partId);


}

