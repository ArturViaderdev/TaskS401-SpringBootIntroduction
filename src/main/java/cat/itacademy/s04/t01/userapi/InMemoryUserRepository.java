package cat.itacademy.s04.t01.userapi;

import cat.itacademy.s04.t01.userapi.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InMemoryUserRepository implements UserRepository{
    public static List<User> users;

    public InMemoryUserRepository()
    {
        users = new ArrayList<>();
    }

    @Override
    public User save(User user) {
        users.add(user);
        return users.get(users.size()-1);
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return users.stream()
                .filter(o -> o.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<User> searchByName(String name) {
        List<User> result = users.stream().filter(o -> o.getName().toLowerCase().contains(name.toLowerCase())).toList();
        return result;
    }

    @Override
    public boolean existsByEmail(String email) {
        List<User> result = users.stream().filter(o->o.getEmail().equals(email)).toList();
        if(!result.isEmpty())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
