package com.example.web.movie.webmovie.services;

import com.example.web.movie.webmovie.models.User;
import com.example.web.movie.webmovie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// @Service  thường chứa các logic xử lý nghiệp vụ và các phương thức tương tác với các tầng khác như
// tầng Controller hoặc tầng DAO (Data Access Object)
// để truy xuất hoặc lưu trữ dữ liệu.
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional  //@Transactional được sử dụng để đảm bảo tính nhất quán của giao dịch trong cơ sở dữ liệu.
    // Khi một phương thức được đánh dấu là @Transactional, Spring sẽ tự động bắt đầu một giao dịch trước khi
    // phương thức được thực thi và sẽ tự động commit hoặc rollback giao dịch sau khi phương thức kết thúc.
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return UserDetailsImpl.build(user);
    }
}
