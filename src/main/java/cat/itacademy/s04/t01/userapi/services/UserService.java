package cat.itacademy.s04.t01.userapi.services;

import cat.itacademy.s04.t01.userapi.exception.EmailAlreadyExistsException;
import cat.itacademy.s04.t01.userapi.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    public User createUser(User user) throws EmailAlreadyExistsException;

    public List<User> readAllUsers();

    public List<User> findByName(String name);

    public User getUserId(UUID id);

    public void removeAllUsers();
}
