package com.example.web.movie.webmovie.services;

import com.example.web.movie.webmovie.dto.CommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> getAllCommentOfMovie(Long movieId);

    void createComment(Long userId, Long movieId, CommentDto commentDto);


}
