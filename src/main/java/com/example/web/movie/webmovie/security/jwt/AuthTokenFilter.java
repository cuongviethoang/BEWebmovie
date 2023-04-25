package com.example.web.movie.webmovie.security.jwt;


import com.example.web.movie.webmovie.services.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// OncePerRequestFilterthực  cung cấp một phương thức doFilterInternal() để phân tích cú pháp & xác thực JWT,
//tải chi tiết Người dùng,  kiểm tra Authorizaion
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class); // trả về logger object cho class được truyền vào làm tham số

    //doFilterInternal() sẽ triển khai
    // phân tích cú pháp & xác thực JWT, tải chi tiết
    // Người dùng (sử dụng UserDetailsService),
    // kiểm tra Authorizaion (sử dụng UsernamePasswordAuthenticationToken).
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);  // Lấy chuỗi jwt từ request
            if(jwt != null && jwtUtils.validateJwtToken(jwt)) {
                // jwtUtils.validateJwtToken(jwt) kiểm tra ngoại lệ của token được viết từ class JwtUtils

                // lấy tên người dùng từ từ chuỗi JWT
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                // lấy thông tin người dùng bằng cách truyền username bằng phương thức loadUsername
                // được viết từ class UserDetailsServiceImpl
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                        userDetails, //đối tượng chứa thông tin người dùng
                        null, // mật khẩu người dùng là null vì không cần thiết cho việc xác thực JWT
                        userDetails.getAuthorities() // danh sách các quyền của người dùng được lấy ra từ đối tượng userDetails
                );

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // phương thức sử dụng đối tượng WebAuthenticationDetailsSource
                // để tạo một đối tượng WebAuthenticationDetails,
                // chứa chi tiết xác thực của request, và đặt chi tiết xác thực đó
                // vào đối tượng authentication

                SecurityContextHolder.getContext().setAuthentication(authentication); // Khi đối tượng authentication được đặt vào SecurityContext,
                // nó cho phép ứng dụng xác thực người dùng trong request.
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }
        filterChain.doFilter(request, response); // các Filter tiếp theo hoặc Servlet cuối cùng có thể tiếp tục xử lý request sau khi phương thức doFilterInternal() đã hoàn thành nhiệm vụ
    }

    // Đoạn mã này là một phương thức để lấy chuỗi JWT từ trường Authorization trong request header.
    private String parseJwt(HttpServletRequest request) {

        // phương thức lấy chuỗi Authorization từ đối tượng request header
        String headerAuth = request.getHeader("Authorization");

        if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }
        return null;
    }
}
