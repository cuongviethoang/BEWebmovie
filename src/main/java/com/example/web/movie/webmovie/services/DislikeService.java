package com.example.web.movie.webmovie.services;

import com.example.web.movie.webmovie.dto.DislikeDto;

import java.util.List;

public interface DislikeService {

    List<DislikeDto> getAllDislikeFromMovie(Long movieId);

    void createDislike(Long userId, Long movieId);
}
