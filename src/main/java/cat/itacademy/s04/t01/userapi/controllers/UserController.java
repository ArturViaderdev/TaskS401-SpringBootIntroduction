package cat.itacademy.s04.t01.userapi.controllers;

import cat.itacademy.s04.t01.userapi.EmailAlreadyExistsException;
import cat.itacademy.s04.t01.userapi.UserService;
import cat.itacademy.s04.t01.userapi.UserServiceImpl;
import cat.itacademy.s04.t01.userapi.model.User;
import cat.itacademy.s04.t01.userapi.InMemoryUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class UserController {
    UserService userService;

    @GetMapping("/users")
    public List<User> getUsers(@RequestParam(defaultValue = "") String name) {
        if (name.isEmpty()) {
            return userService.readAllUsers();
        } else {
            return userService.findByName(name);
        }
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public User postUsers(@RequestBody User user) {
        try {
            return userService.createUser(user);
        } catch (EmailAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/users/{id}")
    public User getUserId(@PathVariable String id) {
        Optional<User> user = userService.getUserId(UUID.fromString(id));
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return user.get();
    }

    public UserController() {
        userService = new UserServiceImpl(new InMemoryUserRepository());
    }
}
