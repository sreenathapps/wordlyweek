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
import com.example.wordlyweek.repository.WriterJpaRepository;
import com.example.wordlyweek.repository.WriterRepository;

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
        List<Writer> writers = writerJpaRepository.findAll();
        return writers;
    }

    @Override
    public Writer addWriter(Writer writer) {
        writer.setWriterId(0);
        writerJpaRepository.save(writer);
        return writer;
    }

    @Override
    public Writer getWriterById(int writerId) {
        try {
            Writer writer = writerJpaRepository.findById(writerId).get();
            return writer;
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
                for(Magazine i : writer.getMagazines()) {
                    magazineIds.add(i.getMagazineId());
                }
                List<Magazine> completeMagazines = magazineJpaRepository.findAllById(magazineIds);
                if (magazineIds.size() != completeMagazines.size()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some magazines are not found");
                }
                newWriter.setMagazines(completeMagazines);
            }
            writerJpaRepository.save(newWriter);
            return newWriter;
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteWriter(int writerId) {
        try {
            Writer writer = writerJpaRepository.findById(writerId).get();
            List<Magazine> magazines = writer.getMagazines();
            for(Magazine i : magazines) {
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