package com.example.authentication_api.repository;

import com.example.authentication_api.model.entity.User;
import com.example.authentication_api.model.entity.UserRole;
import com.example.authentication_api.util.UserCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    void findByEmail_ReturnUser_WhenSuccessful() {
        User saved = userRepository.save(UserCreator.createUserToBeSaved());

        User userByEmail = userRepository.findByEmail(saved.getEmail());

        Assertions.assertThat(userByEmail).isNotNull();
        Assertions.assertThat(userByEmail.getEmail()).isEqualTo(saved.getEmail());
        Assertions.assertThat(userByEmail.getRoles()).contains(UserRole.ROLE_USER);

    }

    @Test
    void findByEmail_ReturnsNull_WhenUserNotFound() {
        User userByEmail = userRepository.findByEmail("");

        Assertions.assertThat(userByEmail).isNull();
    }
}