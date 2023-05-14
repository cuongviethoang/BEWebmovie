package com.example.web.movie.webmovie.services.serviceImpl;

import com.example.web.movie.webmovie.dto.LocalStoreDto;
import com.example.web.movie.webmovie.model.LocalStore;
import com.example.web.movie.webmovie.models.User;
import com.example.web.movie.webmovie.repository.LocalStoreRepository;
import com.example.web.movie.webmovie.repository.MoviesRepository;
import com.example.web.movie.webmovie.repository.UserRepository;
import com.example.web.movie.webmovie.services.LocalStoreService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocalStoreServiceImpl implements LocalStoreService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    LocalStoreRepository localStoreRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MoviesRepository moviesRepository;

    @Override
    public List<LocalStoreDto> getAllMovieInStore(Long userId) {
        User user = userRepository.findById(userId).get();
        List<LocalStoreDto> localStoreDtos =  user.getLocalStores().stream()
                .map(localStore -> {
                    LocalStoreDto localStoreDto = modelMapper.map(localStore, LocalStoreDto.class);
                    localStoreDto.setMovieId(localStore.getMovies().getId());
                    localStoreDto.setMovieName(localStore.getMovies().getOriginal_title());
                    localStoreDto.setMoviePicture(localStore.getMovies().getPoster_path());
                    localStoreDto.setUserId(userId);
                    return localStoreDto;
                }).collect(Collectors.toList());
        return localStoreDtos;
    }

    @Override
    public void createLocalStore(Long userId, Long movieId) {
        LocalStore localStore = new LocalStore();
        localStore.setUser(userRepository.findById(userId).get());
        localStore.setMovies(moviesRepository.findById(movieId).get());
        localStoreRepository.save(localStore);
    }

    @Override
    public void deleteLocalStore(Long localStoreId) {
        localStoreRepository.deleteById(localStoreId);
    }
}
