package cat.itacademy.s04.t01.userapi.controllers;

import cat.itacademy.s04.t01.userapi.services.UserService;
import cat.itacademy.s04.t01.userapi.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class UserController {
    UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users")
    public List<User> getUsers(@RequestParam(name = "name", defaultValue = "") String name) {
        if (name.isEmpty()) {
            return userService.readAllUsers();
        } else {
            return userService.findByName(name);
        }
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public User postUsers(@RequestBody User user) {
        return userService.createUser(user);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users/{id}")
    public User getUserId(@PathVariable String id) {
        return userService.getUserId(UUID.fromString(id));
    }

    public UserController(UserService userService) {
        this.userService = userService;
    }
}
