package com.example.demo.user;

import com.example.demo.email.EmailService;
import com.example.demo.exception.user.EmailAlreadyUsedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
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
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EmailService emailService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return new User(userEntity.getUsername(), userEntity.getPassword(), List.of());
    }

    public UserDto createUser(UserDto userDto) {
        UserDto createdUser = userMapper.mapEntityToDto(userRepository.save(userMapper.mapDtoToEntity(userDto).toBuilder()
                .password(passwordEncoder.encode(userDto.getPassword()))
                .updatedAt(new Timestamp(System.currentTimeMillis()))
                .build()
        ));

        emailExecutor.submit(() -> emailService.sendEmail(userDto.getEmail(), "Registration", "Successfully registered!"));

        return createdUser;
    }

    public UserDto updateUser(UserDto userDto) throws EmailAlreadyUsedException {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(userDto.getUsername());

        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User Not Found with username: " + userDto.getUsername());
        }

        UserDto existingUser = userMapper.mapEntityToDto(optionalUser.get());
        String emailUser = userDto.getEmail();

        if (!emailUser.equals(existingUser.getEmail())) {
            boolean isEmailUsedByOtherUser = userRepository.existsByEmailAndIdNot(userDto.getEmail(), userDto.getId());

            if (isEmailUsedByOtherUser) {
                throw new EmailAlreadyUsedException(emailUser);
            }
        }

        UserDto updatedUser = userMapper.mapEntityToDto(userRepository.save(UserEntity.builder()
                .id(existingUser.getId())
                .username(existingUser.getUsername())
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .createdAt(userDto.getCreatedAt())
                .updatedAt(new Timestamp(System.currentTimeMillis()))
                .notes(existingUser.getNotes())
                .build()));

        emailExecutor.submit(() -> emailService.sendEmail(updatedUser.getEmail(), "Updating settings", "Your account successfully updated!"));

        return updatedUser;
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

    public UserDto getById(UUID id) {
        return userRepository.findById(id)
                .map(userMapper::mapEntityToDto)
                .orElseThrow();
    }

    public boolean isAuthenticated(Authentication authentication) {
        return authentication != null && authentication.isAuthenticated();
    }
}