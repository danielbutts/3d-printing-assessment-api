package com.github.danielbutts.partsanalyzer.controller;

import com.github.danielbutts.partsanalyzer.model.Bureau;
import com.github.danielbutts.partsanalyzer.repository.BureauRepository;
import org.springframework.web.bind.annotation.*;

/**
 * Created by danielbutts on 7/8/17.
 */

@RestController
@RequestMapping("/bureaus")
public class BureauController {

    private final BureauRepository repository;

    public BureauController(BureauRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public Iterable<Bureau> all() {
        return this.repository.findAll();
    }

    @GetMapping("/{id}")
    public Bureau getBureauById(@PathVariable String id) {
        Long bureauId =  Long.parseLong(id);
        return this.repository.findById(bureauId);
    }

    @PostMapping("")
    public Bureau create(@RequestBody Bureau bureau) throws Exception {
         return this.repository.save(bureau);
    }

    @PatchMapping("")
    public Bureau update(@RequestBody Bureau bureau) {
        Bureau existingBureau = this.repository.findById(bureau.getId());

        if (bureau.getName() != null) {
            existingBureau.setName(bureau.getName());
        }
        if (bureau.getZipCode() != null) {
            existingBureau.setZipCode(bureau.getZipCode());
        }
        if (bureau.getMargin() != null) {
            existingBureau.setMargin(bureau.getMargin());
        }
        if (bureau.getMaxOrder() != null) {
            existingBureau.setMaxOrder(bureau.getMaxOrder());
        }
        if (bureau.getMinOrder() != null) {
            existingBureau.setMinOrder(bureau.getMinOrder());
        }
        if (bureau.getDiscountFactor() != null) {
            existingBureau.setDiscountFactor(bureau.getDiscountFactor());
        }

        return this.repository.save(existingBureau);
    }


}
