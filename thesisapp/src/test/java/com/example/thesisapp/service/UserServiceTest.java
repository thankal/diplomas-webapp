package com.example.thesisapp.service;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.thesisapp.dao.UserDAO;
import com.example.thesisapp.model.User;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations="classpath:application.properties")
public class UserServiceTest {

    @TestConfiguration
    static class UserServiceImplTestContextConfiguration {
        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }

    @Autowired
    private UserService userService;

    @MockBean
    private UserDAO userDAO;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void testUserDAOJpaImplIsNotNull() {
        Assertions.assertNotNull(userService);
    }

    @Test
    public void testGetUserByUsername() {
        User user = new User();
        Mockito.when(userDAO.findByUsername("username")).thenReturn(Optional.of(user));
        User foundUser = userService.getUserByUsername("username");
        Assertions.assertEquals(user, foundUser);
    }

    @Test
    public void testIsUserPresent() {
        User user = new User();
        user.setUsername("username");
        Mockito.when(userDAO.findByUsername("username")).thenReturn(Optional.of(user));
        boolean isPresent = userService.isUserPresent(user);
        Assertions.assertTrue(isPresent);
    }

    @Test
    public void testSaveUser() {
        User user = new User();
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword("password");

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());

        userDAO.save(user);
        Mockito.verify(userDAO).save(user);

        String savedPassword = user.getPassword();
        boolean isPasswordMatch = bCryptPasswordEncoder.matches(savedPassword, encodedPassword);
        Assertions.assertTrue(isPasswordMatch);

    }
}
