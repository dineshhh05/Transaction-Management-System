package com.dinesh.tms.user.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.dinesh.tms.account.model.Account;
import com.dinesh.tms.account.service.AccountService;
import com.dinesh.tms.user.dto.CreateUserRequest;
import com.dinesh.tms.user.exception.UserNotFoundException;
import com.dinesh.tms.user.model.User;
import com.dinesh.tms.user.service.UserService;

import tools.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {
        @Autowired
        private MockMvc mockMvc;

        @MockitoBean
        private UserService userService;

        @MockitoBean
        private AccountService accountService;

        @Autowired
        private ObjectMapper objectMapper;

        // ================= CREATE USER =================

        @Test
        void shouldCreateUserSuccessfully() throws Exception {

        CreateUserRequest request = validRequest();
        User user = mockUser();

        when(userService.createUser(any())).thenReturn(user);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("test"))
                .andExpect(jsonPath("$.email").value("test@gmail.com"));

        verify(userService).createUser(any());
        }

        @Test
        void shouldReturn400WhenRequestInvalid() throws Exception {

        CreateUserRequest invalid = new CreateUserRequest(
                "", "", "", "", LocalDate.now(), ""
        );

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(userService);
        }

        // ================= GET USER BY ID =================

        @Test
        void shouldReturnUserById() throws Exception {

        UUID id = UUID.randomUUID();
        User user = mockUser();

        when(userService.getUserByID(id)).thenReturn(user);

        mockMvc.perform(get("/users/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("test"))
                .andExpect(jsonPath("$.email").value("test@gmail.com"));

        verify(userService).getUserByID(id);
        }

        @Test
        void shouldReturn404WhenUserNotFound() throws Exception {

        UUID id = UUID.randomUUID();

        when(userService.getUserByID(id))
                .thenThrow(new UserNotFoundException(id));

        mockMvc.perform(get("/users/{id}", id))
                .andExpect(status().isNotFound());

        verify(userService).getUserByID(id);
        }

        // ================= GET ALL USERS =================

        @Test
        void shouldReturnAllUsers() throws Exception {

        List<User> users = List.of(mockUser(), mockUser());

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(userService).getAllUsers();
        }

        @Test
        void shouldReturnEmptyListWhenNoUsersExist() throws Exception {

        when(userService.getAllUsers()).thenReturn(List.of());

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(userService).getAllUsers();
        }

        // ================= GET USER ACCOUNTS =================

        @Test
        void shouldReturnUserAccounts() throws Exception {

        UUID userId = UUID.randomUUID();

        Account account = mock(Account.class);
        when(accountService.getAccountsByUserID(userId))
                .thenReturn(List.of(account));

        mockMvc.perform(get("/users/{id}/accounts", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(accountService).getAccountsByUserID(userId);
        }

        @Test
        void shouldReturnEmptyAccountList() throws Exception {

        UUID userId = UUID.randomUUID();

        when(accountService.getAccountsByUserID(userId))
                .thenReturn(List.of());

        mockMvc.perform(get("/users/{id}/accounts", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(accountService).getAccountsByUserID(userId);
        }

        // ================= DELETE USER =================

        @Test
        void shouldDeleteUserSuccessfully() throws Exception {

        UUID id = UUID.randomUUID();

        doNothing().when(userService).deleteUserById(id);

        mockMvc.perform(delete("/users/{id}", id))
                .andExpect(status().isNoContent());

        verify(userService).deleteUserById(id);
        }

        @Test
        void shouldReturn404WhenDeletingMissingUser() throws Exception {

        UUID id = UUID.randomUUID();

        doThrow(new UserNotFoundException(id))
                .when(userService)
                .deleteUserById(id);

        mockMvc.perform(delete("/users/{id}", id))
                .andExpect(status().isNotFound());

        verify(userService).deleteUserById(id);
        }

        @Test
        void shouldReturn400WhenInvalidUUID() throws Exception {
        mockMvc.perform(get("/users/not-a-uuid"))
                .andExpect(status().isBadRequest());
        }

        // ================= TEST DATA =================

        private CreateUserRequest validRequest() {
        return new CreateUserRequest(
                "test",
                "test@gmail.com",
                "test",
                "tester",
                LocalDate.now().minusYears(25),
                "M3J0L7"
        );
        }

        private User mockUser() {
        return new User(
                "test",
                "test@gmail.com",
                "test",
                "tester",
                LocalDate.now().minusYears(25),
                "M3J0L7"
        );
        }
}