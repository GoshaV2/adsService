package ru.skypro.homework.infrastructure.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.core.mapper.CommentMapper;
import ru.skypro.homework.core.model.Comment;
import ru.skypro.homework.core.model.User;
import ru.skypro.homework.core.repository.CommentRepository;
import ru.skypro.homework.core.service.AdService;
import ru.skypro.homework.core.service.CommentService;
import ru.skypro.homework.infrastructure.dto.request.CommentRequest;
import ru.skypro.homework.infrastructure.dto.response.CommentListResponse;
import ru.skypro.homework.infrastructure.dto.response.CommentResponse;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final AdService adService;

    private Comment getComment(long adId, long commentId) {
        //todo exception
        return commentRepository.findByIdAndAdId(commentId, adId).orElseThrow();
    }

    @Override
    @Transactional
    public CommentListResponse getCommentOfAd(long adId) {
        List<Comment> commentsOfAd = commentRepository.findAllByAdId(adId);
        return commentMapper.toCommentListResponse(commentsOfAd);
    }

    @Override
    public CommentResponse addComment(long adId, CommentRequest commentRequest, User author) {
        Comment comment = commentMapper.fromCommentRequest(commentRequest);
        comment.setAd(adService.getAd(adId));
        comment.setAuthor(author);
        comment.setCreatedDate(LocalDateTime.now());
        commentRepository.save(comment);
        return commentMapper.toCommentResponse(comment);
    }

    @Override
    public CommentResponse getCommentResponse(long adId, long commentId) {
        Comment comment = getComment(adId, commentId);
        return commentMapper.toCommentResponse(comment);
    }

    @Override
    public void removeComment(long adId, long commentId) {
        commentRepository.deleteByIdAndAdId(commentId, adId);
    }

    @Override
    public CommentResponse updateComment(long adId, long commentId, CommentRequest commentRequest) {
        Comment comment = getComment(adId, commentId);
        comment.setText(commentRequest.getText());
        comment.setCreatedDate(LocalDateTime.now());
        commentRepository.save(comment);
        return commentMapper.toCommentResponse(comment);
    }
}
