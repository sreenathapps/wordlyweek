package com.example.wordlyweek.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.wordlyweek.model.*;
import com.example.wordlyweek.service.WriterJpaService;

import org.springframework.web.bind.annotation.*;

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
    public void deleteWriter(@PathVariable("writerId") int writerId) {
        writerJpaService.deleteWriter(writerId);
    }

    @GetMapping("/writers/{writerId}/magazines")
    public List<Magazine> getWriterMagazines(@PathVariable("writerId") int writerId) {
        return writerJpaService.getWriterMagazines(writerId);
    }

}