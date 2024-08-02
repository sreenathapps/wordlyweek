package com.example.wordlyweek.repository;

import java.util.List;

import com.example.wordlyweek.model.Magazine;
import com.example.wordlyweek.model.Writer;

/**
 * WriterRepository
 */
public interface WriterRepository {

    List<Writer> getWriters();
    Writer addWriter(Writer writer);
    Writer getWriterById(int writerId);
    Writer updateWriter(int writerId, Writer writer);
    void deleteWriter(int writerId);
    List<Magazine> getWriterMagazines(int writerId);
}