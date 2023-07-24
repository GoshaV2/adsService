package ru.skypro.homework.core.repository;

import org.springframework.stereotype.Repository;

import java.io.InputStream;

@Repository
public interface FileRepository {
    void addFile(String name, InputStream inputStream);

    InputStream getFile(String name);
}
