package com.github.danielbutts.partsanalyzer.repository;

import com.github.danielbutts.partsanalyzer.model.Printer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by danielbutts on 7/8/17.
 */

@Repository
public interface PrinterRepository extends JpaRepository<Printer, Long> {

    public Printer findById(Long id);
}
