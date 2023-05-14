package com.example.web.movie.webmovie.controller;

import com.example.web.movie.webmovie.dto.DislikeDto;
import com.example.web.movie.webmovie.model.Dislike;
import com.example.web.movie.webmovie.repository.DislikeRepository;
import com.example.web.movie.webmovie.services.DislikeService;
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
public class DislikeController {

    @Autowired
    DislikeService dislikeService;

    @Autowired
    DislikeRepository dislikeRepository;

    // http://localhost:8081/api/movie/{id}/dislikes
    @GetMapping("/movie/{movieId}/dislikes")
    public ResponseEntity<?> getAllDislikeFromMovie(@PathVariable(value = "movieId") Long movieId) {
        List<DislikeDto> dislikeDtos = dislikeService.getAllDislikeFromMovie(movieId);
        return ResponseEntity.ok(dislikeDtos);
    }

    // http://localhost:8081/api/movie/{id}/dislike
    @PostMapping("/movie/{movieId}/dislike")
    public ResponseEntity<?> createDislike(Authentication authentication, @PathVariable(value = "movieId") Long movieId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        long userId = userDetails.getId();

        // Xử lí khi ấn like 2 lần sẽ tự xóa like đó đi.
        List<Dislike> dislikes = dislikeRepository.findDislikeByMoviesId(movieId);
        for(Dislike dislike:dislikes) {
            if(dislike.getUser().getId() == userId) {
                dislikeRepository.delete(dislike);
                return ResponseEntity.ok(HttpStatus.ACCEPTED);
            }
        }
        dislikeService.createDislike(userId, movieId);
        return ResponseEntity.ok(HttpStatus.OK);
     }
}
