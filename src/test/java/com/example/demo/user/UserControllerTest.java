package com.example.demo.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;

public class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetAccount() throws Exception {
        UserDto mockUserDto = new UserDto();
        mockUserDto.setUsername("mockUsername");
        when(userService.getUser()).thenReturn(mockUserDto);

        mockMvc.perform(get("/user/settings"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/settings"))
                .andExpect(model().attributeExists("editUser"));
    }

    @Test
    public void testUpdateAccountValid() throws Exception {
        UserDto mockUserDto = new UserDto();
        mockUserDto.setUsername("mockUsername");

        when(userService.getUser()).thenReturn(mockUserDto);

        mockMvc.perform(post("/user/update")
                        .param("username", "validUsername")
                        .param("email", "valid@example.com")
                        .param("password", "validPassword")
                        .param("firstName", "ValidFirstName")
                        .param("lastName", "ValidLastName"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/settings"));
    }

    @Test
    public void testUpdateAccountInvalid() throws Exception {
        UserDto mockUserDto = new UserDto();
        mockUserDto.setUsername("mockUsername");

        when(userService.getUser()).thenReturn(mockUserDto);

        mockMvc.perform(post("/user/update")
                        .param("username", "invalidUsername")
                        .param("email", "invalidEmail")
                        .param("password", "short")
                        .param("firstName", "ValidFirstName")
                        .param("lastName", "ValidLastName"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/settings"));
    }
}

