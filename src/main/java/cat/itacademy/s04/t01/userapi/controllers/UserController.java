package cat.itacademy.s04.t01.userapi.controllers;

import cat.itacademy.s04.t01.userapi.model.User;
import cat.itacademy.s04.t01.userapi.model.UserManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
@RestController
public class UserController {
    UserManager userManager;
    @GetMapping("/users")
    public List<User> getUsers(){
        return userManager.getUsers();
    }

    @PostMapping("/users")
    public User postUsers(@RequestBody User user)
    {
        userManager.addUser(user);
        return user;
    }

    @GetMapping("/users/{id}")
    public User getUserId(@PathVariable String id)
    {
        User user = userManager.getUserId(UUID.fromString(id));
        if(user == null)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return user;
    }

    public UserController()
    {
        userManager = new UserManager();
    }
}
