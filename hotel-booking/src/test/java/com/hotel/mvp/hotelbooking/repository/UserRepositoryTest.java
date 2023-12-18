package com.hotel.mvp.hotelbooking.repository;

import com.hotel.mvp.hotelbooking.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void UserRepository_findByUsername_returnUser() {
        String username = "username";
        User user = User.builder()
                .userId(UUID.randomUUID())
                .username(username)
                .password("password")
                .build();

        userRepository.save(user);

        Optional<User> savedUser = userRepository.findByUsername(username);

        Assertions.assertThat(savedUser.isPresent()).isTrue();
        Assertions.assertThat(savedUser.get().getUsername()).isEqualTo(username);
    }
}