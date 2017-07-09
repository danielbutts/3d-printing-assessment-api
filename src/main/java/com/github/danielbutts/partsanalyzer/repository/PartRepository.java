package com.github.danielbutts.partsanalyzer.repository;

import com.github.danielbutts.partsanalyzer.model.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by danielbutts on 7/8/17.
 */

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {

    public Part findById(Long id);

    @Query(value = "insert into parts_materials (part_id, material_id) values (?1, ?2) returning part_id", nativeQuery = true)
    public Long addMaterialToPart(Long partId, Long materialId);
}
