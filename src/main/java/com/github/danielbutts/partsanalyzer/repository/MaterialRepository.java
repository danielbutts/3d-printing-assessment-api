package com.github.danielbutts.partsanalyzer.repository;

import com.github.danielbutts.partsanalyzer.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by danielbutts on 7/8/17.
 */

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {

    public Material findById(Long id);

    public List<Material> findByType(String type);

}
