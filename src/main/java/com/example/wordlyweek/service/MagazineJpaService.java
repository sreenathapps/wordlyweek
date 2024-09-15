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
        try {
            List<Integer> writerIds = new ArrayList<>();
            for(Writer w : magazine.getWriters()) {
                writerIds.add(w.getWriterId());
            }
            List<Writer> writers = writerJpaRepository.findAllById(writerIds);
            if (writerIds.size() != writers.size()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some of authors are not been found");
            }
            magazine.setWriters(writers);

            for (Writer w : writers) {
                w.getMagazines().add(magazine);
            }

            Magazine savedMagazine = magazineJpaRepository.save(magazine);

            writerJpaRepository.saveAll(writers);

            return savedMagazine;

        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Magazine getMagazineById(int magazineId) {
        try {
            return magazineJpaRepository.findById(magazineId).get();
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
                List<Writer> writers = newMagazine.getWriters();
                for (Writer w : writers) {
                    w.getMagazines().remove(newMagazine);
                }
                writerJpaRepository.saveAll(writers);

                List<Integer> writerIds = new ArrayList<>();
                for (Writer w : magazine.getWriters()) {
                    writerIds.add(w.getWriterId());
                }
                List<Writer> newWriters = writerJpaRepository.findAllById(writerIds);

                if (writerIds.size() != newWriters.size()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
                for (Writer w : newWriters) {
                    w.getMagazines().add(newMagazine);
                }

                writerJpaRepository.saveAll(newWriters);

                newMagazine.setWriters(newWriters);
            }
            return magazineJpaRepository.save(newMagazine);
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
        } catch (NoSuchElementException e) {
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