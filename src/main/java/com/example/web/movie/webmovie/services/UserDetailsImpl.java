package com.example.web.movie.webmovie.services;

import com.example.web.movie.webmovie.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String email;

    // @JsonIgnore dùng để báo cho thư viện jackson biết có 1 trường ko nên đổi thành json, cụ thể
    // ở đây là trường password
    @JsonIgnore
    private String password;

    private String profileImg;

    private Collection<? extends GrantedAuthority> authorities; // GrantedAuthority là một interface trong Spring Security được sử dụng để
    // đại diện cho quyền được cấp cho một người dùng, authorities trong trường hợp này là một bộ sưu tập các đối tượng GrantedAuthority đại diện
    // cho các quyền được cấp cho người dùng tương ứng


    public UserDetailsImpl(Long id, String username, String email, String password, String profileImg, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.profileImg = profileImg;
        this.authorities = authorities;
    }

    // xây dựng build() truyền user để chuyển đổi thông tin sang UserDetails để spring secirity làm vc
    public static UserDetailsImpl build(User user) {
        // sử dụng Stream API để chuyển đổi mỗi Role thành một đối tượng
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
        // vì sau khi getRoles().stream() ta thu đc 1 Stream các đối tượng GrantedAuthority
        // việc sử sử dung collect(Collectors.toList()) để chuyển stream API sang 1 danh sách các đối tượng GrantedAuthority
        // rồi gán cho biến authorities

        return new UserDetailsImpl(
                user.getId(),// lấy từ tham số truyền vào
                user.getUsername(),// lấy từ tham số truyền vào
                user.getEmail(), // lấy từ tham số truyền vào
                user.getPassword(), // lấy từ tham số truyền vào
                user.getProfileImg(),
                authorities // lấy từ tham số truyền vào
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getProfileImg(){return profileImg;}

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if( o == null || getClass() != o.getClass()) return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
