package ru.skypro.homework.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.core.model.Comment;
import ru.skypro.homework.infrastructure.dto.request.CommentRequest;
import ru.skypro.homework.infrastructure.dto.response.CommentListResponse;
import ru.skypro.homework.infrastructure.dto.response.CommentResponse;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {
    @Mapping(target = "userId", source = "id")
    @Mapping(target = "authorFirstName", source = "author.firstName")
    public abstract CommentResponse toCommentResponse(Comment comment);

    public CommentListResponse toCommentListResponse(List<Comment> commentList) {
        CommentListResponse commentListResponse = new CommentListResponse();
        commentListResponse.setCommentResponseList(commentList.stream()
                .map(this::toCommentResponse).collect(Collectors.toList()));
        commentListResponse.setCount(commentList.size());
        return commentListResponse;
    }

    public abstract Comment fromCommentRequest(CommentRequest commentRequest);
}
