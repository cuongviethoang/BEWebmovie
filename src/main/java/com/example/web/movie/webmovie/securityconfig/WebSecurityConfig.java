package com.example.web.movie.webmovie.securityconfig;

import com.example.web.movie.webmovie.security.jwt.AuthEntryPointJwt;
import com.example.web.movie.webmovie.security.jwt.AuthTokenFilter;
import com.example.web.movie.webmovie.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity( // là một annotation trong Spring Security dùng để kích hoạt tính năng bảo mật cho các phương thức trong ứng dụng.
        prePostEnabled = true // prePostEnabled: Nếu được đặt thành true, cho phép sử dụng các annotation @PreAuthorize, @PostAuthorize,
        // @PreFilter và @PostFilter để bảo mật các phương thức trong ứng dụng.
)
public class WebSecurityConfig {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        // AuthenticationManager có một DaoAuthenticationProvider(với sự trợ giúp của UserDetailsService & PasswordEncoder)
        // để xác thực UsernamePasswordAuthenticationToken đối tượng.
        // Nếu thành công, AuthenticationManagertrả về một đối tượng Xác thực được
        // điền đầy đủ (bao gồm cả các cơ quan được cấp).

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // sử dụng thuật toán bcrup mã hóa mật khẩu trước
        // khi lưu vào csdl, và so sánh với mật khẩu dược cung cấp trong quá trình đăng nhập để xác thực đăng nhập
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()  // Tắt tính năng bảo vệ chống tấn công giả mạo trang web (CSRF) và bật cấu hình cho CORS để cho phép các yêu cầu từ các tên miền khác nhau được chấp nhận.
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and() //  Xác định authenticationEntryPoint (nơi xử lý các yêu cầu bị từ chối truy cập vì không có quyền truy cập) bằng unauthorizedHandler.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/api/auth/**").permitAll() // Xác định quy tắc cho việc phân quyền truy cập vào các yêu cầu. Ở đây, yêu cầu có đường dẫn bắt đầu bằng "/api/auth" và "/api/test" được cho phép truy cập mà không cần xác thực, các yêu cầu khác cần xác thực để truy cập.
                .antMatchers("/api/test/**").permitAll()
                .antMatchers("/api/file/**").permitAll()
                .anyRequest().authenticated();

        http.authenticationProvider(authenticationProvider()); // Cung cấp authenticationProvider, một đối tượng cung cấp thông tin đăng nhập cho Spring Security.

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class); //  Thêm authenticationJwtTokenFilter trước UsernamePasswordAuthenticationFilter trong chuỗi bộ lọc Spring Security.
        // authenticationJwtTokenFilter là một Filter để xác thực người dùng dựa trên JWT, nó được thêm vào bộ lọc Spring Security để thực hiện việc này.


        return http.build();  //  Xây dựng và trả về chuỗi bộ lọc bảo mật được cấu hình.
    }
}
