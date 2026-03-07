package com.dinesh.tms.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dinesh.tms.user.dto.CreateUserRequest;
import com.dinesh.tms.user.exception.DuplicateEmailException;
import com.dinesh.tms.user.exception.DuplicateUsernameException;
import com.dinesh.tms.user.exception.UnderageException;
import com.dinesh.tms.user.exception.UserNotFoundException;
import com.dinesh.tms.user.model.User;
import com.dinesh.tms.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private static final String USERNAME = "test";
    private static final String EMAIL = "test@gmail.com";
    private static final String FIRST_NAME = "test";
    private static final String LAST_NAME = "tester";
    private static final String POSTAL_CODE = "M3J0L7";

    // ==================== CREATE USER ====================

    @Test
    void shouldCreateUserSuccessfully() {

        CreateUserRequest request = createValidRequest();
        User savedUser = createUser();

        when(userRepository.existsByUsername(USERNAME)).thenReturn(false);
        when(userRepository.existsByEmail(EMAIL)).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.createUser(request);

        assertNotNull(result);
        assertEquals(USERNAME, result.getUsername());
        assertEquals(EMAIL, result.getEmail());

        verify(userRepository).existsByUsername(USERNAME);
        verify(userRepository).existsByEmail(EMAIL);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldThrowWhenUsernameAlreadyExists() {

        CreateUserRequest request = createValidRequest();

        when(userRepository.existsByUsername(USERNAME)).thenReturn(true);

        assertThrows(DuplicateUsernameException.class,
                () -> userService.createUser(request));

        verify(userRepository).existsByUsername(USERNAME);
        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldThrowWhenEmailAlreadyExists() {

        CreateUserRequest request = createValidRequest();

        when(userRepository.existsByUsername(USERNAME)).thenReturn(false);
        when(userRepository.existsByEmail(EMAIL)).thenReturn(true);

        assertThrows(DuplicateEmailException.class,
                () -> userService.createUser(request));

        verify(userRepository).existsByEmail(EMAIL);
        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldThrowWhenUserIsUnderage() {

        CreateUserRequest request = new CreateUserRequest(
                USERNAME,
                EMAIL,
                FIRST_NAME,
                LAST_NAME,
                LocalDate.now().minusYears(16),
                POSTAL_CODE
        );

        when(userRepository.existsByUsername(USERNAME)).thenReturn(false);
        when(userRepository.existsByEmail(EMAIL)).thenReturn(false);

        assertThrows(UnderageException.class,
                () -> userService.createUser(request));

        verify(userRepository, never()).save(any());
    }

    // ==================== GET USER BY ID ====================

    @Test
    void shouldReturnUserWhenFound() {

        UUID userId = UUID.randomUUID();
        User mockUser = createUser();

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        User result = userService.getUserByID(userId);

        assertNotNull(result);
        assertEquals(USERNAME, result.getUsername());

        verify(userRepository).findById(userId);
    }

    @Test
    void shouldThrowWhenUserNotFound() {

        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.getUserByID(userId));

        verify(userRepository).findById(userId);
    }

    // ==================== GET ALL USERS ====================

    @Test
    void shouldReturnAllUsers() {

        List<User> users = List.of(createUser(), createUser());

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());

        verify(userRepository).findAll();
    }

    @Test
    void shouldReturnEmptyListWhenNoUsersExist() {

        when(userRepository.findAll()).thenReturn(List.of());

        List<User> result = userService.getAllUsers();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(userRepository).findAll();
    }

    // ==================== DELETE USER ====================

    @Test
    void shouldDeleteUserSuccessfully() {

        UUID userId = UUID.randomUUID();
        User user = createUser();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.deleteUserById(userId);

        verify(userRepository).findById(userId);
        verify(userRepository).delete(user);
    }

    @Test
    void shouldThrowWhenDeletingNonExistentUser() {

        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.deleteUserById(userId));

        verify(userRepository).findById(userId);
        verify(userRepository, never()).delete(any());
    }

    // ==================== TEST DATA HELPERS ====================

    private CreateUserRequest createValidRequest() {
        return new CreateUserRequest(
                USERNAME,
                EMAIL,
                FIRST_NAME,
                LAST_NAME,
                LocalDate.now().minusYears(30),
                POSTAL_CODE
        );
    }

    private User createUser() {
        return new User(
                USERNAME,
                EMAIL,
                FIRST_NAME,
                LAST_NAME,
                LocalDate.now().minusYears(30),
                POSTAL_CODE
        );
    }


}
