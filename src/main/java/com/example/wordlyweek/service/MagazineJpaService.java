package com.example.wordlyweek.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.wordlyweek.model.Magazine;
import com.example.wordlyweek.model.Writer;
import com.example.wordlyweek.repository.MagazineJpaRepository;
import com.example.wordlyweek.repository.MagazineRepository;
import com.example.wordlyweek.repository.WriterJpaRepository;

/**
 * MagazineJpaService
 */
@Service
public class MagazineJpaService implements MagazineRepository {

    @Autowired
    private MagazineJpaRepository magazineJpaRepository;
    @Autowired
    private WriterJpaRepository writerJpaRepository;

    @Override
    public List<Magazine> getMagazines() {
        List<Magazine> magazines = magazineJpaRepository.findAll();
        return magazines;
    }

    @Override
    public Magazine addMagazine(Magazine magazine) {
        magazine.setMagazineId(0);
        List<Integer> writerIds = new ArrayList<>();
        for (Writer writer : magazine.getWriters()) {
            writerIds.add(writer.getWriterId());
        }
        List<Writer> completeWriters = writerJpaRepository.findAllById(writerIds);
        magazine.setWriters(completeWriters);
        if (writerIds.size() != completeWriters.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some writers are not found");
        }
        magazineJpaRepository.save(magazine);
        return magazine;
    }

    @Override
    public Magazine getMagazineById(int magazineId) {
        try {
            Magazine magazine = magazineJpaRepository.findById(magazineId).get();
            return magazine;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Magazine updateMagazine(int magazineId, Magazine magazine) {
        try {
            Magazine newMagazine = magazineJpaRepository.findById(magazineId).get();
            if (magazine.getMagazineName() != null) {
                newMagazine.setMagazineName(magazine.getMagazineName());
            }
            if (magazine.getPublicationDate() != null) {
                newMagazine.setPublicationDate(magazine.getPublicationDate());
            }
            if (magazine.getWriters() != null) {
                List<Integer> writerIds = new ArrayList<>();
                for (Writer writer : magazine.getWriters()) {
                    writerIds.add(writer.getWriterId());
                }
                List<Writer> completeWriters = new ArrayList<>();
                completeWriters = writerJpaRepository.findAllById(writerIds);
                if (writerIds.size() != completeWriters.size()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some of writers are not found");
                }
                newMagazine.setWriters(completeWriters);
            }
            magazineJpaRepository.save(newMagazine);
            return newMagazine;
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteMagazine(int magazineId) {
        try {
            Magazine magazine = magazineJpaRepository.findById(magazineId).get();
            List<Writer> writers = magazine.getWriters();
            for (Writer i : writers) {
                i.getMagazines().remove(magazine);
            }
            writerJpaRepository.saveAll(writers);

            magazineJpaRepository.deleteById(magazineId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<Writer> getMagazineWriters(int magazineId) {
        try {
            Magazine magazine = magazineJpaRepository.findById(magazineId).get();
            return magazine.getWriters();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}