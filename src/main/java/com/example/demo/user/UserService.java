package com.example.demo.user;

import com.example.demo.exception.note.NoteNotFoundException;
import com.example.demo.exception.user.UserAlreadyExistsException;
import com.example.demo.exception.user.UserNotFoundException;
import com.example.demo.note.NoteEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        if (userDto == null) {
            throw new IllegalArgumentException("UserDto is null.");
        }

        UUID userId = userDto.getId();
        UserEntity existingUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        if (userRepository.existsByUsername(userDto.getUsername()) || userRepository.existsByEmail(userDto.getEmail())) {
            throw new UserAlreadyExistsException(userDto.getUsername(), userDto.getEmail());
        }

        UserEntity.UserEntityBuilder userBuilder = existingUser.toBuilder().updatedAt(new Timestamp(System.currentTimeMillis()));

        return userMapper.mapEntityToDto(userRepository.save(userBuilder.build()));
    }

    public UserEntity getUserByUsername(String username) {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new UserNotFoundException(optionalUser.get().getId());
        }
    }
}
