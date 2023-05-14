package com.example.web.movie.webmovie.repository;

import com.example.web.movie.webmovie.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    List<Like> findLikeByMoviesId(Long moviesId);
}
