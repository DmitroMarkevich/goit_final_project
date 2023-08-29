//package com.example.demo.config;
//
//import com.example.demo.user.UserEntity;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.Getter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.UUID;
//
//public class UserDetailsImpl implements UserDetails {
//    @Getter
//    private final UUID id;
//    @Getter
//    private final String username;
//    @Getter
//    private final String email;
//    private final String password;
//
//    private final Collection<? extends GrantedAuthority> authorities;
//
//    public UserDetailsImpl(UUID id, String username, String email, String password,
//                           Collection<? extends GrantedAuthority> authorities) {
//        this.id = id;
//        this.username = username;
//        this.email = email;
//        this.password = password;
//        this.authorities = authorities;
//    }
//
//    public static UserDetailsImpl build(UserEntity user) {
//        return new UserDetailsImpl(user.getId(),
//                user.getUsername(),
//                user.getEmail(),
//                user.getPassword(),
//                List.of());
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return username;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}