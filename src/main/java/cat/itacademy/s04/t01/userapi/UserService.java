package cat.itacademy.s04.t01.userapi;

import cat.itacademy.s04.t01.userapi.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    public User createUser(User user) throws EmailAlreadyExistsException;
    public List<User> readAllUsers();
    public List<User> findByName(String name);
    public Optional<User> getUserId(UUID id);
}
