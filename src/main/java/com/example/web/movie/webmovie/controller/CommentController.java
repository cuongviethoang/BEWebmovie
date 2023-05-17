package com.example.web.movie.webmovie.controller;

import com.example.web.movie.webmovie.dto.CommentDto;
import com.example.web.movie.webmovie.exception.ResourceNotFoundException;
import com.example.web.movie.webmovie.model.Comment;
import com.example.web.movie.webmovie.model.Movies;
import com.example.web.movie.webmovie.models.User;
import com.example.web.movie.webmovie.payload.response.MessageResponse;
import com.example.web.movie.webmovie.repository.CommentRepository;
import com.example.web.movie.webmovie.repository.MoviesRepository;
import com.example.web.movie.webmovie.repository.UserRepository;
import com.example.web.movie.webmovie.services.CommentService;
import com.example.web.movie.webmovie.services.UserDetailsImpl;
import org.aspectj.bridge.Message;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    CommentService commentService;


    //http://localhost:8081/api/movies/1/comments
    @GetMapping("/movies/{movieId}/comments")
    public ResponseEntity<List<CommentDto>> getAllComment(@PathVariable(value = "movieId") Long movieId) {
        List<CommentDto> commentDtos = commentService.getAllCommentOfMovie(movieId);
        return ResponseEntity.ok(commentDtos);
    }

    // http://localhost:8081/api/movies/1/comments
    @CrossOrigin
    @PostMapping("/movies/{movieId}/comments")
    public ResponseEntity<?> createComment(Authentication authentication,
    		@PathVariable(value = "movieId") Long movieId,
    		@RequestBody CommentDto commentDto) {

        UserDetailsImpl userDetails = (UserDetailsImpl)  authentication.getPrincipal();
        Long userId = userDetails.getId();
        commentService.createComment(userId, movieId, commentDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    // http://localhost:8081/api/movie/{id}/comment/{id}
    @CrossOrigin
    @DeleteMapping("comment/{id}")
    public ResponseEntity<?> delComment(Authentication authentication,  @PathVariable(value = "id") Long id) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        long userId = userDetails.getId();
        Comment comment = commentRepository.findById(id).get();
        long userID = comment.getUser().getId();
        if(userID==userId) {
            commentRepository.deleteById(id);
            return ResponseEntity.ok(HttpStatus.OK);
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Bạn không phải người dùng viết bình luận này"));
    }
}
