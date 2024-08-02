package com.example.wordlyweek.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.wordlyweek.model.Magazine;
import com.example.wordlyweek.model.Writer;
import com.example.wordlyweek.service.WriterJpaService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * WriterController
 */
@RestController
public class WriterController {
    @Autowired
    private WriterJpaService writerJpaService;

    @GetMapping("/magazines/writers")
    public List<Writer> getWriters() {
        return writerJpaService.getWriters();
    }

    @PostMapping("/magazines/writers")
    public Writer addWriter(@RequestBody Writer writer) {
        return writerJpaService.addWriter(writer);
    }

    @GetMapping("/magazines/writers/{id}")
    public Writer getWriter(@PathVariable int id) {
        return writerJpaService.getWriterById(id);
    }

    @PutMapping("/magazines/writers/{writerId}")
    public Writer updateWriter(@PathVariable int writerId, @RequestBody Writer writer) {
        return writerJpaService.updateWriter(writerId, writer);
    }

    @DeleteMapping("/magazines/writers/{writerId}")
    public void delteWriter(@PathVariable("writerId") int writerId) {
        writerJpaService.deleteWriter(writerId);
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/writers/{writerId}/magazines")
    public List<Magazine> getWriterMagazines(@PathVariable("magazineId") int magazineId) {
        return writerJpaService.getWriterMagazines(magazineId);
    }

}