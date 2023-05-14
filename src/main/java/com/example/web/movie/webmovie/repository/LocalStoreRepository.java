package com.example.web.movie.webmovie.repository;

import com.example.web.movie.webmovie.model.LocalStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalStoreRepository extends JpaRepository<LocalStore, Long> {

}
