package com.example.web.movie.webmovie.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class); // sẽ trả về 1 logger object cho class AuthEntryPointJwt
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // AuthenticationException là 1 clss dc định nghĩa trong spring security được sử dụng để xác định các
        // lỗi xác thực trong ứng dụng web
        logger.error("Unauthorized error: {}", authException.getMessage());// authException.getMessage() được sử dụng để lấy
        // thông tin chi tiết về lỗi xác thực (authentication error) và ghi vào log, hoặc trả về cho client

        // Thiết lặp các đối tượng của HttpServletResponse trả về cho client từ phía server
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); // trả về cho người dùng 1 đối tượng JSON
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // thiết lập mã trạng thái lỗi 401 cho ng dùng biết họ không được xác thực

        // Thiết lập nội dung của response trả về chứa các thông tin về lỗi
        final Map<String, Object> body =  new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);// truyền vào status code
        body.put("error", "Unauthorized"); // truyền vào error message "Unauthorized"
        body.put("message", authException.getMessage());  // truyền vào message cụ thể tại sao request không được xác thực
        body.put("path", request.getServletPath()); // dường dâẫn request gây lỗi

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body); // Chuyển đổi đối tượng JSON thành dạng string và
        // ghi vào outputStream của response, để trả về kết quả cho client.
    }
}
