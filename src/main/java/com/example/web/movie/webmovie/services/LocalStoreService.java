package com.example.web.movie.webmovie.services;

import com.example.web.movie.webmovie.dto.LocalStoreDto;

import java.util.List;

public interface LocalStoreService {

    List<LocalStoreDto> getAllMovieInStore(Long userId);
    void createLocalStore(Long userId, Long movieId);

    void deleteLocalStore(Long localStoreId);
}
