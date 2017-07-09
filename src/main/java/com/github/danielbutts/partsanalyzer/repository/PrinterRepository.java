package com.github.danielbutts.partsanalyzer.repository;

import com.github.danielbutts.partsanalyzer.model.Printer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by danielbutts on 7/8/17.
 */

@Repository
public interface PrinterRepository extends JpaRepository<Printer, Long> {

    public Printer findById(Long id);

    @Query(value = "insert into printers_materials (material_id, printer_id) values (?1, ?2) returning printer_id", nativeQuery = true)
    public Long addMaterialToPrinter(Long materialId, Long printerId);
}
