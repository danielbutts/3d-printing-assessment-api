package com.github.danielbutts.partsanalyzer.repository;

import com.github.danielbutts.partsanalyzer.model.Bureau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by danielbutts on 7/8/17.
 */

@Repository
public interface BureauRepository extends JpaRepository<Bureau, Long> {

    public Bureau findById(Long id);

    @Query(value = "insert into bureaus_printers (bureau_id, printer_id) values (?1, ?2) returning bureau_id", nativeQuery = true)
    public Long addPrinterToBureau(Long bureauId, Long printerId);

    @Query(value = "delete from bureaus_printers where bureau_id = ?1 and printer_id = ?2 returning bureau_id", nativeQuery = true)
    public Long removePrinterFromBureau(Long bureauId, Long printerId);
}
