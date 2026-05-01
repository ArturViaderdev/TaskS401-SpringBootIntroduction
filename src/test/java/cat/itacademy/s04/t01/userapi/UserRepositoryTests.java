package cat.itacademy.s04.t01.userapi;

import cat.itacademy.s04.t01.userapi.model.User;
import cat.itacademy.s04.t01.userapi.repository.InMemoryUserRepository;
import cat.itacademy.s04.t01.userapi.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserRepositoryTests {
    private UserRepository userRepository;

    @BeforeEach
    public void clean() {
        userRepository.removeAll();
    }


    @Test
    public void saveTest() {
        User userAdd = new User("Pedro", "pedro@gmail.com");
        userAdd.setUUID(java.util.UUID.randomUUID());
        User savedUser = userRepository.save(userAdd);
        Assertions.assertTrue(userAdd.equals(savedUser));
    }

    @Test
    public void findAllTest() {
        User userAdd = new User("Pedro", "pedro@gmail.com");
        userAdd.setUUID(java.util.UUID.randomUUID());
        User userAddSecond = new User("Ramon", "ramon@gmail.com");
        userAddSecond.setUUID(java.util.UUID.randomUUID());
        User userAddThird = new User("Juan", "juan@gmail.com");
        userAddThird.setUUID(java.util.UUID.randomUUID());
        userRepository.save(userAdd);
        userRepository.save(userAddSecond);
        userRepository.save(userAddThird);
        List<User> users = userRepository.findAll();
        Assertions.assertTrue(users.size() == 3);
        Assertions.assertTrue(users.contains(userAdd));
        Assertions.assertTrue(users.contains(userAddSecond));
        Assertions.assertTrue(users.contains(userAddThird));
    }

    @Test
    public void findByIdTest() {
        User user = userRepository.save(new User("Pedro", "pedro@gmail.com"));
        user.setUUID(java.util.UUID.randomUUID());
        Optional<User> readed = userRepository.findById(user.getId());
        Assertions.assertTrue(!(readed.isEmpty()));
    }

    @Test
    public void findByIdEmpty() {
        Optional<User> readed = userRepository.findById(UUID.randomUUID());
        Assertions.assertTrue(readed.isEmpty());
    }

    public UserRepositoryTests() {
        userRepository = new InMemoryUserRepository();
    }
}
