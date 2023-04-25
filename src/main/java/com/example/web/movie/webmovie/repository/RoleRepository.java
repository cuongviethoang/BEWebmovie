package com.example.web.movie.webmovie.repository;

import com.example.web.movie.webmovie.models.ERole;
import com.example.web.movie.webmovie.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
