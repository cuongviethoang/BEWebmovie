package com.example.web.movie.webmovie.repository;

import com.example.web.movie.webmovie.model.Gener;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GenerRepository extends JpaRepository<Gener, Long> {
    List<Gener> findGenersByMoviesId(Long moviesId);


    Gener findByName(String name);
}
