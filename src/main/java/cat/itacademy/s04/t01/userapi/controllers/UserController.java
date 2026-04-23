package cat.itacademy.s04.t01.userapi.controllers;

import cat.itacademy.s04.t01.userapi.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserController {
    public static List<User> users;

    public UserController()
    {
        users = new ArrayList<>();
    }

    public List<User> getUsers()
    {
        return users;
    }

    public void addUser(User user)
    {
        user.setUUID();
        users.add(user);
    }
}
