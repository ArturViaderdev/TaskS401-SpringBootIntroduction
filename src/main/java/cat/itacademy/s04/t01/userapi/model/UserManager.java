package cat.itacademy.s04.t01.userapi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserManager {
    public static List<User> users;

    public UserManager()
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

    public User getUserId(UUID idUser)
    {
        boolean found = false;
        int count;
        for(count=0;count<users.size();count++)
        {
            if(users.get(count).getId().equals(idUser))
            {
                found=true;
                break;
            }
        }
        if(found)
        {
            return users.get(count);
        }
        else
        {
            return null;
        }
    }

    public List<User> findByName(String name)
    {
        List<User> result = users.stream().filter(o -> o.getName().toLowerCase().contains(name.toLowerCase())).toList();
        return result;
    }
}
