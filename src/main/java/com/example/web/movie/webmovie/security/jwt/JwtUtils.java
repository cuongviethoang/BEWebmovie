package com.example.web.movie.webmovie.security.jwt;

import com.example.web.movie.webmovie.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    // logger có nhiệm vụ ghi lại các thông tin, cảnh báo hoặc lỗi xảy ra trong ứng dung.
    // tham số truyền vào phương thức getLogger chính là class cần sử dụng getLogger

    @Value("${reelzool.app.jwtSecret}")
    private String jwtSecret; // Khóa bí mật

    @Value("${reelzool.app.jwtExpirationMs}")
    private int jwtExpirationMs; // Thời gian có hiệu lực của chuôi jwt

    // tạo chuỗi jwt từ thông tin user
    public String generateJwtToken(Authentication authentication) {
        // Authentication đại diện cho thông tin xác thực của một người dùng.
        // Khi người dùng đăng nhập vào hệ thống, thông tin xác thực của người dùng
        // sẽ được đưa vào đối tượng Authentication, và sau đó được tự động đưa vào trong đối tượng
        // SecurityContextHolder.

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();// authentication.getPrincipal() lấy thông tin người dùng  đã được xác thực
        // ép kiểu Princial thành UserDetailsImpl.

        // Jwts.builder() được sử dụng thư viện JWT của "io.jsonwebtoken" để tạo 1 chuỗi
        // JWT token, với các thành phần bên dưới
        return Jwts.builder()
                .setSubject((userPrincipal.getUsername())) // thiết lập đối tượng cho token
                .setIssuedAt(new Date()) // thiết lập thời gian phát hành của token hiện tại
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))  // thiết ập thời gian hết hạn của token, từ thời gian hiện tại + thời gian cos hiệu lực của chuỗi JWT
                .signWith(SignatureAlgorithm.HS512, jwtSecret)   // thiết lập thuật toán kí và khóa bí mật để tạo chữ ký cho token, ở đây sử dụng thuật toán ký HS512 và khóa bị mật do mình tự tạo vởi phía trên
                .compact();
    }

    // Lấy thông tin user từ chuỗi JWT
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser() // tạo 1 đối tượng JwtParser
                .setSigningKey(jwtSecret) // cung cấp khóa bí mật được sử dụng để tạo chuỗi token ở trên
                .parseClaimsJws(token)   // giải mã JWT và lấy ra đối tượng "Claim"
                .getBody()
                .getSubject(); // lấy ra tên đăng nhập của người dùng trên đối tượng Claim và trả về kết quả
    }

    // phương thức validateJwtToken để kiểm tra tính hợp lệ của một JWT token
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken); // giải mã token trả về đối tượng Claim
            // nếu thành công thì trả về true -> chuỗi token hợp lệ
            return true;
        } catch (SignatureException e) {
            // bắt ngoại lệ xảy ra khi chữ kí trong token không hợp lệ
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            // xảy ra khi không đúng định dạng
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            // token đã hết hạn
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            // token không được hỗ trợ
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            // chuỗi JWT không có các thông tin cần thiết
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
