package cat.itacademy.s04.t01.userapi.controllers;

import cat.itacademy.s04.t01.userapi.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HealthController {
    UserController userController;
    @GetMapping("/health")
    public Status getOk()
    {
        return new Status("OK");
    }

    @GetMapping("/users")
    public List<User> getUsers(){
        return userController.getUsers();
    }

    @PostMapping("/users")
    public User postUsers(@RequestBody User user)
    {
        userController.addUser(user);
        return user;
    }

    public HealthController()
    {
        userController = new UserController();
    }
}
