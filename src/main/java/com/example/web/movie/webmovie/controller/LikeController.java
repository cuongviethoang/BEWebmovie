package com.example.web.movie.webmovie.controller;

import com.example.web.movie.webmovie.dto.LikeDto;
import com.example.web.movie.webmovie.model.Like;
import com.example.web.movie.webmovie.payload.response.MessageResponse;
import com.example.web.movie.webmovie.repository.LikeRepository;
import com.example.web.movie.webmovie.services.LikeService;
import com.example.web.movie.webmovie.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class LikeController {

    @Autowired
    LikeService likeService;

    @Autowired
    LikeRepository likeRepository;

    @GetMapping("/movie/{movieId}/likes")
    public ResponseEntity<?> getAllLikeFromMovie(@PathVariable(value = "movieId") Long movieId) {
        List<LikeDto> likeDtos = likeService.getAllLikeFromMovie(movieId);
        return ResponseEntity.ok(likeDtos);
    }

    // http://localhost:8081/api/movie/{id}/like
    @PostMapping("/movie/{movieId}/like")
    public ResponseEntity<?> createLike(Authentication authentication, @PathVariable(value = "movieId") Long movieId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        long userId = userDetails.getId();;
        List<Like> likes = likeRepository.findLikeByMoviesId(movieId);
        for(Like like :likes) {
            if(like.getUser().getId() == userId) {
                likeRepository.delete(like);
                return ResponseEntity.ok(HttpStatus.ACCEPTED);
            }
        }
        likeService.createLike(userDetails.getId(), movieId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
