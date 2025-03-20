package com.admini.manage.service;

import com.admini.manage.entity.User;
import com.admini.manage.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testGetAllUsers() {
        // Given: Save a few test users
        User user1 = new User();
        user1.setUsername("testuser1");
        user1.setEmail("test1@example.com");
        user1.setPassword("password1");
        user1.setRole(User.UserRole.USER);
        user1.setLocked(false);
        userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("testuser2");
        user2.setEmail("test2@example.com");
        user2.setPassword("password2");
        user2.setRole(User.UserRole.USER);
        user2.setLocked(false);
        userRepository.save(user2);

        // When: Get all users
        List<User> users = userService.getAllUsers();

        // Then: Verify all users are returned
        assertNotNull(users);
        assertTrue(users.size() >= 2);
        assertTrue(users.stream().anyMatch(u -> u.getUsername().equals("testuser1")));
        assertTrue(users.stream().anyMatch(u -> u.getUsername().equals("testuser2")));
    }

    @Test
    public void testLockUser() {
        // Given: Save a test user
        User user = new User();
        user.setUsername("lockuser");
        user.setEmail("lock@example.com");
        user.setPassword("password");
        user.setRole(User.UserRole.USER);
        user.setLocked(false);
        User savedUser = userRepository.save(user);

        // When: Lock the user
        User lockedUser = userService.lockUser(savedUser.getId());

        // Then: Verify the user is locked
        assertNotNull(lockedUser);
        assertTrue(lockedUser.isLocked());

        // Verify the change is persisted
        User retrievedUser = userRepository.findById(savedUser.getId()).orElse(null);
        assertNotNull(retrievedUser);
        assertTrue(retrievedUser.isLocked());
    }

    @Test
    public void testUnlockUser() {
        // Given: Save a locked test user
        User user = new User();
        user.setUsername("unlockuser");
        user.setEmail("unlock@example.com");
        user.setPassword("password");
        user.setRole(User.UserRole.USER);
        user.setLocked(true);
        User savedUser = userRepository.save(user);

        // When: Unlock the user
        User unlockedUser = userService.unlockUser(savedUser.getId());

        // Then: Verify the user is unlocked
        assertNotNull(unlockedUser);
        assertFalse(unlockedUser.isLocked());

        // Verify the change is persisted
        User retrievedUser = userRepository.findById(savedUser.getId()).orElse(null);
        assertNotNull(retrievedUser);
        assertFalse(retrievedUser.isLocked());
    }
} 