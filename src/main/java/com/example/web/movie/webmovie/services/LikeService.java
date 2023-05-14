package com.example.web.movie.webmovie.services;

import com.example.web.movie.webmovie.dto.LikeDto;

import java.util.List;

public interface LikeService {

    List<LikeDto> getAllLikeFromMovie(Long movieId);
    void createLike(Long userId, Long movieId);


}
