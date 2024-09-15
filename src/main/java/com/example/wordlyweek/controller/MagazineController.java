package com.example.wordlyweek.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.wordlyweek.model.*;
import com.example.wordlyweek.service.MagazineJpaService;

;

/**
 * MagazineController
 */
@RestController
public class MagazineController {

    @Autowired
    private MagazineJpaService magazineJpaService;

    @GetMapping("/magazines")
    public List<Magazine> getMagazines() {
        return magazineJpaService.getMagazines();
    }

    @GetMapping("/magazines/{magazineId}")
    public Magazine getMagazine(@PathVariable int magazineId) {
        return magazineJpaService.getMagazineById(magazineId);
    }

    @PostMapping("/magazines")
    public Magazine addMagazine(@RequestBody Magazine magazine) {
        return magazineJpaService.addMagazine(magazine);
    }

    @DeleteMapping("/magazines/{magazineId}")
    public void deleteMagazine(@PathVariable int magazineId) {
        magazineJpaService.deleteMagazine(magazineId);
    }

    @PutMapping("/magazines/{id}")
    public Magazine updateMagazine(@PathVariable int id, @RequestBody Magazine magazine) {
        return magazineJpaService.updateMagazine(id, magazine);
    }

    @GetMapping("/magazines/{magazineId}/writers")
    public List<Writer> getMagazineWriters(@PathVariable int magazineId) {
        return magazineJpaService.getMagazineWriters(magazineId);
    }

}