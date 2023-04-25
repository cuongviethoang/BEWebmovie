package com.example.web.movie.webmovie.repository;

import com.example.web.movie.webmovie.model.Movies;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoviesRepository extends JpaRepository<Movies, Long> {
    List<Movies> findMoviesByGenersId(Long generId);


}
