package com.example.web.movie.webmovie.controller;

import com.example.web.movie.webmovie.models.ERole;
import com.example.web.movie.webmovie.models.Role;
import com.example.web.movie.webmovie.models.User;
import com.example.web.movie.webmovie.payload.request.LoginRequest;
import com.example.web.movie.webmovie.payload.request.SignupRequest;
import com.example.web.movie.webmovie.payload.response.JwtResponse;
import com.example.web.movie.webmovie.payload.response.MessageResponse;
import com.example.web.movie.webmovie.repository.RoleRepository;
import com.example.web.movie.webmovie.repository.UserRepository;
import com.example.web.movie.webmovie.security.jwt.JwtUtils;
import com.example.web.movie.webmovie.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;   // Mã hóa chuỗi thông tin người dùng thành chuỗi JWT

    // http://localhost:8081/api/auth/signin
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        // authenticationManager dùng để xác thực thông tin đăng nhập của người dùng
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication); // tạo chuỗi JWT từ thông tin người dùng

        // thông tin chi tiết của người dùng đã đc xác thực từ đối tượng Authentication
        // Khi người dùng đã được xác thực, đối tượng Authentication sẽ được lưu trữ trong SecurityContextHolder, bao gồm cả đối tượng Principal mà chứa thông tin về người dùng.
        // tuy nhiên đối tượng Principal lưu trữ dưới dạng là 1 Object nên phải ép kiểu
        // sang UserDetailsImp để spring security cos thể hiểu đc
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // sử dụng phương thức getAuthorities() để lấy danh sách các đối tượng GrantedAuthority,
        // sau đó sử dụng phương thức stream() của lớp List để chuyển đổi danh sách này thành một luồng dữ liệu (stream).
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList()); // phương thức collect() để thu thập các chuỗi này thành một danh sách mới của lớp List<String>.

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getProfileImg(),
                roles));
    }

    // http://localhost:8081/api/auth/signup
    @PostMapping("signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already token!"));
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signupRequest.getUsername(), signupRequest.getEmail(),
                encoder.encode(signupRequest.getPassword()));

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if(strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }
}
