package com.example.demo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return new User(userEntity.getUsername(), userEntity.getPassword(), List.of());
    }

    public void createUser(UserDto userDto) {
        userRepository.save(userMapper.mapDtoToEntity(userDto).toBuilder()
                .password(passwordEncoder.encode(userDto.getPassword()))
                .updatedAt(new Timestamp(System.currentTimeMillis()))
                .build()
        );
    }

    public UserDto updateUser(UserDto userDto) {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(userDto.getUsername());

        if (optionalUser.isPresent()) {
            return userMapper.mapEntityToDto(userRepository.save(optionalUser.get().toBuilder()
                    .email(userDto.getEmail())
                    .firstName(userDto.getFirstName())
                    .lastName(userDto.getLastName())
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .updatedAt(new Timestamp(System.currentTimeMillis())).build())
            );
        }

        return new UserDto();
    }

    public UserDto getUser() {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(getUsername());

        if (optionalUser.isPresent()) {
            return userMapper.mapEntityToDto(optionalUser.get());
        }

        return new UserDto();
    }

    public String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
