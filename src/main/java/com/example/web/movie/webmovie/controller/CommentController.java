package com.example.web.movie.webmovie.controller;

import com.example.web.movie.webmovie.exception.ResourceNotFoundException;
import com.example.web.movie.webmovie.model.Comment;
import com.example.web.movie.webmovie.repository.CommentRepository;
import com.example.web.movie.webmovie.repository.MoviesRepository;
import com.example.web.movie.webmovie.repository.UserRepository;
import com.example.web.movie.webmovie.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MoviesRepository moviesRepository;

    @Autowired
    private UserRepository userRepository;

    //http://localhost:8081/api/movies/1/comments
    @GetMapping("/movies/{movieId}/comments")
    public ResponseEntity<List<Comment>> getAllComment(@PathVariable(value = "movieId") Long movieId) {
        if(!moviesRepository.existsById(movieId)) {
            throw new ResourceNotFoundException("Not found Movie with id = " + movieId);
        }

        List<Comment> comments = commentRepository.findCommentByMoviesId(movieId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/comment/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable(value = "id") Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found comment with id = " + id));

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    // http://localhost:8081/api/movies/1/comments
    @CrossOrigin
    @PostMapping("/movies/{movieId}/comments")
    public ResponseEntity<Comment> createComment(Authentication authentication,  @PathVariable(value = "movieId") Long movieId, @RequestBody Comment commentRequest) {

        UserDetailsImpl userDetails = (UserDetailsImpl)  authentication.getPrincipal();
        Long userId = userDetails.getId();
        Comment comment = moviesRepository.findById(movieId).map(movie -> {
            commentRequest.setMovie(movie);

//            commentRequest.setUser(userRepository.getReferenceById(userId));
            commentRequest.setUser(userRepository.findById(userId).get());
            commentRequest.setDate(LocalDate.now());
            LocalTime tm = LocalTime.now();
            //commentRequest.setTime(LocalTime.parse(tm.getHour() + ":" + tm.getMinute() + ":" + tm.getSecond()));
            commentRequest.setTime(LocalTime.parse(String.format("%02d:%02d:%02d", tm.getHour(), tm.getMinute(), tm.getSecond())));

            return commentRepository.save(commentRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found movie with id = " + movieId));

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable(value = "id") Long id, @RequestBody Comment commentRequest) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment " + id + "not found"));

        comment.setDate(LocalDate.now());
        comment.setContent(commentRequest.getContent());

        return new ResponseEntity<>(comment, HttpStatus.ACCEPTED);
    }


}
