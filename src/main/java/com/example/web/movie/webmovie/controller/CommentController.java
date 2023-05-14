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
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    ModelMapper modelMapper;

    //http://localhost:8081/api/movies/1/comments
    @GetMapping("/movies/{movieId}/comments")
    public ResponseEntity<List<CommentDto>> getAllComment(@PathVariable(value = "movieId") Long movieId) {
        Movies movies = moviesRepository.findById(movieId).get();

        List<CommentDto> commentDtos = movies.getComments().stream()
                .map(comment -> {
                    CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
                    commentDto.setIdUser(comment.getUser().getId());
                    commentDto.setUsername(comment.getUser().getUsername());
                    commentDto.setImgUser(comment.getUser().getProfileImg());
                    return commentDto;
                }).collect(Collectors.toList());

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
        Movies movies = moviesRepository.findById(movieId).get();
        User user = userRepository.findById(userId).get();
        Comment  comment = new Comment();
        comment.setMovie(movies);
        comment.setUser(user);
        comment.setContent(commentDto.getContent());
        comment.setDate(LocalDate.now());
        LocalTime tm = LocalTime.now();
        comment.setTime(LocalTime.parse(String.format("%02d:%02d:%02d", tm.getHour(), tm.getMinute(), tm.getSecond())));
        commentRepository.save(comment);


        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    // http://localhost:8081/api/movie/{id}/comment/{id}
    @DeleteMapping("comment/{id}")
    public ResponseEntity<?> delComment(Authentication authentication,  @PathVariable(value = "id") Long id) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        long userId = userDetails.getId();;

        Comment comment = commentRepository.findById(id).get();
        long userID = comment.getUser().getId();

        if(userID==userId) {

            commentRepository.deleteById(id);
            return ResponseEntity.ok(HttpStatus.OK);
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Bạn không phải người dùng viết bình luận này"));
    }


}
