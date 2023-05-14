package com.example.web.movie.webmovie.services.serviceImpl;

import com.example.web.movie.webmovie.dto.DislikeDto;
import com.example.web.movie.webmovie.model.Dislike;
import com.example.web.movie.webmovie.model.Movies;
import com.example.web.movie.webmovie.repository.DislikeRepository;
import com.example.web.movie.webmovie.repository.MoviesRepository;
import com.example.web.movie.webmovie.repository.UserRepository;
import com.example.web.movie.webmovie.services.DislikeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DislikeServiceImpl implements DislikeService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    DislikeRepository dislikeRepository;

    @Autowired
    MoviesRepository moviesRepository;

    @Autowired
    UserRepository userRepository;
    @Override
    public List<DislikeDto> getAllDislikeFromMovie(Long movieId) {
        Movies movies = moviesRepository.findById(movieId).get();
        List<DislikeDto> dislikeDtos = movies.getDislikes().stream()
                .map(dislike -> {
                    DislikeDto dislikeDto = modelMapper.map(dislike, DislikeDto.class);
                    dislikeDto.setUserId(dislike.getUser().getId());
                    dislikeDto.setUsername(dislike.getUser().getUsername());
                    return dislikeDto;
                }).collect(Collectors.toList());
        return dislikeDtos;
    }

    @Override
    public void createDislike(Long userId, Long movieId) {
        Dislike dislike = new Dislike();
        dislike.setUser(userRepository.findById(userId).get());
        dislike.setMovie(moviesRepository.findById(movieId).get());
        dislikeRepository.save(dislike);
    }
}
