package com.admin.modelTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.admin.management.model.UserInfo;

public class UserInfoTest {
    @Test
    void testConstructorAndGetters(){
        Long id = 1L;
        String username = "Curry";
        String email = "user@outlook.com";
        String role = "ADMIN";
        boolean active = true;

        UserInfo userInfo = new UserInfo(id, username, email, role, active);

        assertEquals(id, userInfo.getId());
        assertEquals(username, userInfo.getUsername());
        assertEquals(email, userInfo.getEmail());
        assertEquals(role, userInfo.getRole());
        assertEquals(active, userInfo.isActive());
    }

    @Test
    void testSettersAndGetters(){
        UserInfo userInfo = new UserInfo();
        Long id = 2L;
        String username = "Lebron";
        String email = "Lebron@outlook.com";
        String role = "USER";
        boolean active = false;

        userInfo.setId(id);
        userInfo.setUsername(username);
        userInfo.setEmail(email);
        userInfo.setRole(role);
        userInfo.setActive(active);

        assertEquals(id, userInfo.getId());
        assertEquals(username, userInfo.getUsername());
        assertEquals(email, userInfo.getEmail());
        assertEquals(role, userInfo.getRole());
        assertEquals(active, userInfo.isActive());
    }

    @Test
    void testDefaultConstructor(){
        UserInfo userInfo = new UserInfo();

        assertNull(userInfo.getId());
        assertNull(userInfo.getUsername());
        assertNull(userInfo.getEmail());
        assertNull(userInfo.getRole());
        assertEquals(false, userInfo.isActive());        
    }

    @Test
    void testEmailTypo(){
        UserInfo userInfo = new UserInfo();
        String email = "test@outlook.com";

        userInfo.setEmail(email);

        assertEquals(email, userInfo.getEmail());
    }
}
