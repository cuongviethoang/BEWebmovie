package com.example.web.movie.webmovie.repository;

import com.example.web.movie.webmovie.model.Movies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

public interface MoviesRepository extends JpaRepository<Movies, Long> {
    List<Movies> findMoviesByGenersId(Long generId);

    @Query("select m from Movies m where m.original_title like %:name% ")
    List<Movies> findNameMovie(@Param("name") String name);

    @Modifying
    @Transactional
    @Query("update Movies m set m.backdrop_path = ?2, m.original_title = ?3, m.overview = ?4, m.poster_path = ?5, m.release_date = ?6, m.vote_average = ?7, m.vote_count = ?8, m.runtime = ?9, m.tagline = ?10, m.link_trailer = ?11, m.link_movie = ?12 where m.id = ?1" )
    void updateMovie(Long id, String backdrop_path, String original_title, String overview, String poster_path,
                     LocalDate release_date, Double vote_average, int vote_count, int runtime,
                     String tagline, String link_trailer, String link_movie);


}
