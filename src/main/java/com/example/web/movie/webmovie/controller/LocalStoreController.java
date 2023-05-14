package com.example.web.movie.webmovie.controller;

import com.example.web.movie.webmovie.dto.LocalStoreDto;
import com.example.web.movie.webmovie.repository.LocalStoreRepository;
import com.example.web.movie.webmovie.services.LocalStoreService;
import com.example.web.movie.webmovie.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class LocalStoreController {

    @Autowired
    LocalStoreService localStoreService;

    @Autowired
    LocalStoreRepository localStoreRepository;

    // http://localhost:8081/api/localStores
    @GetMapping("/localStores")
    public ResponseEntity<?> getAllMovieInStore(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<LocalStoreDto> localStoreDtos = localStoreService.getAllMovieInStore(userDetails.getId());
        return ResponseEntity.ok(localStoreDtos);
    }

    // http://localhost:8081/api/movie/{id}/localStore
    @PostMapping("/movie/{movieId}/localStore")
    public ResponseEntity<?> addMovieInStore(Authentication authentication, @PathVariable(value = "movieId") Long movieId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        localStoreService.createLocalStore(userDetails.getId(), movieId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    // http://localhost:8081/api/localStore/{id}
    @DeleteMapping("/localStore/{id}")
    public ResponseEntity<?> deleteMovieInStore(@PathVariable(value = "id") Long id) {
        localStoreService.deleteLocalStore(id);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }
}
