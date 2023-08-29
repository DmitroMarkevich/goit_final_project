package com.example.demo.user;

import com.example.demo.config.UserDetailsImpl;
import com.example.demo.exception.user.UserAlreadyExistsException;
import com.example.demo.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }

    public UserDto updateUser(UUID id, UserDto userDto) {
        if (id == null) {
            throw new NullPointerException("Id is null.");
        }
        if (userDto == null) {
            throw new NullPointerException("Dto is null.");
        }

        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (userRepository.existsByUsername(userDto.getUsername())
                || userRepository.existsByEmail(userDto.getEmail())) {
            throw new UserAlreadyExistsException(userDto.getUsername(), userDto.getEmail());
        }

        UserEntity.UserEntityBuilder userBuilder = existingUser.toBuilder()
                .updatedAt(new Timestamp(System.currentTimeMillis()));

        if (StringUtils.isNotBlank(userDto.getUsername())) {
            userBuilder.username(userDto.getUsername());
        }
        if (StringUtils.isNotBlank(userDto.getEmail())) {
            userBuilder.email(userDto.getEmail());
        }
        if (StringUtils.isNotBlank(userDto.getPassword())) {
            userBuilder.password(encoder.encode(userDto.getPassword()));
        }
        if (StringUtils.isNotBlank(userDto.getFirstName())) {
            userBuilder.firstName(userDto.getFirstName());
        }
        if (StringUtils.isNotBlank(userDto.getLastName())) {
            userBuilder.lastName(userDto.getLastName());
        }
        return userMapper.mapEntityToDto(userRepository.save(userBuilder.build()));
    }

    public UserDto createUser(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())
                || userRepository.existsByEmail(userDto.getEmail())) {
            throw new UserAlreadyExistsException(userDto.getUsername(), userDto.getEmail());
        }

        Timestamp now = new Timestamp(System.currentTimeMillis());

        UserEntity user = UserEntity.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(encoder.encode(userDto.getPassword()))
                .createdAt(now)
                .updatedAt(now)
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .build();

        return userMapper.mapEntityToDto(userRepository.save(user));
    }
}
