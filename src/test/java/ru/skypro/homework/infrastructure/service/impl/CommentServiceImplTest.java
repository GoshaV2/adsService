package ru.skypro.homework.infrastructure.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import ru.skypro.homework.core.mapper.CommentMapper;
import ru.skypro.homework.core.model.Ad;
import ru.skypro.homework.core.model.Comment;
import ru.skypro.homework.core.model.Role;
import ru.skypro.homework.core.model.User;
import ru.skypro.homework.core.repository.AdRepository;
import ru.skypro.homework.core.repository.CommentRepository;
import ru.skypro.homework.infrastructure.dto.request.CommentRequest;
import ru.skypro.homework.infrastructure.dto.response.CommentResponse;
import ru.skypro.homework.infrastructure.exception.NotFoundElementException;
import ru.skypro.homework.infrastructure.facade.AuthenticationFacade;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {
    @Mock
    private CommentRepository commentRepository;
    @Spy
    private CommentMapper commentMapper = Mappers.getMapper(CommentMapper.class);
    @Mock
    private AuthenticationFacade authenticationFacade;
    @Mock
    private AdRepository adRepository;
    @InjectMocks
    private CommentServiceImpl commentService;

    private void mockAuthentication(User user) {
        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(user);
        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
    }

    private void mockCommentRepositorySaveWithSettingId(long id) {
        when(commentRepository.save(any())).thenAnswer(answer -> {
            Comment comment = (Comment) answer.getArguments()[0];
            comment.setId(id);
            return comment;
        });
    }

    private void mockCommentRepositorySaveWithoutSettingId() {
        when(commentRepository.save(any())).thenAnswer(answer -> answer.getArguments()[0]);
    }

    private void mockCommentRepositoryFindById(long commentId, Comment comment) {
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
    }

    private void mockCommentRepositoryFindByIdAndAdIdAndAuthorId(long commentId, long adId, long authorId, Comment comment) {
        when(commentRepository.findByIdAndAdIdAndAuthorId(commentId, adId, authorId))
                .thenReturn(Optional.of(comment));
    }

    private void mockCommentRepositoryFindByIdAndAdIdAndAuthorId_returnEmptyOptional(long commentId, long adId, long authorId) {
        when(commentRepository.findByIdAndAdIdAndAuthorId(commentId, adId, authorId))
                .thenReturn(Optional.empty());
    }

    private void mockAdRepositoryGetReferenceById(long id, Ad ad) {
        when(adRepository.getReferenceById(id)).thenReturn(ad);
    }

    @Test
    void addComment_whenSuccess_thenReturnCommentResponse() {
        User author = getDefaultUser();
        CommentRequest commentRequest = getCommentRequest();
        Ad ad = getAd(author);
        mockAuthentication(author);
        mockCommentRepositorySaveWithSettingId(1);
        mockAdRepositoryGetReferenceById(1, ad);

        CommentResponse commentResponse = commentService.addComment(1, commentRequest);

        assertEquals(commentResponse.getId(), 1);
        assertEquals(commentResponse.getText(), commentRequest.getText());
        assertEquals(commentResponse.getUserId(), author.getId());
        assertEquals(commentResponse.getAuthorFirstName(), author.getFirstName());
        assertNotNull(commentResponse.getCreatedDate());
        assertEquals(commentResponse.getAuthorImageUrl(), author.getUserImageUrl());
    }

    @Test
    void removeComment_whenEditorIsAuthor_thenSuccessDelete() {
        User editor = getDefaultUser();
        Ad ad = getAd(editor);
        Comment comment = getComment(ad, editor);
        mockAuthentication(editor);
        mockCommentRepositoryFindByIdAndAdIdAndAuthorId(1, 1, editor.getId(), comment);

        commentService.removeComment(1, 1);

        verify(commentRepository).delete(comment);
    }

    @Test
    void removeComment_whenEditorIsAdmin_thenSuccessDelete() {
        User editor = getAdminUser();
        User author = getDefaultUser();
        Ad ad = getAd(author);
        Comment comment = getComment(ad, author);
        mockAuthentication(editor);
        mockCommentRepositoryFindById(1, comment);

        commentService.removeComment(1, 1);

        verify(commentRepository).delete(comment);
    }

    @Test
    void removeComment_whenEditorIsNotAuthor_thenSuccessDelete() {
        User editor = getDefaultUserWithoutImage();
        User author = getDefaultUser();
        Ad ad = getAd(author);
        Comment comment = getComment(ad, author);
        mockAuthentication(editor);
        mockCommentRepositoryFindByIdAndAdIdAndAuthorId_returnEmptyOptional(1, ad.getId(),
                editor.getId());

        assertThrows(NotFoundElementException.class,
                () -> commentService.removeComment(1, 1));

        verify(commentRepository, never()).delete(comment);
    }

    @Test
    void updateComment_whenEditorIsAuthor_thenReturnCommentResponse() {
        User editor = getDefaultUser();
        CommentRequest commentRequest = getCommentRequest();
        Ad ad = getAd(editor);
        Comment comment = getComment(ad, editor);
        mockAuthentication(editor);
        mockCommentRepositorySaveWithoutSettingId();
        mockCommentRepositoryFindByIdAndAdIdAndAuthorId(1, 1, editor.getId(), comment);

        CommentResponse commentResponse = commentService.updateComment(1, 1, commentRequest);

        assertEquals(commentResponse.getId(), 1);
        assertEquals(commentResponse.getText(), commentRequest.getText());
        assertEquals(commentResponse.getUserId(), editor.getId());
        assertEquals(commentResponse.getAuthorFirstName(), editor.getFirstName());
        assertNotNull(commentResponse.getCreatedDate());
        assertEquals(commentResponse.getAuthorImageUrl(), editor.getUserImageUrl());
    }

    @Test
    void updateComment_whenEditorIsAdmin_thenReturnCommentResponse() {
        User author = getDefaultUser();
        User editor = getAdminUser();
        CommentRequest commentRequest = getCommentRequest();
        Ad ad = getAd(author);
        Comment comment = getComment(ad, author);
        mockAuthentication(editor);
        mockCommentRepositorySaveWithoutSettingId();
        mockCommentRepositoryFindById(1, comment);

        CommentResponse commentResponse = commentService.updateComment(1, 1, commentRequest);

        verify(commentRepository).save(comment);
        assertEquals(commentResponse.getId(), 1);
        assertEquals(commentResponse.getText(), commentRequest.getText());
        assertEquals(commentResponse.getUserId(), author.getId());
        assertEquals(commentResponse.getAuthorFirstName(), author.getFirstName());
        assertNotNull(commentResponse.getCreatedDate());
        assertEquals(commentResponse.getAuthorImageUrl(), author.getUserImageUrl());
    }

    @Test
    void updateComment_whenEditorIsNotAuthor_thenThrowNotFoundException() {
        User editor = getDefaultUserWithoutImage();
        CommentRequest commentRequest = getCommentRequest();
        mockAuthentication(editor);
        mockCommentRepositoryFindByIdAndAdIdAndAuthorId_returnEmptyOptional(1, 1, editor.getId());

        assertThrows(NotFoundElementException.class,
                () -> commentService.updateComment(1, 1, commentRequest));
        verify(commentRepository, never()).save(any());
    }


    private User getDefaultUserWithoutImage() {
        return User.builder()
                .id(1L)
                .email("test@mail.ru")
                .firstName("name")
                .lastName("surname")
                .password("Password")
                .phone("phone")
                .role(Role.USER)
                .build();
    }

    private User getDefaultUser() {
        return User.builder()
                .id(3L)
                .email("test@mail.ru")
                .firstName("name")
                .lastName("surname")
                .password("Password")
                .phone("phone")
                .role(Role.USER)
                .userImageUrl("image")
                .build();
    }

    private User getAdminUser() {
        return User.builder()
                .id(2L)
                .email("test@mail.ru")
                .firstName("name")
                .lastName("surname")
                .password("Password")
                .phone("phone")
                .role(Role.ADMIN)
                .userImageUrl("image")
                .build();
    }

    private CommentRequest getCommentRequest() {
        return new CommentRequest("text");
    }

    private Comment getComment(Ad ad, User commentAuthor) {
        return Comment.builder()
                .id(1L)
                .text("text1")
                .createdDate(LocalDateTime.now())
                .ad(ad)
                .author(commentAuthor)
                .build();
    }

    private Ad getAd(User author) {
        return Ad.builder()
                .id(1L)
                .author(author)
                .description("description1")
                .title("title1")
                .price(1001L)
                .build();
    }
}