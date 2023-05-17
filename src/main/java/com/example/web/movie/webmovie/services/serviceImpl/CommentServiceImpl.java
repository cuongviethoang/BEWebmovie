package com.example.web.movie.webmovie.services.serviceImpl;

import com.example.web.movie.webmovie.dto.CommentDto;
import com.example.web.movie.webmovie.model.Comment;
import com.example.web.movie.webmovie.model.Movies;
import com.example.web.movie.webmovie.repository.CommentRepository;
import com.example.web.movie.webmovie.repository.MoviesRepository;
import com.example.web.movie.webmovie.repository.UserRepository;
import com.example.web.movie.webmovie.services.CommentService;
import com.example.web.movie.webmovie.services.UserDetailsImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    MoviesRepository moviesRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<CommentDto> getAllCommentOfMovie(Long movieId) {
        Movies movies = moviesRepository.findById(movieId).get();
        List<CommentDto> commentDtos = movies.getComments().stream()
                .map(comment ->  {
                    CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
                    commentDto.setIdUser(comment.getUser().getId());
                    commentDto.setUsername(comment.getUser().getUsername());
                    commentDto.setImgUser(comment.getUser().getProfileImg());
                    return commentDto;
                }).collect(Collectors.toList());
        Collections.sort(commentDtos, Comparator.comparing(CommentDto::getTime).reversed());
        Collections.sort(commentDtos, Comparator.comparing(CommentDto::getDate).reversed());
        return commentDtos;
    }

    @Override
    public void createComment(Long userId, Long movieId, CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setUser(userRepository.findById(userId).get());
        comment.setMovie(moviesRepository.findById(movieId).get());
        comment.setContent(commentDto.getContent());
        comment.setDate(LocalDate.now());
        LocalTime tm = LocalTime.now();
        comment.setTime(LocalTime.parse(String.format("%02d:%02d:%02d", tm.getHour(), tm.getMinute(), tm.getSecond())));
        commentRepository.save(comment);
    }
}
