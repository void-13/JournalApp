package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    UserService userService;

    @ParameterizedTest
    @ValueSource(strings = {"Anamika", "Navin"})
    public void testValidUser_findByUserName(String username) {
        User user = userService.findByUserName(username);
        assertNotNull(user);
        assertEquals(username, user.getUsername());
    }

    @ParameterizedTest
    @ValueSource(strings = {"testUser1", "testUser2"})
    public void testInvalidUser_findByUserName(String username) {
        User user = userService.findByUserName(username);
        assertNull(user);
    }
}
