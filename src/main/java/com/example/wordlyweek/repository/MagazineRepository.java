package com.example.wordlyweek.repository;

import java.util.List;

import com.example.wordlyweek.model.Magazine;
import com.example.wordlyweek.model.Writer;

/**
 * MagazineRepository
 */
public interface MagazineRepository {

    List<Magazine> getMagazines();
    Magazine addMagazine(Magazine magazine);
    Magazine getMagazineById(int magazineId);
    Magazine updateMagazine(int magazineId, Magazine magazine);
    void deleteMagazine(int magazineId);
    List<Writer> getMagazineWriters(int magazineId);
}