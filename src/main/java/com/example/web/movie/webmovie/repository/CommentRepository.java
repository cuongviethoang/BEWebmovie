package com.example.web.movie.webmovie.repository;

import com.example.web.movie.webmovie.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findCommentByMoviesId(Long moviesId);
}
