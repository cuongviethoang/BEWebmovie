package com.example.web.movie.webmovie.repository;

import com.example.web.movie.webmovie.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);


    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.profileImg = :path WHERE u.id = :id")
    void uploadPicture(Long id, String path);
}
