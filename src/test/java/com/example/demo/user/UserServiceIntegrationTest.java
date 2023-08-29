package com.example.demo.user;

import com.example.demo.exception.user.UserAlreadyExistsException;
import com.example.demo.exception.user.UserNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserServiceIntegrationTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder encoder;
    private UUID testUserId;

    @BeforeEach
    void setUp() {
        testUserId = userRepository.save(UserEntity.builder()
                        .username("test_service_username")
                        .email("test_service_email@test_email.com")
                        .password(encoder.encode("test_password"))
                        .build())
                .getId();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteById(testUserId);
    }

    @Test
    void loadUserByUsername() {
        assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername("not_existing_username"));

        UserDetails userDetails = userService.loadUserByUsername("test_service_username");
        assertEquals("test_service_username", userDetails.getUsername());
        assertTrue(encoder.matches("test_password", userDetails.getPassword()));
        assertTrue(userDetails.getAuthorities().isEmpty());
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isCredentialsNonExpired());
        assertTrue(userDetails.isEnabled());
    }

    @Test
    void updateUser() {
        assertThrows(NullPointerException.class,
                () -> userService.updateUser(new UserDto()));
        assertThrows(NullPointerException.class,
                () -> userService.updateUser(null));
        assertThrows(UserNotFoundException.class,
                () -> userService.updateUser(new UserDto()));
        assertThrows(UserAlreadyExistsException.class,
                () -> userService.updateUser(UserDto.builder()
                        .username("test_service_username")
                        .build()));
        assertThrows(UserAlreadyExistsException.class,
                () -> userService.updateUser(UserDto.builder()
                        .email("test_service_email@test_email.com")
                        .build()));

        UserDto userDtoToUpdate = UserDto.builder().build();
        UserDto userDtoResponse = userService.updateUser(userDtoToUpdate);
        assertEquals("test_service_username", userDtoResponse.getUsername());
        assertEquals("test_service_email@test_email.com", userDtoResponse.getEmail());
        assertTrue(encoder.matches("test_password", userDtoResponse.getPassword()));
        assertNull(userDtoResponse.getFirstName());
        assertNull(userDtoResponse.getLastName());

        userDtoToUpdate = UserDto.builder()
                .username("test_service_username_updated")
                .email("test_service_email_updated@test_email.com")
                .password("test_password_updated")
                .firstName("test_firstName_updated")
                .lastName("test_lastName_updated")
                .build();
        userDtoResponse = userService.updateUser(userDtoToUpdate);
        assertEquals("test_service_username_updated", userDtoResponse.getUsername());
        assertEquals("test_service_email_updated@test_email.com", userDtoResponse.getEmail());
        assertTrue(encoder.matches("test_password_updated", userDtoResponse.getPassword()));
        assertEquals("test_firstName_updated", userDtoResponse.getFirstName());
        assertEquals("test_lastName_updated", userDtoResponse.getLastName());
    }

    @Test
    void createUser() {
    }
}
