package com.example.web.movie.webmovie.repository;

import com.example.web.movie.webmovie.model.Dislike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DislikeRepository extends JpaRepository<Dislike, Long> {

    List<Dislike> findDislikeByMoviesId(Long moviesId);
}
