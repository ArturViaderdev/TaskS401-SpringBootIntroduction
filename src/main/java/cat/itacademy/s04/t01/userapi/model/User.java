package cat.itacademy.s04.t01.userapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public class User {
    public UUID id;
    public String name;
    public String email;

    public User(String name,String email)
    {
        this.name = name;
        this.email = email;
    }

    public void setUUID()
    {
        id = java.util.UUID.randomUUID();
    }

    public UUID getId()
    {
        return id;
    }
}
