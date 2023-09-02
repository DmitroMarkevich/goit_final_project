package com.example.demo.user;

import com.example.demo.email.EmailService;
import com.example.demo.exception.user.EmailIsUsedException;
import com.example.demo.note.NoteEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService userService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;
    @Mock
    private EmailService emailService;
    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        userService = new UserService(passwordEncoder, userRepository, new UserMapper(), emailService);
    }

    private void mockSecurityContextHolder() {
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testLoadUserByUsernameThrowsWhenUsernameDoesNotExist() {
        when(userRepository.findByUsername("non_existing_username")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("non_existing_username"));
    }

    @Test
    void testLoadUserByUsernameHappyPath() {
        UserEntity userEntity = UserEntity.builder()
                .username("existing_username")
                .password("existing_password")
                .build();
        when(userRepository.findByUsername("existing_username")).thenReturn(Optional.of(userEntity));

        UserDetails userDetails = userService.loadUserByUsername("existing_username");
        assertEquals("existing_username", userDetails.getUsername());
        assertEquals("existing_password", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().isEmpty());
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isCredentialsNonExpired());
        assertTrue(userDetails.isEnabled());
    }

    @Test
    void testCreateUserHappyPath() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        when(passwordEncoder.encode(any())).thenReturn("encoded_test_password");
        when(userRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        UserDto actualUserDto = userService.createUser(UserDto.builder()
                .updatedAt(now)
                .username("test_username")
                .email("test_email@test_email.com")
                .password("test_password")
                .firstName("test_firstname")
                .lastName("test_lastname")
                .build());

        assertEquals("test_username", actualUserDto.getUsername());
        assertEquals("test_email@test_email.com", actualUserDto.getEmail());
        assertEquals("encoded_test_password", actualUserDto.getPassword());
        assertEquals("test_firstname", actualUserDto.getFirstName());
        assertEquals("test_lastname", actualUserDto.getLastName());
        assertTrue(actualUserDto.getUpdatedAt().after(now));
    }

    @Test
    void testUpdateUserThrowsIfUsernameDoesNotExist() {
        when(userRepository.findByUsername("non_existing_username")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.updateUser(UserDto.builder()
                .username("non_existing_username")
                .build()));
    }

    @Test
    void testUpdateUserThrowsIfEmailIsAlreadyTaken() {
        UUID id = UUID.randomUUID();
        String username = "test_username";
        String email = "test_email@test_email.com";
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(UserEntity.builder()
                .email("old_test_email@test_email.com")
                .build()));
        when(userRepository.existsByEmailAndIdNot(email, id)).thenReturn(true);

        assertThrows(EmailIsUsedException.class, () -> userService.updateUser(UserDto.builder()
                .id(id)
                .username(username)
                .email(email)
                .build()));
    }

    @Test
    void testUpdateUserHappyPath() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        UUID id = UUID.randomUUID();
        String username = "test_username";
        String email = "test_email@test_email.com";
        String password = "test_password";
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(UserEntity.builder()
                .id(id)
                .username(username)
                .email("old_test_email@test_email.com")
                .password("old_password")
                .firstName("old_firstname")
                .lastName("old_lastname")
                .updatedAt(now)
                .notes(List.of(new NoteEntity(), new NoteEntity()))
                .build()));
        when(userRepository.existsByEmailAndIdNot(email, id)).thenReturn(false);
        when(passwordEncoder.encode(password)).thenReturn("encoded_test_password");
        when(userRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        UserDto actualUserDto = userService.updateUser(UserDto.builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password)
                .firstName("new_firstname")
                .lastName("new_lastname")
                .updatedAt(now)
                .notes(List.of(new NoteEntity()))
                .build());

        assertEquals(id, actualUserDto.getId());
        assertEquals(username, actualUserDto.getUsername());
        assertEquals(email, actualUserDto.getEmail());
        assertEquals("encoded_test_password", actualUserDto.getPassword());
        assertEquals("new_firstname", actualUserDto.getFirstName());
        assertEquals("new_lastname", actualUserDto.getLastName());
        assertTrue(actualUserDto.getUpdatedAt().after(now));
        assertEquals(List.of(new NoteEntity(), new NoteEntity()), actualUserDto.getNotes());
    }

    @Test
    void testGetUserThrowsWhenUsernameDoesNotExist() {
        mockSecurityContextHolder();
        when(userRepository.findByUsername("non_existing_username")).thenReturn(Optional.empty());
        when(authentication.getName()).thenReturn("non_existing_username");

        assertEquals(new UserDto(), userService.getUser());
    }

    @Test
    void testGetUserHappyPath() {
        mockSecurityContextHolder();
        when(userRepository.findByUsername("test_username")).thenReturn(Optional.of(UserEntity.builder()
                .username("test_username")
                .email("test_email@test_email.com")
                .password("test_password")
                .firstName("test_firstname")
                .lastName("test_lastname")
                .build()));
        when(authentication.getName()).thenReturn("test_username");

        UserDto actualUserDto = userService.getUser();
        assertEquals("test_username", actualUserDto.getUsername());
        assertEquals("test_email@test_email.com", actualUserDto.getEmail());
        assertEquals("test_password", actualUserDto.getPassword());
        assertEquals("test_firstname", actualUserDto.getFirstName());
        assertEquals("test_lastname", actualUserDto.getLastName());
    }

    @Test
    void testGetUsernameHappyPath() {
        mockSecurityContextHolder();
        when(authentication.getName()).thenReturn("test_username");

        assertEquals("test_username", userService.getUsername());
    }

    @Test
    void testGetByIdThrowsWhenIdDoesNotExist() {
        UUID nonExistingId = UUID.randomUUID();
        when(userRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userService.getById(nonExistingId));
    }

    @Test
    void testGetByIdHappyPath() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.of(UserEntity.builder()
                .id(id)
                .username("test_username")
                .email("test_email@test_email.com")
                .password("test_password")
                .firstName("test_firstname")
                .lastName("test_lastname")
                .build()));

        UserDto actualUserDto = userService.getById(id);
        assertEquals(id, actualUserDto.getId());
        assertEquals("test_username", actualUserDto.getUsername());
        assertEquals("test_email@test_email.com", actualUserDto.getEmail());
        assertEquals("test_password", actualUserDto.getPassword());
        assertEquals("test_firstname", actualUserDto.getFirstName());
        assertEquals("test_lastname", actualUserDto.getLastName());
    }
}