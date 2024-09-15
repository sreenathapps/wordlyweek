package com.example.wordlyweek.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.wordlyweek.model.*;
import com.example.wordlyweek.repository.*;

/**
 * WriterJpaService
 */
@Service
public class WriterJpaService implements WriterRepository {

    @Autowired
    private WriterJpaRepository writerJpaRepository;
    @Autowired
    private MagazineJpaRepository magazineJpaRepository;

    @Override
    public List<Writer> getWriters() {
        return writerJpaRepository.findAll();
    }

    @Override
    public Writer addWriter(Writer writer) {
        try {
            List<Integer> magazineIds = new ArrayList<>();
            for (Magazine m : writer.getMagazines()) {
                magazineIds.add(m.getMagazineId());
            }
            List<Magazine> magazines = magazineJpaRepository.findAllById(magazineIds);
            if (magazineIds.size() != magazines.size()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some of magazines are not found");
            }
            writer.setMagazines(magazines);
            return writerJpaRepository.save(writer);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Writer getWriterById(int writerId) {
        try {
            return writerJpaRepository.findById(writerId).get();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Writer updateWriter(int writerId, Writer writer) {
        try {
            Writer newWriter = writerJpaRepository.findById(writerId).get();
            if (writer.getBio() != null) {
                newWriter.setBio(writer.getBio());
            }
            if (writer.getWriterName() != null) {
                newWriter.setWriterName(writer.getWriterName());
            }
            if (writer.getMagazines() != null) {
                List<Integer> magazineIds = new ArrayList<>();
                for (Magazine i : writer.getMagazines()) {
                    magazineIds.add(i.getMagazineId());
                }
                List<Magazine> completeMagazines = magazineJpaRepository.findAllById(magazineIds);
                if (magazineIds.size() != completeMagazines.size()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some magazines are not found");
                }
                newWriter.setMagazines(completeMagazines);
            }
           return writerJpaRepository.save(newWriter);
            
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteWriter(int writerId) {
        try {
            Writer writer = writerJpaRepository.findById(writerId).get();
            List<Magazine> magazines = writer.getMagazines();
            for (Magazine i : magazines) {
                i.getWriters().remove(writer);
            }
            magazineJpaRepository.saveAll(magazines);
            writerJpaRepository.delete(writer);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<Magazine> getWriterMagazines(int writerId) {
        try {
            Writer writer = writerJpaRepository.findById(writerId).get();
            return writer.getMagazines();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}