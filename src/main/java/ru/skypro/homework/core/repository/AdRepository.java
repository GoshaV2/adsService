package ru.skypro.homework.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.core.model.Ad;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {
    List<Ad> findAllByAuthorId(long authorId);

    Page<Ad> findAllByTitleContainsOrDescriptionContains(String title, String description, Pageable pageable);
}
