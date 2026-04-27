package cat.itacademy.s04.t01.userapi;

import cat.itacademy.s04.t01.userapi.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository repository;

    @Override
    public User createUser(User user) throws EmailAlreadyExistsException {
        if (repository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException();
        }
        user.setUUID();
        user = repository.save(user);
        return user;
    }

    @Override
    public List<User> readAllUsers() {
        List<User> users = repository.findAll();
        return users;
    }

    @Override
    public List<User> findByName(String name) {
        List<User> users = repository.searchByName(name);
        return users;
    }

    @Override
    public Optional<User> getUserId(UUID id) {
        Optional<User> user = repository.findById(id);
        return user;
    }

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }
}
