package ru.skypro.homework.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.core.model.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByAdId(long adId);

    Optional<Comment> findByIdAndAdId(long commentId, long adId);

    void deleteByIdAndAdId(long commentId, long adId);
}