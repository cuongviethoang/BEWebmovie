package com.example.web.movie.webmovie.services.serviceImpl;

import com.example.web.movie.webmovie.dto.LikeDto;
import com.example.web.movie.webmovie.model.Like;
import com.example.web.movie.webmovie.model.Movies;
import com.example.web.movie.webmovie.models.User;
import com.example.web.movie.webmovie.repository.LikeRepository;
import com.example.web.movie.webmovie.repository.MoviesRepository;
import com.example.web.movie.webmovie.repository.UserRepository;
import com.example.web.movie.webmovie.services.LikeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MoviesRepository moviesRepository;

    @Override
    public List<LikeDto> getAllLikeFromMovie(Long movieId) {
        Movies movies = moviesRepository.findById(movieId).get();
        List<LikeDto> likeDtos = movies.getLikes().stream()
                .map(like -> {
                    LikeDto likeDto = modelMapper.map(like, LikeDto.class);
                    likeDto.setIdUser(like.getUser().getId());
                    likeDto.setUsername(like.getUser().getUsername());
                    return likeDto;
                }).collect(Collectors.toList());
        return likeDtos;
    }

    @Override
    public void createLike(Long userId, Long movieId) {

        User user = userRepository.findById(userId).get();
        Movies movies = moviesRepository.findById(movieId).get();
        Like like = new Like();
        like.setUser(user);
        like.setMovies(movies);
        likeRepository.save(like);
    }

}
