package com.github.danielbutts.partsanalyzer.material;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by danielbutts on 7/8/17.
 */

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {

    public Material findById(Long id);
}
