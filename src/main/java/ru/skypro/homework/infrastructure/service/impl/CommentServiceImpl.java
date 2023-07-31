package ru.skypro.homework.infrastructure.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.core.mapper.CommentMapper;
import ru.skypro.homework.core.model.Comment;
import ru.skypro.homework.core.model.Role;
import ru.skypro.homework.core.model.User;
import ru.skypro.homework.core.repository.AdRepository;
import ru.skypro.homework.core.repository.CommentRepository;
import ru.skypro.homework.core.service.CommentService;
import ru.skypro.homework.infrastructure.dto.request.CommentRequest;
import ru.skypro.homework.infrastructure.dto.response.CommentListResponse;
import ru.skypro.homework.infrastructure.dto.response.CommentResponse;
import ru.skypro.homework.infrastructure.exception.NotFoundElementException;
import ru.skypro.homework.infrastructure.facade.AuthenticationFacade;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final AuthenticationFacade authenticationFacade;
    private final AdRepository adRepository;

    private Comment getCommentWithUserRole(long adId, long commentId) {
        User user = (User) authenticationFacade.getAuthentication().getPrincipal();
        Role role = user.getRole();
        if (role == Role.ADMIN) {
            return commentRepository.findById(commentId)
                    .orElseThrow(() -> new NotFoundElementException(commentId, Comment.class));
        } else {
            return commentRepository.findByIdAndAdIdAndAuthorId(commentId, adId, user.getId())
                    .orElseThrow(() ->
                            new NotFoundElementException("Entity(%s) with id=%d and adId=%d and userId=%d not be found",
                                    Comment.class, commentId, adId, user.getId()));
        }
    }

    @Override
    public CommentListResponse getCommentOfAd(long adId) {
        List<Comment> commentsOfAd = commentRepository.findAllByAdId(adId);
        return commentMapper.toCommentListResponse(commentsOfAd);
    }

    @Override
    public CommentResponse addComment(long adId, CommentRequest commentRequest) {
        User user = (User) authenticationFacade.getAuthentication().getPrincipal();
        Comment comment = commentMapper.fromCommentRequest(commentRequest);
        comment.setAd(adRepository.getReferenceById(adId));
        comment.setAuthor(user);
        comment.setCreatedDate(LocalDateTime.now());
        commentRepository.save(comment);
        return commentMapper.toCommentResponse(comment);
    }

    @Override
    public CommentResponse getCommentResponse(long adId, long commentId) {
        Comment comment = getCommentWithUserRole(adId, commentId);
        return commentMapper.toCommentResponse(comment);
    }

    @Override
    public void removeComment(long adId, long commentId) {
        Comment comment = getCommentWithUserRole(adId, commentId);
        commentRepository.delete(comment);
    }

    @Override
    public CommentResponse updateComment(long adId, long commentId, CommentRequest commentRequest) {
        Comment comment = getCommentWithUserRole(adId, commentId);
        comment.setText(commentRequest.getText());
        comment.setCreatedDate(LocalDateTime.now());
        commentRepository.save(comment);
        return commentMapper.toCommentResponse(comment);
    }


}
