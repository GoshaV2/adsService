package ru.skypro.homework.core.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.core.model.Ad;
import ru.skypro.homework.core.model.Comment;
import ru.skypro.homework.core.model.Role;
import ru.skypro.homework.core.model.User;
import ru.skypro.homework.infrastructure.dto.request.CommentRequest;
import ru.skypro.homework.infrastructure.dto.response.CommentListResponse;
import ru.skypro.homework.infrastructure.dto.response.CommentResponse;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class CommentMapperTest {
    private final CommentMapper commentMapper = Mappers.getMapper(CommentMapper.class);

    @Test
    void toCommentResponse_whenSuccessData() {
        Comment comment = getComment();
        CommentResponse commentResponse = commentMapper.toCommentResponse(comment);

        assertThat(commentResponse,
                hasProperty("id", equalTo(comment.getId())));
        assertThat(commentResponse,
                hasProperty("text", equalTo(comment.getText())));
        assertThat(commentResponse,
                hasProperty("userId", equalTo(comment.getAuthor().getId())));
        assertThat(commentResponse,
                hasProperty("createdDate", equalTo(comment.getCreatedDate())));
        assertThat(commentResponse,
                hasProperty("authorFirstName", equalTo(comment.getAuthor().getFirstName())));
        assertThat(commentResponse,
                hasProperty("authorImageUrl", equalTo(comment.getAuthor().getUserImageUrl())));
    }

    @Test
    void toCommentListResponse_whenSuccessData() {
        List<Comment> comments = getCommentList();
        CommentListResponse commentListResponse = commentMapper.toCommentListResponse(comments);

        assertThat(commentListResponse,
                hasProperty("count", equalTo(comments.size())));
        assertThat(commentListResponse,
                hasProperty("commentResponseList", hasSize(comments.size())));
    }

    @Test
    void fromCommentRequest_whenSuccessData() {
        CommentRequest commentRequest = getCommentRequest();
        Comment comment = commentMapper.fromCommentRequest(commentRequest);

        assertThat(comment,
                hasProperty("text",equalTo(commentRequest.getText())));
    }

    private Ad getAd() {
        User user = User.builder()
                .id(1L)
                .email("test@mail.ru")
                .phone("+777777777")
                .role(Role.USER)
                .firstName("name")
                .lastName("surname")
                .password("secret")
                .build();
        return Ad.builder()
                .id(1L)
                .author(user)
                .description("description")
                .title("title")
                .price(1000)
                .build();
    }

    private Comment getComment() {
        User user = User.builder()
                .id(1L)
                .email("test@mail.ru")
                .phone("+777777777")
                .role(Role.USER)
                .firstName("name")
                .lastName("surname")
                .password("secret")
                .userImageUrl("imageUrl")
                .build();
        return Comment.builder()
                .id(1L)
                .author(user)
                .ad(getAd())
                .text("text")
                .createdDate(LocalDateTime.now())
                .build();
    }

    private List<Comment> getCommentList() {
        return List.of(getComment(), getComment());
    }

    private CommentRequest getCommentRequest() {
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setText("text");
        return commentRequest;
    }
}